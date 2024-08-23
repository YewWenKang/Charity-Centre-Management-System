package utility;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import ADT.ListInterface;
import java.io.BufferedReader;
import java.io.FileReader;

public class SaveFile {

    public static <T> void saveToFile(String fileName, ListInterface<T> list, String header) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Write the header
            writer.write(header);
            writer.newLine();

            // Write the data
            for (int i = 1; i <= list.getNumberOfEntries(); i++) {
                writer.write(list.getEntry(i).toString());
                writer.newLine();
            }
            System.out.println("Data saved successfully to " + fileName);
        } catch (IOException e) {
            System.err.println("Error saving data to file: " + e.getMessage());
        }
    }

    // Method to load data from file
    public static <T> void loadFromFile(String fileName, ListInterface<T> list, DataParser<T> parser) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            // Read and discard the header line
            String header = reader.readLine();

            // Read and parse the data
            String line;
            while ((line = reader.readLine()) != null) {
                T data = parser.parse(line);
                list.add(data);  // Assumes LinkedList's add method is available
            }
            System.out.println("Data loaded successfully from " + fileName);
        } catch (IOException e) {
            System.err.println("Error loading data from file: " + e.getMessage());
        }
    }
    
    // Functional interface for parsing each line of the CSV
    @FunctionalInterface
    public interface DataParser<T> {
        T parse(String line);
    }

}
