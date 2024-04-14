package VolunteerLink;

import java.util.Date;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import static com.mongodb.client.model.Filters.eq;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

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

    //converting string to objectID. Should accept ObjectId type later from front end or we will need to convert for every function.
    public void setEventName(String id, String eventName){
        ObjectId objectId = new ObjectId(id);
        Document filter = new Document("_id", objectId);
        Document update = new Document("$set", new Document("eventName", eventName));
        eventCollection.updateOne(filter, update);
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


    // These methods are deprecated, leaving them here for now.
    // Event Parsing created by Colin, may update in future to automatically sort by most recent.
    // Currently sorted by most recently imported (I believe)
    // Displays/prints a list of all eventNames in DB
    // Inefficient but works, perhaps there's a way to do the same function without iterating twice, which we may improve once everything's functional
    // Will update to reduce redundancy.
    /*
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
    /* Deprecated
    public String[] getEventDescriptionsOld() { // returns an Array of all eventDescriptions in DB
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
    public String[] getEventDescriptions() {
        List<String> descriptions = new ArrayList<>();
        MongoCursor<Document> cursor = eventCollection.aggregate(
            Arrays.asList(
                Aggregates.group("$description", Accumulators.first("description", "$description")), // Group by description
                Aggregates.project(new Document("_id", 0).append("description", "$_id")) // Project to get event descriptions
            )
        ).iterator();

        try {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                descriptions.add(doc.getString("description"));
            }
        } finally {
            cursor.close();
        }

        return descriptions.toArray(new String[0]);
    }

    public String[] getLocations() {
        List<String> locations = new ArrayList<>();
        MongoCursor<Document> cursor = eventCollection.aggregate(
            Arrays.asList(
                Aggregates.group("$location", Accumulators.first("location", "$location")), // Group by location
                Aggregates.project(new Document("_id", 0).append("location", "$_id")) // Project to get location names
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
    } */

}
