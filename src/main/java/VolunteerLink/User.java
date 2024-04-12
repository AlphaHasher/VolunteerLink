package VolunteerLink;

import org.bson.Document;


// import static com.mongodb.client.model.Filters.eq;
// import org.bson.Document;
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

public class User {

    private MongoClient mongoClient;
    private MongoCollection<Document> eventCollection;
    private MongoDatabase database;

    private String id; // need a way to generate unique IDs
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
    private String registrationDate;

    public User(MongoClient mongoClient, MongoDatabase database){
        this.mongoClient = mongoClient;
        this.eventCollection = database.getCollection("Events");
        this.database = database;
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

    // TODO: eventRole_id, id, and registrationDate (Date object)
    public User(String id, String email, String password, String firstName, String lastName, String role, String registrationDate){
        this.id = id;
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

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
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

    public int numEvents() {
        int count = 0;
        Iterable<Document> documents = eventCollection.find();
        for (Document document : documents) {
            count ++;
        }
        return count;
    }

    public int numPendingEvents() {
        int count = 0;
        MongoCursor<Document> cursor = eventCollection.find().iterator();
        while (cursor.hasNext()) {
            Document testDoc = cursor.next();
            String eventStatus = testDoc.get("eventStatus") + "";
            if (eventStatus.equals("Pending")) {
                count++;
            }
        }
        return count;
    }

    public void selectEvent(String event){
        // TODO
    }

    public void selectPosition(String position){
        // TODO
    }

    public Document toDocument(){
        return new Document("id", id)
                .append("email", email)
                .append("password", password)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("role", role)
                .append("registrationDate", registrationDate);
    }
}
