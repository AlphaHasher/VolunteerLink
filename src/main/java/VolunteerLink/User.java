package VolunteerLink;

import org.bson.Document;
// import static com.mongodb.client.model.Filters.eq;
// import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
// import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
// import com.mongodb.client.model.Filters;
// import com.mongodb.client.model.UpdateOptions;
// import com.mongodb.client.model.Updates;
// import com.mongodb.client.result.UpdateResult;

// import java.util.ArrayList;
// import java.util.List;
import com.mongodb.client.MongoCursor;
import java.util.Date;

public class User {

    private MongoClient mongoClient;
    private MongoCollection<Document> eventCollection;
    private MongoDatabase database;

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
    private Date registrationDate;

    public User(MongoClient mongoClient, MongoDatabase database) {
        try {
            this.mongoClient = mongoClient;
            this.eventCollection = database.getCollection("Events");
            this.database = database;
        } catch (Exception e) {
            System.err.println("Failed to initialize User with database connection: " + e.getMessage());
            e.printStackTrace();
        }
    }


    // returns every event in the database (will need to implement a way to filter later)
    public String getEvents() {
        StringBuilder eventsString = new StringBuilder();
        Iterable<Document> documents = eventCollection.find();

        for (Document document : documents) {
            eventsString.append(document.toString()).append("\n");
        }
        return eventsString.toString();
    }

    //  TODO: make registrationDate a Date object
    public User(String email, String password, String firstName, String lastName, String role, Date registrationDate){
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.registrationDate = registrationDate;
    }

    public User(){
        this.role = "volunteer";
    }

    public String getName(){
        return firstName + " " + lastName;
    }

    public void setName(String name){
        String[] names = name.split(" ");
        this.firstName = names[0];
        this.lastName = names[1];
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void viewEvents(){
        Iterable<Document> documents = eventCollection.find();
        for (Document document : documents) {
            System.out.println(document.toString());
        }
    }

    // Event Parsing created by Colin, may update in future to automatically sort by most recent.
    // Currently sorted by most recently imported (I believe)
    // Displays/prints a list of all eventNames in DB
    // Inefficient but works, perhaps there's a way to do the same function without iterating twice, which we may improve once everything's functional
    // Will update to reduce redundancy.

    public void viewEventNames() { // Might want to create a cursor variable/object for the entire class to avoid redundancy. Testing needs to be done to check if the cursor "resets" each time
        int count = 0;
        Iterable<Document> documents = eventCollection.find();
        for (Document document : documents) {
            count ++;
        }
        MongoCursor<Document> cursor = eventCollection.find().iterator();
        while (cursor.hasNext()) {
            Document testDoc = cursor.next();
            Object eventName = testDoc.get("eventName");
            System.out.println(eventName);
            cursor.close();

        }
    }

    public String[] getEventNames() { // returns an Array of all EventNames in DB
        int count = 1;
        Iterable<Document> documents = eventCollection.find();
        MongoCursor<Document> cursor = eventCollection.find().iterator();
        for (Document document : documents) {
            count ++;
        }
        String[] eventNameArr = new String[count];
        int i = 0;
        while (cursor.hasNext()) { // Used MongoCursor object to iterate over docs in Events collection
            Document testDoc = cursor.next();
            String eventName = testDoc.get("eventName") + ""; // Converts the eventName in DB from Object to String (DB imports from MongoCursor defaults to Object)
            eventNameArr[i] = eventName;
            i++;
        }
        return eventNameArr;
    }
    public String[] getEventDescriptions() { // returns an Array of all eventDescriptions in DB
        int count = 1;
        Iterable<Document> documents = eventCollection.find();
        MongoCursor<Document> cursor = eventCollection.find().iterator();
        for (Document document : documents) {
            count ++;
        }
        String[] eventDescArr = new String[count];
        int i = 0;
        while (cursor.hasNext()) { // Used MongoCursor object to iterate over docs in Events collection
            Document testDoc = cursor.next();
            String eventDesc = testDoc.get("eventDescription") + ""; // Converts the eventDescription in DB from Object to String (DB imports from MongoCursor defaults to Object)
            eventDescArr[i] = eventDesc;
            i++;
        }
        return eventDescArr;
    }
    public String[] getLocations() { // returns an Array of all Event locations in DB
        int count = 1;
        Iterable<Document> documents = eventCollection.find();
        MongoCursor<Document> cursor = eventCollection.find().iterator();
        for (Document document : documents) {
            count ++;
        }
        String[] eventLocationArr = new String[count];
        int i = 0;
        while (cursor.hasNext()) { // Used MongoCursor object to iterate over docs in Events collection
            Document testDoc = cursor.next();
            String eventLocation = testDoc.get("location") + ""; // Converts the event location in DB from Object to String (DB imports from MongoCursor defaults to Object)
            eventLocationArr[i] = eventLocation;
            i++;
        }
        return eventLocationArr;
    }
    public String[] getPendingEvents() { // returns an Array of all Pending Event Names in DB
        Iterable<Document> documents = eventCollection.find();
        MongoCursor<Document> cursor = eventCollection.find().iterator();
        int count = numPendingEvents();
        int runningCount = 0;
        String[] eventNameArr = new String[count];
        int i = 0;
        while (cursor.hasNext()) { // Used MongoCursor object to iterate over docs in Events collection
            Document testDoc = cursor.next();
            String eventName;
            String eventStatus = testDoc.get("eventStatus") + "";
            //System.out.println(eventStatus);
            if (eventStatus.equals("Pending")) { // Must use .equals, doesn't work with == comparison.
            eventName = testDoc.get("eventName") + ""; // Converts the event Name in DB from Object to String (DB imports from MongoCursor defaults to Object)
            eventNameArr[runningCount] = eventName;
            ++runningCount;
            }
            i++;
        }
        return eventNameArr;
    }

    public int numPendingEvents() {
        int count = 0;
        try (MongoCursor<Document> cursor = eventCollection.find().iterator()) {
            while (cursor.hasNext()) {
                Document testDoc = cursor.next();
                String eventStatus = testDoc.get("eventStatus") + "";
                if ("Pending".equals(eventStatus)) {
                    count++;
                }
            }
        } // The cursor is automatically closed here
        return count;
    }

    public void selectEvent(String event){
        // TODO
    }

    public void selectPosition(String position){
        // TODO
    }

    public String getRole(){
        return role;
    }

    public void setRole(String role) {
        try {
            if (!"volunteer".equals(role) && !"event organizer".equals(role) && !"admin".equals(role)) {
                throw new IllegalArgumentException("Invalid role. Please enter 'volunteer', 'event organizer', or 'admin'.");
            }
            this.role = role;
        } catch (IllegalArgumentException e) {
            System.err.println("Error setting role: " + e.getMessage());
            throw e; // Rethrowing the exception after logging it
        }
    }


    public Date getRegistrationDate(){
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate){
        this.registrationDate = registrationDate;
    }
}
