package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    
    public static List<String> readAllLines(String filename) {
        List<String> lines = new ArrayList<>();
        File file = new File(filename);
        
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Error creating file: " + e.getMessage());
            }
            return lines;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return lines;
    }
    
    public static void writeAllLines(String filename, List<String> lines) {
        File file = new File(filename);
        file.getParentFile().mkdirs();
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}