package VolunteerLink;

// import java.util.ArrayList;
// import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
// import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import static com.mongodb.client.model.Filters.eq;


// @SuppressWarnings("unused")
public class Admin {

    private MongoClient mongoClient;
    private MongoCollection<Document> userCollection;
    private MongoCollection<Document> eventCollection;
    private MongoDatabase database;

    public Admin(MongoClient mongoClient, MongoDatabase database){
        this.mongoClient = mongoClient;
        this.userCollection = database.getCollection("Users");
        this.eventCollection = database.getCollection("Events");
        this.database = database;
    }

    // Obsolete?
    public void approveEvent(String eventId){
        Document doc = eventCollection.find(eq("_id", eventId)).first();
        Bson update = Updates.set("approved", true);
        eventCollection.updateOne(doc, update);
    }

    public void denyEvent(String eventId){
        Document doc = eventCollection.find(eq("_id", eventId)).first();
        Bson update = Updates.set("approved", false);
        eventCollection.updateOne(doc, update);
    }
}
