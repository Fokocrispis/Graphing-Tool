package data;

import javax.sound.sampled.*;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Audio {
    private List<Point> samples;

    public Audio(String audioFile) {
        File file = new File("./resources/" + audioFile);
        try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file)) {
            AudioFormat format = audioInputStream.getFormat();
            int bytesPerFrame = format.getFrameSize();
            int numChannels = format.getChannels();
            int bytesPerSample = format.getSampleSizeInBits() / 8;
            long numFrames = audioInputStream.getFrameLength();

            byte[] audioBytes = new byte[(int) (numFrames * bytesPerFrame)];
            int bytesRead = 0;
            int totalBytesRead = 0;

            // Properly read the entire file
            while (totalBytesRead < audioBytes.length) {
                bytesRead = audioInputStream.read(audioBytes, totalBytesRead, audioBytes.length - totalBytesRead);
                if (bytesRead == -1) break; // End of stream
                totalBytesRead += bytesRead;
            }

            samples = new ArrayList<>();

            // Extract samples from audioBytes, assuming mono
            for (int i = 0; i < audioBytes.length / (bytesPerSample * numChannels); i++) {
                int index = i * bytesPerSample * numChannels;
                int low = audioBytes[index] & 0xFF;
                int high = audioBytes[index + 1] & 0xFF;

                int sampleValue = (high << 8) | low;

                // Convert from unsigned to signed if necessary
                if (sampleValue > 32767) {
                    sampleValue -= 65536;
                }

                samples.add(new Point(i, sampleValue));
            }
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    public List<Point> getAudio() {
        return samples;
    }
}

