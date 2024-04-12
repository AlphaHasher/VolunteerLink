package VolunteerLink;

import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
// import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
// import com.mongodb.client.model.Filters;
// import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
// import com.mongodb.client.result.UpdateResult;

// import java.util.ArrayList;
// import java.util.List;

// @SuppressWarnings("unused")
public class Admin {

    private MongoClient mongoClient;
    private MongoCollection<Document> userCollection;
    private MongoCollection<Document> eventCollection;
    private MongoDatabase database;

    // public Admin(MongoClient mongoClient, MongoDatabase database, MongoCollection<Document> collection){
    public Admin(MongoClient mongoClient, MongoDatabase database){
        this.mongoClient = mongoClient;
        this.userCollection = database.getCollection("Users");
        this.eventCollection = database.getCollection("Events");
        this.database = database;
    }

    // Currently requires the event name but will later need to be changed to the id of the event

    public void approveEvent(String event){
        Document doc = eventCollection.find(eq("name", event)).first();
        Bson update = Updates.set("approved", true);
        eventCollection.updateOne(doc, update);
    }

    public void denyEvent(String event){
        Document doc = eventCollection.find(eq("name", event)).first();
        Bson update = Updates.set("approved", false);
        eventCollection.updateOne(doc, update);
    }
}
