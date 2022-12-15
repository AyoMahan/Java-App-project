package vector5.driver;

import vector5.suppliers.SupplierContact;
import vector5.csvhandler.CsvWriter;
import vector5.emailsending.SendEmail;
import vector5.suppliers.SupplierContactsProvider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainSendQuoteRequests {

    public static void main(String[] args) {
        try {
            run("tmp/test-json.json");
        } catch (IOException e) {
            System.err.println("Error reading or writing file");
            e.printStackTrace();
        }
    }

    /**
     * Send quote requests to suppliers:
     * - Converts the JSON data into CSV;
     * - Reads a list of recipient emails from the JSON;
     * - Sends an email to the recipients with the CSV file attached.
     * @param jsonInputFile path to the input JSON file
     * @throws IOException Error reading or writing file.
     */
    private static void run(String jsonInputFile) throws IOException {
        Path csvFilePath = Paths.get("tmp/items.csv");

        // Receive JSON input from html form
        // TODO: receive JSON input from html form. For now, read file.
        String jsonData = Files.readString(Path.of(jsonInputFile));

        // Create CSV attachment for items
        CsvWriter.printCsvFileFromJson(jsonData, csvFilePath);

        // Get list of destination emails
        SupplierContact[] recipients = SupplierContactsProvider.getSubscribedSuppliers();

        // Send emails
        for (var recipient : recipients) {
            SendEmail.sendEmail(recipient.getEmail(), recipient.getName(), csvFilePath);
        }
    }
}
