package vector5.suppliers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Provides a list of suppliers subscribed to quote requests.
 */
public final class SupplierContactsProvider {

    /** Private constructor to prevent instantiation. */
    private SupplierContactsProvider() {}

    private static final String SUPPLIERS_LIST_FILE = "data/suppliers.json";

    /**
     * Gets the list of suppliers subscribed to quote requests.
     * @return An array of SupplierContact.
     */
    public static SupplierContact[] getSubscribedSuppliers() {
        // Read suppliers list file content
        String jsonData = "";
        try {
            jsonData = Files.readString(Path.of(SUPPLIERS_LIST_FILE));
        } catch (IOException e) {
            System.err.println("Could not read list of suppliers.");
            e.printStackTrace();
        }

        return readSuppliersFromJson(jsonData);
    }

    /**
     * Extracts a list of suppliers from the given JSON.
     * @param jsonData the JSON data containing contact information
     * @return A list of SupplierContact.
     */
    public static SupplierContact[] readSuppliersFromJson(String jsonData) {
        // Read users from json
        JSONArray jaUsers = new JSONArray(jsonData);

        // Init output list
        int length = jaUsers.length();
        SupplierContact[] suppliers = new SupplierContact[length];

        for (int i = 0; i < length; i++) {
            JSONObject user = jaUsers.getJSONObject(i);

            SupplierContact supplier = new SupplierContact(
                    user.getString("name"),
                    user.getString("email"));

            suppliers[i] = supplier;
        }

        return suppliers;
    }
}
