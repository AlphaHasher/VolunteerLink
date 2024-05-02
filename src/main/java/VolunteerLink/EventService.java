package VolunteerLink;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

@Service
public class EventService {

    // Method to retrieve all users from the database
    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();

        // Retrieve the users collection from the database
        MongoCollection<Document> eventsCollection = Database.getInstance().getEventCollection();

        // Query all documents from the users collection
        FindIterable<Document> eventDocuments = eventsCollection.find();

        // Iterate over the documents and convert them to User objects
        try (MongoCursor<Document> cursor = eventDocuments.iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Event event = Event.convertDocumentToEvent(doc);
                events.add(event);
            }
        }

        return events;
    }
    public List<Event> getNextEvents(int limit) {
        List<Event> events = new ArrayList<>();
    
        // Retrieve the events collection from the database
        MongoCollection<Document> eventsCollection = Database.getInstance().getEventCollection();
    
        // Query the first 'limit' number of documents from the events collection
        FindIterable<Document> eventDocuments = eventsCollection.find().limit(limit);
    
        // Iterate over the documents and convert them to Event objects
        try (MongoCursor<Document> cursor = eventDocuments.iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Event event = Event.convertDocumentToEvent(doc);
                events.add(event);
            }
        }
    
        return events;
    }

    public static Event getEventById(String eventId) {
        // Use your database access method to retrieve the event by its ID
        Bson filter = Filters.eq("_id", new ObjectId(eventId));
    
    // Use the filter to query the event collection
        Document eventDocument = Database.getInstance().getEventCollection().find(filter).first();
    
    // Convert the document to an Event object
        if (eventDocument != null) {
            return Event.convertDocumentToEvent(eventDocument);
        } else {
            return null; // No event found with the given eventId
        }
    }
    

}