package VolunteerLink;

// Java Imports
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

// MongoDB Imports
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.lt;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import org.bson.types.ObjectId;

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
            EventManager eventManager = new EventManager(mongoClient, database);

            MongoCollection<Document> Events = database.getCollection("Events");


            


            
            
            
            // Testing section for Colin
             if (true) {
            //Event testEvent = new Event("eventName", "description", "startDate", "endDate", "location", 2, 1, "Pending");
            //Document testDocument = testEvent.toDocument();
            //Events.insertOne(testDocument); 
            User testUser = new User(mongoClient, database);
            //System.out.println(testUser.getEvents());
            //testUser.viewEventNames();
            String[] arr = testUser.getPendingEvents();
            for (int i = 0; i < arr.length - 1; ++i) {
                System.out.println("EventPending: " + i + " " + arr[i]);
            }
            //testUser.viewEventNames();


            } 
            // Colin testing section end
        }


            

           


            // These tests will need to be moved to a proper testing suite


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

            // ----------------- Event Creation Test -----------------
            // Document event = new Document("name", "Sample Event")
            //     .append("description", "This is a sample event.")
            //     .append("startDate", "2021-12-31")
            //     .append("endDate", "2022-1-1")
            //     .append("location", "Sample Location")
            //     .append("volunteersNeeded", 10)
            //     .append("volunteersRegistered", 0)
            //     .append("eventStatus", "Pending");

            // eventManager.addEvent(event);
            // ----------------- Event Creation Test -----------------

            // ----------------- Event Deletion Test -----------------
            // eventManager.deleteEvent("21");
            // ----------------- Event Deletion Test -----------------
            

        }
    }

