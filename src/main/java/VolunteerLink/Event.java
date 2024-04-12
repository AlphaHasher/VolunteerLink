package VolunteerLink;

import java.util.Date;
import org.bson.Document;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Event {

    private MongoClient mongoClient;
    private MongoCollection<Document> eventCollection;
    private MongoDatabase database;


    private String id; // MongoDB makes _ids automatically unique when imported to the DB
    private String eventName;
    private String eventDescription;

    private Date startDate;
    private Date endDate;

    private String location;
    private int volunteersNeeded;
    private int volunteersRegistered;
    private String eventStatus;
    private boolean approved;

    public Event(MongoClient mongoClient, MongoDatabase database) {
        try {
            this.mongoClient = mongoClient;
            this.eventCollection = database.getCollection("Events");
            this.database = database;
        } catch (Exception e) {
            System.err.println("Failed to initialize Event with database connection: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Event(String eventName, String description, Date startDate, Date endDate, String location, int volunteersNeeded, int volunteersRegistered, String eventStatus){
        this.eventName = eventName;
        this.eventDescription = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.volunteersNeeded = volunteersNeeded;
        this.volunteersRegistered = volunteersRegistered;
        this.eventStatus = eventStatus;
        this.approved = false;
    }

    // public String getEvents() {
    //     StringBuilder eventsString = new StringBuilder();
    //     Iterable<Document> documents = eventCollection.find();

    //     for (Document document : documents) {
    //         eventsString.append(document.toString()).append("\n");
    //     }
    //     return eventsString.toString();
    // }

    public Event getEvent(){
        return this;
    }

    public void setEventName(String eventName){
        this.eventName = eventName;
    }
    public String getEventName(){
        return eventName;
    }

    public String getDescription() {
        return eventDescription;
    }
    public void setDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getVolunteersNeeded() {
        return volunteersNeeded;
    }

    public void setVolunteersNeeded(int volunteersNeeded) {
        this.volunteersNeeded = volunteersNeeded;
    }
    public int getVolunteersRegistered() {
        return volunteersRegistered;
    }

    public void setVolunteersRegistered(int volunteersRegistered) {
        this.volunteersRegistered = volunteersRegistered;
    }
    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }

    public boolean isApproved() {
        return approved;
    }
    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
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

}
