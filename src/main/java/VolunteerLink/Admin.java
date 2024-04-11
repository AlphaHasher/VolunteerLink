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

    // returns every user in the database
    public String getUsers() {
        StringBuilder usersString = new StringBuilder();
        Iterable<Document> documents = userCollection.find();

        for (Document document : documents) {
            usersString.append(document.toString()).append("\n");
        }
        return usersString.toString();
    }

    public void setRole(String userId, String role){
        if (role != "volunteer" && role != "event organizer" && role != "admin") {
            throw new IllegalArgumentException("Invalid role. Please enter 'volunteer', 'event organizer', or 'admin'.");
        }
        Document doc = userCollection.find(eq("_id", userId)).first();
        Bson update = Updates.set("role", role);
        UpdateResult result = userCollection.updateOne(doc, update);
    }

    // Priority is set as: 1 = volunteer, 2 = event organizer, 3 = admin
    // public int getPriority(String userId){
    //     Document doc = userCollection.find(eq("userID", userId)).first();
    //     return doc.getInteger("priority");
    // }

    // public void setPriority(String userId, int priority){
    //     Document doc = userCollection.find(eq("userID", userId)).first();
    //     Bson update = Updates.set("priority", priority);
    //     UpdateResult result = userCollection.updateOne(doc, update);
    // }

    // public void revokePriority(String userId){
    //     if(getPriority(userId) > 1){
    //         setPriority(userId, getPriority(userId) - 1);
    //     }
    // }

    // public void elevatePriority(String userId){
    //     if(getPriority(userId) < 3){
    //         setPriority(userId, getPriority(userId) + 1);
    //     }
    // }

    // Currently requires the event name but will later need to be changed to the id of the event

    public void approveEvent(String event){
        Document doc = eventCollection.find(eq("name", event)).first();
        Bson update = Updates.set("approved", true);
        UpdateResult result = eventCollection.updateOne(doc, update);
    }

    public void denyEvent(String event){
        Document doc = eventCollection.find(eq("name", event)).first();
        Bson update = Updates.set("approved", false);
        UpdateResult result = eventCollection.updateOne(doc, update);
    }
}
