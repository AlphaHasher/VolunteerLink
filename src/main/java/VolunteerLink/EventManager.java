package VolunteerLink;

import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.MongoCollection;

public class EventManager {

    private MongoCollection<Document> eventCollection;

    public EventManager() {
        this.eventCollection = Database.getInstance().getEventCollection();
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

    public void addEvent(Document event){
        // try {
            // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            // Date startDate = dateFormat.parse(event.getString("startDate"));
            // Date endDate = dateFormat.parse(event.getString("endDate"));

            Event newEvent = new Event(
                event.getString("eventName"),
                event.getString("eventDescription"),
                event.getString("startDate"),
                event.getString("endDate"),
                event.getString("location"),
                event.getInteger("volunteersNeeded"),
                event.getInteger("volunteersRegistered"),
                event.getString("eventStatus"));

            event.append("approved", false);

            eventCollection.insertOne(event);
        // }
        // catch (ParseException e) {
        //     e.printStackTrace();
        // }
    }

    public void deleteEvent(String id){
        eventCollection.deleteOne(eq("_id", id));
    }
}
