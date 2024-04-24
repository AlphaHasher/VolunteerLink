package VolunteerLink;

import java.util.Date;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.LocalDateTime;
import java.util.List;

// import java.util.List;
// import java.util.ArrayList;
// import java.util.Arrays;

import org.bson.Document;
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

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private String location;
    private int volunteersNeeded;
    private int volunteersRegistered;

    private List<String> tags;

    // private String[] Event;

    public Event(String eventName, String description, String location, LocalDateTime startDate, LocalDateTime endDate, int volunteersNeeded, int volunteersRegistered, List<String> tags) {
        this.eventName = eventName;
        this.eventDescription = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.volunteersNeeded = volunteersNeeded;
        this.volunteersRegistered = volunteersRegistered;
        this.tags = tags;
    }
    public Event(){};

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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
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
        if (volunteersNeeded < 0) { // should we allow events with 0 volunteers needed?
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

    public List<String> getTags() {
        return tags;
    }

    public void addTag(String tag) {
        tags.add(tag);
    }

    public void removeTag(String tag) {
        tags.remove(tag);
    }

    public Document toDocument() {
        Document doc = new Document();
        doc.append("eventName", this.eventName)
           .append("eventDescription", this.eventDescription)
           .append("location", this.location)
           .append("startDate", this.startDate)
           .append("endDate", this.endDate)
           .append("volunteersNeeded", this.volunteersNeeded)
           .append("volunteersRegistered", this.volunteersRegistered)
           .append("tags", this.tags);
        return doc;
    }

    //feel free to improve or format this
    public static Event convertDocumentToEvent(Document doc) {
        Event event = new Event();
    event.setEventName(doc.getString("eventName"));
    event.setEventDescription(doc.getString("eventDescription"));
    event.setLocation(doc.getString("location"));

    Integer volunteersNeeded = doc.getInteger("volunteersNeeded");
    event.setVolunteersNeeded(volunteersNeeded != null ? volunteersNeeded.intValue() : 0);

    Integer volunteersRegistered = doc.getInteger("volunteersRegistered");
    event.setVolunteersRegistered(volunteersRegistered != null ? volunteersRegistered.intValue() : 0);

    // Convert startDate from java.util.Date to java.time.LocalDateTime
    Date startDate = doc.getDate("startDate");
    event.setStartDate(LocalDateTime.ofInstant(startDate.toInstant(), ZoneId.systemDefault()));

    // Convert endDate from java.util.Date to java.time.LocalDateTime
    Date endDate = doc.getDate("endDate");
    event.setEndDate(LocalDateTime.ofInstant(endDate.toInstant(), ZoneId.systemDefault()));

    return event;
    }
}
