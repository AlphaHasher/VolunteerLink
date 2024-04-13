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

        // Optimization checker
        long startTime = System.nanoTime();


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


            System.out.println("Connected to the database.");

            // Event event = new Event(mongoClient, database);
            // String[] pendingEvents = event.getPendingEvents();
            // for (int i = 0; i < pendingEvents.length; i++) {
            //     System.out.println(pendingEvents[i]);
            // }

            // Testing section for Colin
            if (true) {
                //Event testEvent = new Event("eventName", "description", "startDate", "endDate", "location", 2, 1, "Pending"); // test import
                //Document testDocument = testEvent.toDocument();
                //Events.insertOne(testDocument);
                Event testEvent = new Event(mongoClient, database);
                //System.out.println(testUser.getEvents());
                //testUser.viewEventNames();
                String[] arr = testEvent.getLocations();
                for (int i = 0; i < arr.length - 1; ++i) {
                    System.out.println("EventLocations: " + i + " " + arr[i]);
                }
                //testUser.viewEventNames();
            }
            // Colin testing section end

            // ----------------- User get/set Priority Test -----------------
            // System.out.println(admin.getPriority("1"));

            // admin.elevatePriority("1");
            // System.out.println(admin.getPriority("1"));
            // ----------------- User get/set Priority Test -----------------

            // System.out.println(admin.getUsers());

            // ----------------- Event Creation Test -----------------
            // Document event = new Document("name", "Sample Event")
            //     .append("description", "This is a sample event.")
            //     .append("startDate", "2017-02-08T12:30:00")
            //     .append("endDate", "2017-02-08T20:10:00")
            //     .append("location", "Sample Location")
            //     .append("volunteersNeeded", 10)
            //     .append("volunteersRegistered", 0)
            //     .append("eventStatus", "Pending");

            // eventManager.addEvent(event);
            // ----------------- Event Creation Test -----------------

            // ----------------- Event Deletion Test -----------------
            // eventManager.deleteEvent("21");
            // ----------------- Event Deletion Test -----------------

            // ----------------- Event Approval Test -----------------
            // admin.denyEvent("Sample Event");
            // admin.approveEvent("Sample Event");
            // ----------------- Event Approval Test -----------------
        }
    }
}
