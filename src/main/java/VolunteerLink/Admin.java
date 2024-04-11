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

    private int priority;
    private int AdminCode;

    private MongoClient mongoClient;
    private MongoCollection<Document> userCollection;
    private MongoCollection<Document> eventCollection;
    private MongoDatabase database;

    public Admin(MongoClient mongoClient, MongoDatabase database, MongoCollection<Document> collection){
        this.mongoClient = mongoClient;
        this.userCollection = database.getCollection("Users");
        this.eventCollection = database.getCollection("Events");
        this.database = database;
    }

    public List<Document> getUsers(){

        List<Document> users = new ArrayList<>();

        Iterable<Document> documents = userCollection.find();

        for (Document document : documents) {
            users.add(document);

            //need to look more into how to parse the document info
            System.out.println(document);
        }
        return users;
    }

    // Priority is set as: 1 = volunteer, 2 = event organizer, 3 = admin
    public int getPriority(Document user){
        Document doc = userCollection.find(eq("userID", user.getString("userID"))).first();
        return doc.getInteger("priority");
    }

    public void setPriority(Document user, int priority){
        Bson filter = Filters.eq("userID", user.getString("userID"));
        Bson update = Updates.set("priority", priority);
        UpdateResult result = userCollection.updateOne(filter, update);
    }

    public void revokePriority(Document user){
        if(getPriority(user) > 1){
            setPriority(user, getPriority(user) - 1);
        }
    }

    public void elevatePriority(Document user){
        if(getPriority(user) < 3){
            setPriority(user, getPriority(user) + 1);
        }
    }

    // Currently requires the event name but likely will need to be changed to _id

    public void approveEvent(Document event){
        Bson filter = Filters.eq("eventName", event.getString("eventName"));
        Bson update = Updates.set("approved", true);
        UpdateResult result = eventCollection.updateOne(filter, update);
    }

    public void denyEvent(Document event){
        Bson filter = Filters.eq("eventName", event.getString("eventName"));
        Bson update = Updates.set("approved", false);
        UpdateResult result = eventCollection.updateOne(filter, update);
    }
}
