package data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class PairGenerator {

    public PairGenerator(int nPairs) {
        // Example usage
        String directoryPath = "./resources"; // Change to your desired directory
        String fileName = "pairs.txt"; // Desired file name
        int n = nPairs; // Number of pairs to generate

        generatePairs(directoryPath, fileName, n);
    }

    public static void generatePairs(String directoryPath, String fileName, int n) {
        // Create the directory if it does not exist
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs(); // Create directories if they do not exist
        }

        // Create the file
        File file = new File(directory, fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            Set<Integer> uniqueNumbers = new HashSet<>();
            Random random = new Random();

            for (int i = 1; i <= n; i++) {
                // Generate a unique random number between 1 and n
                int randomNumber;
                do {
                    randomNumber = random.nextInt(n) + 1; // Generate numbers from 1 to n
                } while (uniqueNumbers.contains(randomNumber));

                // Add the generated number to the set
                uniqueNumbers.add(randomNumber);

                // Write the pair to the file
                writer.write(i + "," + randomNumber);
                writer.newLine(); // Move to the next line
            }

            System.out.println("Pairs generated and written to " + file.getAbsolutePath());

        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}

