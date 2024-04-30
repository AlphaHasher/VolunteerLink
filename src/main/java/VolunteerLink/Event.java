package VolunteerLink;

import java.util.Date;
// import java.time.LocalTime;
import java.time.ZoneId;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


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
    private Date eventCreationDate;

    private String location;
    private int volunteersNeeded;
    private int volunteersRegistered;

    private List<String> tags;

    public Event(String eventName, String description, String location, LocalDateTime startDate, LocalDateTime endDate, int volunteersNeeded, int volunteersRegistered, List<String> tags, Date eventCreationDate) {
        this.eventName = eventName;
        this.eventDescription = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.volunteersNeeded = volunteersNeeded;
        this.volunteersRegistered = volunteersRegistered;
        this.tags = tags;
        this.eventCreationDate = eventCreationDate;
    }

    public Event(){};

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

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void addTag(String tag) {
        tags.add(tag);
    }

    public void removeTag(String tag) {
        tags.remove(tag);
    }

    public void clearTags() {
        tags.clear();
    }

    public void setEventCreationDate(Date eventCreationDate) {
        this.eventCreationDate = eventCreationDate;
    }

    public Date getEventCreationDate() {
        return eventCreationDate;
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
           .append("tags", this.tags)
           .append("eventCreationDate", this.eventCreationDate);
        return doc;
    }

    public static Event convertDocumentToEvent(Document doc) {
        if (doc == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }

        Event event = new Event();
        event.setEventName(doc.getString("eventName"));
        event.setEventDescription(doc.getString("eventDescription"));
        event.setLocation(doc.getString("location"));
        event.setVolunteersNeeded(getIntValue(doc, "volunteersNeeded"));
        event.setVolunteersRegistered(getIntValue(doc, "volunteersRegistered"));
        event.setStartDate(convertDate(doc.getDate("startDate")));
        event.setEndDate(convertDate(doc.getDate("endDate")));
        event.setTags(doc.getList("tags", String.class));
        event.setEventCreationDate(doc.getDate("eventCreationDate"));

        return event;
    }

    private static int getIntValue(Document doc, String key) {
        return Optional.ofNullable(doc.getInteger(key)).orElse(0);
    }

    // May not need.
    // Possibly causing the time to be off when displaying the event on the front end.
    private static LocalDateTime convertDate(Date date) {
        return date != null ? LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()) : null;
    }
}
