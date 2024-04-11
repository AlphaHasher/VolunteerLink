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

    public void EventApprovalStatus(Document event, boolean status){
        event.append("status", status); // true if approved and false if denied
        collection.insertOne(event);
    }
}
