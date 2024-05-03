package VolunteerLink;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Properties;
import java.util.Date;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Updates;
import static com.mongodb.client.model.Filters.eq;

public class Database {

    private static volatile Database instance;
    private MongoClient mongoClient;
    private static MongoDatabase database;

    // New variables to support collection aggregation through the Database class
    private static MongoCollection<Document> eventCollection;
    private static MongoCollection<Document> eventRolesCollection;
    private static MongoCollection<Document> usersCollection;

    private static String getKey(String key){
        try (InputStream input = Database.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties\nVerify that the config.properties file is in the /src/main/java/resources folder.");
                return null;
            }
            prop.load(input);
            key = prop.getProperty(key);
            return key;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private Database() {
        mongoClient = MongoClients.create(getKey("MongoDBKey"));
        database = mongoClient.getDatabase(getKey("DATABASE_NAME"));

        // New collection
        eventCollection = database.getCollection("Events");
        eventRolesCollection = database.getCollection("Event Roles");
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


    public MongoDatabase getDatabase() {
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
    public MongoCollection<Document> getUsersCollection() {
        getInstance();
        return usersCollection;
    }

    // User methods

    // Delete a user
    public void deleteUser(String id) {
        ObjectId objectId = new ObjectId(id);
        usersCollection.deleteOne(eq("_id", objectId));

        // Delete all id's of the user from assignedUsers fields in eventRolesCollection
        eventRolesCollection.updateMany(eq("assignedUsers", objectId), new Document("$pull", new Document("assignedUsers", objectId)));

        // Decrement volunteersRegistered in eventCollection for all events the user is registered for, and increment volunteersNeeded for those events
        MongoCursor<Document> cursor = eventCollection.find(eq("volunteersRegistered", objectId)).iterator();
        try {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                ObjectId eventId = doc.getObjectId("_id");
                eventCollection.updateOne(eq("_id", eventId), new Document("$inc", new Document("volunteersRegistered", -1)));
                eventCollection.updateOne(eq("_id", eventId), new Document("$inc", new Document("volunteersNeeded", 1)));
            }
        } finally {
            cursor.close();
        }
    }
    // "Revokes" a user's role. Updates role to volunteer if currently event organizer, and deletes account if volunteer.
    public void revokeUser(String id) {
        // Grabs current role from the database
        String currentRole = Utility.getFieldValueFromDocument("Users", id, "role", String.class);
        
        if (currentRole.equals("Event Organizer")) {
            String newRole = "Volunteer";
            Utility.updateFieldInDocument("Users", id, "role", newRole);
            System.out.println("Now Volunteer");
        }
        if (currentRole.equals("Volunteer")) {
            String newRole = "Deleted";
            deleteUser(id);
            System.out.println("Volunteer Deleted");
        }
        System.out.println("Previous Role: " + currentRole);
    }
    // Increases a user's role Volunteer -> Event Organizer, Event Organizer -> admin
    public void promoteUser(String id) {
        // Grabs current role from the database
        String currentRole = Utility.getFieldValueFromDocument("Users", id, "role", String.class);
        
        if (currentRole.equals("Volunteer")) {
            String newRole = "Event Organizer";
            Utility.updateFieldInDocument("Users", id, "role", newRole);
        }
        if (currentRole.equals("Event Organizer")) {
            String newRole = "Admin";
            Utility.updateFieldInDocument("Users", id, "role", newRole);
        }
    }

     
    // Event methods

    public void addEvent(Document event){
        event.append("eventStatus", "Pending"); // Pending, Denied, Scheduled, Ongoing, Ended
        event.append("approved", false);
        event.append("whenCreated", new Date());
        eventCollection.insertOne(event);
    }

    public static void deleteEvent(String id){
        ObjectId objectId = new ObjectId(id);
        eventCollection.deleteOne(eq("_id", objectId));

        // Delete all Event Roles associated with the event
        eventRolesCollection.deleteMany(eq("eventId", objectId));

        // Delete all event role id's from users in their eventRole_id field
        usersCollection.updateMany(eq("eventRole_id", id), Updates.unset("eventRole_id"));
    }

    public void approveEvent(String id){
        ObjectId objectId = new ObjectId(id);
        eventCollection.updateOne(eq("_id", objectId), Updates.set("approved", true));
        eventCollection.updateOne(eq("_id", objectId), Updates.set("eventStatus", "Scheduled"));
    }

    public void denyEvent(String id){
        ObjectId objectId = new ObjectId(id);
        eventCollection.updateOne(eq("_id", objectId), Updates.set("approved", false));
        eventCollection.updateOne(eq("_id", objectId), Updates.set("eventStatus", "Denied"));
    }

    public void addEventRole(Document eventRole){
        eventRolesCollection.insertOne(eventRole);
    }

    public void deleteEventRole(String id){
        ObjectId objectId = new ObjectId(id);

        // get the number of assigned users and number needed for the event role to update the event collection
        Document eventRoleDoc = eventRolesCollection.find(eq("_id", objectId)).first();
        if (eventRoleDoc != null) {
            int numberAssigned = eventRoleDoc.getInteger("numberAssigned");
            int numberNeeded = eventRoleDoc.getInteger("numberNeeded");
            ObjectId eventId = eventRoleDoc.getObjectId("eventId");

            eventRolesCollection.deleteOne(eq("_id", objectId));

            // Update event collection to reflect the removal of this event role
            updateEventCollection(eventId, -numberAssigned, numberNeeded);

            // Delete all id's of the event role from assignedRoles fields in usersCollection
            usersCollection.updateMany(eq("eventRole_id", id), Updates.unset("eventRole_id"));
        }
    }

    public void registerForEventRole(ObjectId userId, ObjectId eventRoleId) {
        updateEventRoles(eventRoleId, 1, userId);
        updateEventCollection(eventRoleId, 1, -1);
        updateUserCollection(userId, eventRoleId, true);
    }

    public void unregisterForEventRole(ObjectId userId, ObjectId eventRoleId) {
        updateEventRoles(eventRoleId, -1, userId);
        updateEventCollection(eventRoleId, -1, 1);
        updateUserCollection(userId, eventRoleId, false);
    }

    // This method updates the event role's assignedUsers, numberAssigned, and numberNeeded fields when a user registers/unregisters for an event role
    private void updateEventRoles(ObjectId eventRoleId, int delta, ObjectId userId) {
        eventRolesCollection.updateOne(eq("_id", eventRoleId), Updates.combine(
            Updates.push("assignedUsers", delta > 0 ? userId : null),
            Updates.pull("assignedUsers", delta < 0 ? userId : null),
            Updates.inc("numberAssigned", delta),
            Updates.inc("numberNeeded", -delta)
        ));
    }

    // This method updates the event's volunteersRegistered and volunteersNeeded fields when a user registers/unregisters for an event role
    private void updateEventCollection(ObjectId eventId, int numberAssignedDelta, int numberNeededDelta) {
        eventCollection.updateOne(eq("_id", eventId), Updates.combine(
            Updates.inc("volunteersRegistered", numberAssignedDelta),
            Updates.inc("volunteersNeeded", numberNeededDelta)
        ));
    }
    public static void updateEvent(Event event, String eventName,String location, String description, Date startDate, Date endDate) {

        
        eventCollection.updateOne(eq("_id", event.getId()), Updates.combine(
            Updates.set("eventName", eventName),
            Updates.set("location", location),
            Updates.set("description", description),
            Updates.set("startDate", startDate),
            Updates.set("endDate", endDate)
            //adding more parameters
        ));
    }

    // This method updates the user's eventRole_id field when they register/unregister for an event role
    private void updateUserCollection(ObjectId userId, ObjectId eventRoleId, boolean isRegistering) {
        if (isRegistering) {
            usersCollection.updateOne(eq("_id", userId), Updates.set("eventRole_id", eventRoleId));
        } else {
            usersCollection.updateOne(eq("_id", userId), Updates.unset("eventRole_id"));
        }
    }
}
