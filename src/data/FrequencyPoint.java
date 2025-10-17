package data;

public class FrequencyPoint {
    private final double frequency;
    private double magnitude;

    public FrequencyPoint(double frequency, double magnitude) {
        this.frequency = frequency;
        this.magnitude = magnitude;
    }

    public double getFrequency() {
        return frequency;
    }

    public double getMagnitude() {
        return magnitude;
    }

    @Override
    public String toString() {
        return "Frequency: " + frequency + " Hz, Magnitude: " + magnitude;
    }
    
    public void setMagnitude(double magnitude) {
    	this.magnitude=magnitude;
    }
}