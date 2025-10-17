package data;

import java.io.IOException;
import java.awt.Point; 
import java.io.*; 
import java.util.ArrayList; 
import java.util.List;
import java.nio.file.*;

//File reader developed by Oleksandr Hryhorenko

public class FileReader {
	
	public List<Point> readPointsFromFile() {
        List<Point> points = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(Paths.get("./resources/pairs.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(","); 
                int x = Integer.parseInt(parts[0].trim());
                int y = Integer.parseInt(parts[1].trim());
                points.add(new Point(x, y));
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }

        return points;

	}   

}