package DAO;

import ADT.LinkedList;
import ADT.ListInterface;
import java.io.*;
import java.util.List;
import java.util.function.Function;

public class FileDao<T> {

    public ListInterface<T> loadDataFromCSV(String fileName, Function<String[], T> mapper) {
        ListInterface<T> list = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false; // Skip the header line
                    continue;
                }
                String[] values = parseCSVLine(line);
                T item = mapper.apply(values);
                if (item != null) {
                    list.add(item);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void writeDataToCSV(String fileName, List<String> headers, ListInterface<T> data, Function<T, List<String>> mapper) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println(String.join(",", headers));
            for (int i = 0; i < data.size(); i++) {
                T item = data.get(i);
                if (item != null) {
                    List<String> row = mapper.apply(item);
                    writer.println(escapeCSV(row));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] parseCSVLine(String line) {
        return line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); // Splits on commas, but not within quotes
    }

    private String escapeCSV(List<String> fields) {
        StringBuilder escaped = new StringBuilder();
        for (int i = 0; i < fields.size(); i++) {
            String field = fields.get(i);
            if (field.contains(",") || field.contains("\"")) {
                field = "\"" + field.replace("\"", "\"\"") + "\"";
            }
            escaped.append(field);
            if (i < fields.size() - 1) {
                escaped.append(",");
            }
        }
        return escaped.toString();
    }
}
