package VolunteerLink;

import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class EventManager {

    private MongoClient mongoClient;
    private MongoCollection<Document> userCollection;
    private MongoCollection<Document> eventCollection;
    private MongoDatabase database;

    //constructor
    public EventManager(MongoClient mongoClient, MongoDatabase database){
        this.mongoClient = mongoClient;
        this.userCollection = database.getCollection("Users");
        this.eventCollection = database.getCollection("Events");
        this.database = database;
    }

    // returns every user in the database
    public String getEvents() {
        StringBuilder eventsString = new StringBuilder();
        Iterable<Document> documents = eventCollection.find();

        for (Document document : documents) {
            eventsString.append(document.toString()).append("\n");
        }
        return eventsString.toString();
    }

    public void addEvent(Document event){
        Event newEvent = new Event(event.getString("name"), event.getString("description"), event.getString("startDate"), event.getString("endDate"), event.getString("location"), event.getInteger("volunteersNeeded"), event.getInteger("volunteersRegistered"), event.getString("eventStatus"));
        eventCollection.insertOne(event);
    }

    public void deleteEvent(String id){
        eventCollection.deleteOne(eq("id", id));
    }
}
