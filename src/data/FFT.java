package data;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FFT {

    private static final double SAMPLING_RATE = 44100;
    private static final double C3 = 130.81;
    private static final double F5 = 1698.46;

    public static List<FrequencyPoint> fft(List<Point> points) {
        List<Point> centeredPoints = dcOffsetRemover(points); //Not functional
        List<Point> filteredPoints = bandGapFilter(centeredPoints, C3, F5); //

        int paddedSize = nextPowerOfTwo(filteredPoints.size());
        double[] input = new double[paddedSize];

        for (int i = 0; i < filteredPoints.size(); i++) {
            input[i] = filteredPoints.get(i).getY();  
        }

        FastFourierTransformer fft = new FastFourierTransformer(DftNormalization.STANDARD);
        Complex[] result = fft.transform(input, TransformType.FORWARD);

        List<FrequencyPoint> transformedPoints = new ArrayList<>();
        for (int j = 0; j < result.length / 2; j++) {
            double frequency = (double) j * SAMPLING_RATE / paddedSize;
            double magnitude = Math.sqrt(result[j].getReal() * result[j].getReal() + result[j].getImaginary() * result[j].getImaginary()) / paddedSize;

            transformedPoints.add(new FrequencyPoint(frequency, Math.log10(magnitude + 1e-10)));
        }

        return transformedPoints;
    }

    public static List<Point> dcOffsetRemover(List<Point> points) {
        double mean = points.stream().mapToDouble(Point::getY).average().orElse(0);

        List<Point> centeredPoints = new ArrayList<>();
        for (Point point : points) {
            centeredPoints.add(new Point(point.x, (int) (point.getY() - mean)));
        }
        return centeredPoints;
    }

    public static List<Point> bandGapFilter(List<Point> points, double lowCutoff, double highCutoff) {
        double alphaLow = 1.0 / (1 + (SAMPLING_RATE / (2 * Math.PI * lowCutoff)));
        double alphaHigh = 1.0 / (1 + (SAMPLING_RATE / (2 * Math.PI * highCutoff)));

        List<Point> filteredPoints = new ArrayList<>();
        double prevY = points.get(0).getY();
        for (Point point : points) {
            double lowPassedY = alphaLow * point.getY() + (1 - alphaLow) * prevY;
            double bandPassedY = alphaHigh * (point.getY() - lowPassedY);
            filteredPoints.add(new Point(point.x, (int) bandPassedY));
            prevY = lowPassedY;
        }
        return filteredPoints;
    }

    public static List<FrequencyPoint> getDominantFrequencies(List<FrequencyPoint> transformedPoints, int numPeaks) {
        List<FrequencyPoint> peaks = new ArrayList<>();
        for (int i = 1; i < transformedPoints.size() - 1; i++) {
            FrequencyPoint prev = transformedPoints.get(i - 1);
            FrequencyPoint current = transformedPoints.get(i);
            FrequencyPoint next = transformedPoints.get(i + 1);

            if (current.getMagnitude() > prev.getMagnitude() && current.getMagnitude() > next.getMagnitude()) {
                peaks.add(current);
            }
        }

        peaks.sort((a, b) -> Double.compare(b.getMagnitude(), a.getMagnitude()));

        List<FrequencyPoint> filteredPeaks = new ArrayList<>();
        for (FrequencyPoint peak : peaks) {
            boolean isHarmonic = false;
            for (FrequencyPoint selectedPeak : filteredPeaks) {
                double harmonicTolerance = 0.05 * selectedPeak.getFrequency();
                if (Math.abs(peak.getFrequency() - selectedPeak.getFrequency()) < harmonicTolerance || 
                    Math.abs(peak.getFrequency() - 2 * selectedPeak.getFrequency()) < harmonicTolerance) {
                    isHarmonic = true;
                    break;
                }
            }
            if (!isHarmonic) {
                filteredPeaks.add(peak);
            }
            if (filteredPeaks.size() >= numPeaks) {
                break;
            }
        }

        return filteredPeaks;
    }
    
    private static int nextPowerOfTwo(int n) {
        int power = 1;
        while (power < n) {
            power <<= 1;
        }
        return power;
    }
}
