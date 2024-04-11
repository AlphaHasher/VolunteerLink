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
public class Admin {

    private int priority;
    private int AdminCode;

    private MongoClient mongoClient;
    private MongoCollection<Document> collection;
    private MongoDatabase database;

    public Admin(MongoClient mongoClient, MongoDatabase database, MongoCollection<Document> collection){
        this.mongoClient = mongoClient;
        this.collection = database.getCollection("Users");
        this.database = database;
    }

    public List<Document> getUsers(){

        List<Document> users = new ArrayList<>();

        Iterable<Document> documents = collection.find();

        for (Document document : documents) {
            users.add(document);

            //need to look more into how to parse the document info
            System.out.println(document);
        }
        return users;
    }

    // Priority is set as: 1 = volunteer, 2 = event organizer, 3 = admin
    public int getPriority(){
        return priority;
    }

    public void setPriority(int priority){
        this.priority = priority;
    }

    public void revokePriority(Document user){
        int newPriority = user.getInteger("priority");
        if(newPriority > 1){
            user.append("priority", newPriority - 1);
        }
    }

    public void elevatePriority(Document user){
        int newPriority = user.getInteger("priority");
        if(newPriority < 3){
            user.append("priority", newPriority + 1);
        }
    }

    public void approveEvent(Document event){
        event.append("approved", true);
        collection.insertOne(event);
    }
}
