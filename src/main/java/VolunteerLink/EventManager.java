package VolunteerLink;

import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class EventManager {

    private MongoClient mongoClient;
    private MongoCollection<Document> collection;
    private MongoDatabase database;

    //constructor
    public EventManager(MongoClient mongoClient, MongoDatabase database, MongoCollection<Document> collection){
        this.mongoClient = mongoClient;
        this.collection = database.getCollection("Events");
        this.database = database;
    }

    //currently prints events from mongo to console. Can change to display list in front end.
    public List<Document> getEvents(){

        List<Document> events = new ArrayList<>();

        Iterable<Document> documents = collection.find();
        
        for (Document document : documents) {
            events.add(document);

            //need to look more into how to parse the document info
            System.out.println(document);
        }
        return events;
    }

    //use .toDocument() in argument
    public void AddEvent(Document Event){
      //testing out mongodb. this will have to be implemented to tie in better with events in the future  
      collection.insertOne(Event);  
    }

    //need to work on better way to interact with mongoDB IDs
    public void deleteEvent(Document Event){
        collection.deleteOne(Filters.eq("_id", Event.getObjectId("_id").toString()));

    }
}
