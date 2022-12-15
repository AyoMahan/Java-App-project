package vector5.csvhandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Path;

public final class CsvWriter {

    private static final String CSV_HEADER = "Product Name,Quantity,Unit of Measurement,Price";

    /** Private constructor to prevent instantiation. */
    private CsvWriter() {}

    /**
     * Reads data from a JSON formatted file and writes a CSV output file.
     * @param jsonData String containing the data in JSON format
     * @param csvOutputFilePath path to the CSV output file
     */
    public static void printCsvFileFromJson(String jsonData, Path csvOutputFilePath) {
        String csvOutput = convertJsonToCsv(jsonData);
        try {
            Files.write(csvOutputFilePath, csvOutput.getBytes());
        } catch (Exception e) {
            System.err.println("Couldn't write to csv file");
            e.printStackTrace();
        }
    }

    public static String convertJsonToCsv(String jsonData) {
        //load items from json
        JSONObject jsonContent = new JSONObject(jsonData);
        JSONArray items = jsonContent.getJSONArray("Items");

        StringBuilder csvStringBuilder = new StringBuilder();

        // Print header row
        csvStringBuilder.append(CSV_HEADER).append("\n");

        // Print csv row for each item
        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);

            String csvRow = formatItemAsCsvRow(
                    item.getString("ID"),
                    item.getString("UoM"),
                    item.getString("qty"),
                    "");

            csvStringBuilder.append(csvRow).append("\n");
        }

        return csvStringBuilder.toString();
    }

    private static String formatItemAsCsvRow(
            String productName,
            String quantity,
            String unitOfMeasurement,
            String price
    ) {
        return String.format("%s,%s,%s,%s", productName, quantity, unitOfMeasurement, price);
    }
}
