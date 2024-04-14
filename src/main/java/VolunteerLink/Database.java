package VolunteerLink;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

// New imports to test aggregation/search methods
import java.util.List;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import java.util.Arrays;
import org.bson.Document;
import org.bson.types.ObjectId;
import com.mongodb.client.MongoCollection; 
import com.mongodb.client.model.Sorts;

public class Database {

    private static volatile Database instance;
    private MongoClient mongoClient;
    private static MongoDatabase database;

    private static final String DATABASE_NAME = "VolunteerLink";

    // New variables to support collection aggregation through the Database class
    private static MongoCollection<Document> eventCollection;
    private static MongoCollection<Document> eventRolesCollection;
    private static MongoCollection<Document> tasksCollection;
    private static MongoCollection<Document> usersCollection;

    private Database() {
        String uri;
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties\nVerify that the config.properties file is in the VolunteerLink/src/main/java folder.");
                return;
            }
            prop.load(input);
            uri = prop.getProperty("MongoDBKey");
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        mongoClient = MongoClients.create(uri);
        database = mongoClient.getDatabase(DATABASE_NAME);

        // New collection 
        eventCollection = database.getCollection("Events");
        eventRolesCollection = database.getCollection("Event Roles");
        tasksCollection = database.getCollection("Tasks");
        usersCollection = database.getCollection("Users");
    }

    // Most optimized?
    public static synchronized Database getInstance() {
        Database result = instance;

        if (result == null) {
            synchronized (Database.class) {
                result = instance;

                if (result == null) {
                    instance = result = new Database();
                }
            }
        }
        return result;
    }

    
    public MongoDatabase getDatabase() { // This method may need to be removed, all interaction with MongoDB should be done through the "Database" class, though this method is public
        return database;
    }
    // Methods to return collections
    public MongoCollection<Document> getEventCollection() {
        getInstance();
        return eventCollection;
    }
    public MongoCollection<Document> getEventRolesCollection() {
        getInstance();
        return eventRolesCollection;
    }
    public MongoCollection<Document> getTasksCollection() {
        getInstance();
        return tasksCollection;
    }
    public MongoCollection<Document> getUsersCollection() {
        getInstance();
        return usersCollection;
    }



    // Methods to return an array of event Details

    // Returns an array of event names
    // Deprecated, but keeping as reference.
    public String[] getLocations() {
        List<String> locations = new ArrayList<>();
        MongoCursor<Document> cursor = eventCollection.aggregate(
            Arrays.asList(
                Aggregates.sort(Sorts.ascending("_id")),
                Aggregates.group("$location", Accumulators.first("location", "$location")) // Group by location
                , Aggregates.project(new Document("_id", 0).append("location", "$_id")) // Project to get location names
            )
        ).iterator();

        try {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                locations.add(doc.getString("location"));
            }
        } finally {
            cursor.close();
        }

        return locations.toArray(new String[0]);
    } 
    public String[] getEventLocations() {
        List<String> eventLocations = new ArrayList<>();
        MongoCursor<Document> cursor = eventCollection.aggregate(
            Arrays.asList(
                Aggregates.sort(Sorts.ascending("_id")), // Sort by _id in ascending order
                Aggregates.group("$_id", Accumulators.first("location", "$location")) // Group by _id
            )
        ).iterator();
    
        try { 
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                String location = doc.getString("location");
                eventLocations.add(location);
            }
        } finally {
            cursor.close();
        }
    
        return eventLocations.toArray(new String[0]);
    }
    // Returns an array of event descriptions
    public String[] getEventDescriptions() {
        List<String> eventDescriptions = new ArrayList<>();
        MongoCursor<Document> cursor = eventCollection.aggregate(
            Arrays.asList(
                Aggregates.sort(Sorts.ascending("_id")), // Sort by _id in ascending order
                Aggregates.group("$_id", Accumulators.first("eventDescription", "$eventDescription")) // Group by _id
            )
        ).iterator();
    
        try { 
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                String description = doc.getString("eventDescription");
                eventDescriptions.add(description);
            }
        } finally {
            cursor.close();
        }
    
        return eventDescriptions.toArray(new String[0]);
    }
    // Returns an array of event names
    public String[] getEventNames() {
        List<String> eventNames = new ArrayList<>();
        MongoCursor<Document> cursor = eventCollection.aggregate(
            Arrays.asList(
                Aggregates.sort(Sorts.ascending("_id")), // Sort by _id in ascending order
                Aggregates.group("$_id", Accumulators.first("eventName", "$eventName")) // Group by _id
            )
        ).iterator();
    
        try { 
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                String description = doc.getString("eventName");
                eventNames.add(description);
            }
        } finally {
            cursor.close();
        }
    
        return eventNames.toArray(new String[0]);
    }
    public ObjectId[] getEventIds() {
        List<ObjectId> eventIds = new ArrayList<>();
        MongoCursor<Document> cursor = eventCollection.aggregate(
            Arrays.asList(
                Aggregates.sort(Sorts.ascending("_id")), // Sort by _id in ascending order
                Aggregates.group("$_id") // Group by _id
            )
        ).iterator();
    
        try { 
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                ObjectId id = doc.getObjectId("_id");
                eventIds.add(id);
            }
        } finally {
            cursor.close();
        }
    
        return eventIds.toArray(new ObjectId[0]);
    }
    public String[] getEventStatuses() {
        List<String> eventStatuses = new ArrayList<>();
        MongoCursor<Document> cursor = eventCollection.aggregate(
            Arrays.asList(
                Aggregates.sort(Sorts.ascending("_id")), // Sort by _id in ascending order
                Aggregates.group("$_id", Accumulators.first("eventStatus", "$eventStatus")) // Group by _id
            )
        ).iterator();
    
        try { 
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                String id = doc.getString("eventStatus");
                eventStatuses.add(id);
            }
        } finally {
            cursor.close();
        }
    
        return eventStatuses.toArray(new String[0]);
    }
    // Generic method to return array of any specified field in the event collection.
    // We should use this in the future to reduce redundant code
    public String[] getEventField(String field) { 
        List<String> eventField = new ArrayList<>();
        MongoCursor<Document> cursor = eventCollection.aggregate(
            Arrays.asList(
                Aggregates.sort(Sorts.ascending("_id")), // Sort by _id in ascending order
                Aggregates.group("$_id", Accumulators.first("field", "$" + field)) // Group by _id
            )
        ).iterator();
    
        try { 
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                String id = doc.getString("field");
                eventField.add(id);
            }
        } finally {
            cursor.close();
        }
    
        return eventField.toArray(new String[0]);
    }
    // Returns an array of pending event's names
    public String[] getPendingEvents() {
        List<String> eventNames = new ArrayList<>();
        try (MongoCursor<Document> cursor = eventCollection.find(eq("eventStatus", "Pending")).iterator()) {
            while (cursor.hasNext()) {
                Document event = cursor.next();
                eventNames.add(event.getString("eventName"));
            }
        }
        return eventNames.toArray(new String[0]);
    }




}
