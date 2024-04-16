package VolunteerLink;

// import java.util.Date;
// import java.util.List;
// import java.util.ArrayList;
// import java.util.Arrays;

// import org.bson.Document;
// import org.bson.types.ObjectId;

// import com.mongodb.client.MongoCursor;
// import com.mongodb.client.MongoClient;
// import com.mongodb.client.MongoCollection;
// import com.mongodb.client.MongoDatabase;
// import com.mongodb.client.model.Accumulators;
// import com.mongodb.client.model.Aggregates;
// import com.mongodb.client.model.Filters;
// import com.mongodb.client.model.Updates;

public class Event {

    // private MongoCollection<Document> eventCollection;

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

    // public Event() {
    //     this.eventCollection = Database.getInstance().getEventCollection();
    // }

    public Event(String eventName, String description, String startDate, String endDate, String location, int volunteersNeeded, int volunteersRegistered){
        this.eventName = eventName;
        this.eventDescription = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.volunteersNeeded = volunteersNeeded;
        this.volunteersRegistered = volunteersRegistered;
        this.eventStatus = "Pending";
        this.approved = false;
    }

    // private Document getFromId(String id) {
    //     ObjectId objectId = new ObjectId(id);
    //     Document doc = eventCollection.find(Filters.eq("_id", objectId)).first();
    //     return doc;
    // }

    // ********************************************
    // *** Getters and setters for class fields ***
    // ********************************************

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getVolunteersNeeded() {
        return volunteersNeeded;
    }

    public void setVolunteersNeeded(int volunteersNeeded) {
        if (volunteersNeeded <= 0) { // should we allow events with 0 volunteers needed?
            throw new IllegalArgumentException("Invalid number of volunteers needed: " + volunteersNeeded);
        }
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
        if (!"Pending".equals(eventStatus) && !"Denied".equals(eventStatus) && !"Scheduled".equals(eventStatus) && !"Ongoing".equals(eventStatus) && !"Ended".equals(eventStatus)){
            throw new IllegalArgumentException("Invalid event status: " + eventStatus);
        }
        this.eventStatus = eventStatus;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
