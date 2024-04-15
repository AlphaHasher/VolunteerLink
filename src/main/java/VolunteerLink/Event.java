package VolunteerLink;

// import java.util.Date;
import org.bson.Document;
import org.bson.types.ObjectId;

// import com.mongodb.client.MongoCursor;
// import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
// import com.mongodb.client.MongoDatabase;
// import com.mongodb.client.model.Accumulators;
// import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

// import static com.mongodb.client.model.Filters.eq;

// import java.util.List;
// import java.util.ArrayList;
// import java.util.Arrays;

public class Event {

    private MongoCollection<Document> eventCollection;

    private String id; // MongoDB makes _ids automatically unique when imported to the DB
    private String eventName;
    private String eventDescription;

    private String startDate;
    private String endDate;

    private String location;
    private int volunteersNeeded;
    private int volunteersRegistered;
    private String eventStatus;
    private boolean approved;

    // public Event(MongoClient mongoClient, MongoDatabase database) {
    //     try {
    //         this.mongoClient = mongoClient;
    //         this.eventCollection = database.getCollection("Events");
    //         this.database = database;
    //     } catch (Exception e) {
    //         System.err.println("Failed to initialize Event with database connection: " + e.getMessage());
    //         e.printStackTrace();
    //     }
    // }
    public Event() {
        this.eventCollection = Database.getInstance().getEventCollection();
    }

    public Event(String eventName, String description, String startDate, String endDate, String location, int volunteersNeeded, int volunteersRegistered, String eventStatus){
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

    private Document getFromId(String id) {
        ObjectId objectId = new ObjectId(id);
        Document doc = eventCollection.find(Filters.eq("_id", objectId)).first();
        return doc;
    }

    public Event getEvent(String id){
        Document doc = getFromId(id);
        return new Event(
            doc.getString("eventName"),
            doc.getString("eventDescription"),
            doc.getString("startDate"),
            doc.getString("endDate"),
            doc.getString("location"),
            doc.getInteger("volunteersNeeded"),
            doc.getInteger("volunteersRegistered"),
            doc.getString("eventStatus"));
    }

    public String getEventName(String id){
        Document doc = getFromId(id);
        return doc.getString("eventName");
    }

    public void setEventName(String id, String eventName) {
        ObjectId objectId = new ObjectId(id);
        eventCollection.updateOne(Filters.eq("_id", objectId), Updates.set("eventName", eventName));
    }

    public String getDescription(String id) {
        Document doc = getFromId(id);
        return doc.getString("eventDescription");
    }
    public void setDescription(String id, String eventDescription) {
        ObjectId objectId = new ObjectId(id);
        eventCollection.updateOne(Filters.eq("_id", objectId), Updates.set("eventDescription", eventDescription));
    }

    public String getStartDate() {
        Document doc = getFromId(id);
        return doc.getString("startDate");
    }
    public void setStartDate(String startDate) {
        ObjectId objectId = new ObjectId(id);
        eventCollection.updateOne(Filters.eq("_id", objectId), Updates.set("startDate", startDate));
    }

    public String getEndDate() {
        Document doc = getFromId(id);
        return doc.getString("endDate");
    }
    public void setEndDate(String endDate) {
        ObjectId objectId = new ObjectId(id);
        eventCollection.updateOne(Filters.eq("_id", objectId), Updates.set("endDate", endDate));
    }

    public int getVolunteersNeeded() {
        Document doc = getFromId(id);
        return doc.getInteger("volunteersNeeded");
    }

    public void setVolunteersNeeded(int volunteersNeeded) {
        ObjectId objectId = new ObjectId(id);
        eventCollection.updateOne(Filters.eq("_id", objectId), Updates.set("volunteersNeeded", volunteersNeeded));
    }
    public int getVolunteersRegistered() {
        Document doc = getFromId(id);
        return doc.getInteger("volunteersRegistered");
    }

    public void setVolunteersRegistered(int volunteersRegistered) {
        ObjectId objectId = new ObjectId(id);
        eventCollection.updateOne(Filters.eq("_id", objectId), Updates.set("volunteersRegistered", volunteersRegistered));
    }
    public String getEventStatus() {
        Document doc = getFromId(id);
        return doc.getString("eventStatus");
    }

    public void setEventStatus(String eventStatus) {
        ObjectId objectId = new ObjectId(id);
        eventCollection.updateOne(Filters.eq("_id", objectId), Updates.set("eventStatus", eventStatus));
    }

    public boolean isApproved() {
        Document doc = getFromId(id);
        return doc.getBoolean("approved");
    }
    public void setApproved(boolean approved) {
        ObjectId objectId = new ObjectId(id);
        eventCollection.updateOne(Filters.eq("_id", objectId), Updates.set("approved", approved));
    }

    public String getLocation() {
        Document doc = getFromId(id);
        return doc.getString("location");
    }
    public void setLocation(String location) {
        ObjectId objectId = new ObjectId(id);
        eventCollection.updateOne(Filters.eq("_id", objectId), Updates.set("location", location));
    }


    // These methods are deprecated, leaving them here for now.
    // Event Parsing created by Colin, may update in future to automatically sort by most recent.
    // Currently sorted by most recently imported (I believe)
    // Displays/prints a list of all eventNames in DB
    // Inefficient but works, perhaps there's a way to do the same function without iterating twice, which we may improve once everything's functional
    // Will update to reduce redundancy.

    // PRINTS EVENT NAMES
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
    //  RETURNS AN ARRAY OF EVENT NAMES
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
