package VolunteerLink;

// Java Imports
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

// MongoDB Imports
import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class App
{
    public static void main( String[] args ) {
        // Store the MongoDB URI
        String uri = "";

        try (InputStream input = App.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties\nVerify that the config.properties file is in the VolunteerLink/scr/main/java folder.");
                return;
            }
            prop.load(input);
            uri = prop.getProperty("MongoDBKey");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Connecting to the MongoDB database
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("VolunteerLink");
            Admin admin = new Admin(mongoClient, database);

            // ----------------- Event Approval Test -----------------
            // admin.denyEvent("Sample Event");
            // admin.approveEvent("Sample Event");
            // ----------------- Event Approval Test -----------------

            // ----------------- User get/set Priority Test -----------------
            // System.out.println(admin.getPriority("1"));

            // admin.elevatePriority("1");
            // System.out.println(admin.getPriority("1"));
            // ----------------- User get/set Priority Test -----------------

            // System.out.println(admin.getUsers());
        }
    }
}
