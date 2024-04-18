package VolunteerLink;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Arrays;
import java.util.Date;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Aggregates;
import static com.mongodb.client.model.Filters.eq;

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

    // User methods

    // Create a new user
    public void createUser(Document user) {
        usersCollection.insertOne(user);
    }

    // Work in progress
    public String logInUser(String userName) {
        Document filter = new Document("email", userName); // Assuming the field name for userName is "userName"
        MongoCursor<Document> cursor = usersCollection.aggregate(
            Arrays.asList(
                Aggregates.match(filter), // Filter documents based on userName
                Aggregates.project(Projections.fields(Projections.excludeId(), Projections.include("_id"))) // Project only the _id field
            )
        ).iterator();

        try {
            if (cursor.hasNext()) {
                Document doc = cursor.next();
                return doc.getObjectId("_id").toString(); // Return the _id as a string
            } else {
                return null; // User not found
            }
        } finally {
            cursor.close();
        }
    }

    // Event methods

    //  *** Keeping for the sake of the example of how to use the aggregation framework ***

    // public String[] getEventStatuses() {
    //     List<String> eventStatuses = new ArrayList<>();
    //     MongoCursor<Document> cursor = eventCollection.aggregate(
    //         Arrays.asList(
    //             Aggregates.sort(Sorts.ascending("_id")), // Sort by _id in ascending order
    //             Aggregates.group("$_id", Accumulators.first("eventStatus", "$eventStatus")) // Group by _id
    //         )
    //     ).iterator();

    //     try {
    //         while (cursor.hasNext()) {
    //             Document doc = cursor.next();
    //             String id = doc.getString("eventStatus");
    //             eventStatuses.add(id);
    //         }
    //     } finally {
    //         cursor.close();
    //     }

    //     return eventStatuses.toArray(new String[0]);
    // }


    public void addEvent(Document event){
        event.append("eventStatus", "Pending"); // Pending, Denied, Scheduled, Ongoing, Ended
        event.append("approved", false);
        event.append("whenCreated", new Date());
        eventCollection.insertOne(event);
    }

    public void deleteEvent(String id){
        ObjectId objectId = new ObjectId(id);
        eventCollection.deleteOne(eq("_id", objectId));

        // Delete all Event Roles associated with the event
        eventRolesCollection.deleteMany(eq("eventId", id));
    }
}
