package DAO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FileDao<T> {
    private static final String FILE_NAME = System.getProperty("DAO") + "/donorData.csv";


    // Method to save data to a CSV file
    public void writeDataToCSV(String fileName, List<String> headers, List<T> data, Function<T, List<String>> rowMapper) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Write the headers
            writeLine(writer, headers);

            // Write the data
            for (T item : data) {
                writeLine(writer, rowMapper.apply(item));
            }

            System.out.println("Data saved to " + fileName + " successfully.");

        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Helper method to write a single line to the file
    private void writeLine(BufferedWriter writer, List<String> values) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            sb.append(escapeSpecialCharacters(values.get(i)));
            if (i < values.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("\n");
        writer.write(sb.toString());
    }

    // Helper method to escape special characters in CSV values
    private String escapeSpecialCharacters(String value) {
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            value = "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    // General method to load data from a CSV file
    public List<T> loadDataFromCSV(String fileName, Function<String[], T> rowMapper) {
        List<T> dataList = new ArrayList<>();
        File file = new File(fileName);

        // Create file if it doesn't exist
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("File " + fileName + " created as it did not exist.");
                }
            } catch (IOException e) {
                System.err.println("An error occurred while creating the file: " + e.getMessage());
                e.printStackTrace();
                return dataList;
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstLine = true; // Track header line
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // Skip header line
                    continue;
                }
                String[] values = line.split(",");
                T data = rowMapper.apply(values);
                if (data != null) {
                    dataList.add(data);
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
            e.printStackTrace();
        }
        return dataList;
    }
}
