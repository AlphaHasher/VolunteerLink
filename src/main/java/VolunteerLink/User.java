package VolunteerLink;

import org.bson.Document;

// import static com.mongodb.client.model.Filters.eq;
// import org.bson.Document;
// import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
// import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
// import com.mongodb.client.model.Filters;
// import com.mongodb.client.model.UpdateOptions;
// import com.mongodb.client.model.Updates;
// import com.mongodb.client.result.UpdateResult;

// import java.util.ArrayList;
// import java.util.List;

public class User {

    private MongoClient mongoClient;
    private MongoCollection<Document> eventCollection;
    private MongoDatabase database;

    private String id; // need a way to generate unique IDs
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
    private String registrationDate;

    public User(MongoClient mongoClient, MongoDatabase database){
        this.mongoClient = mongoClient;
        this.eventCollection = database.getCollection("Events");
        this.database = database;
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

    // TODO: eventRole_id, id, and registrationDate (Date object)
    public User(String id, String email, String password, String firstName, String lastName, String role, String registrationDate){
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.registrationDate = registrationDate;
    }

    public User(){
        this.role = "volunteer";
    }

    public String getName(){
        return firstName + " " + lastName;
    }

    public void setName(String name){
        String[] names = name.split(" ");
        this.firstName = names[0];
        this.lastName = names[1];
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public void viewEvents(){
        // TODO
    }

    public void selectEvent(String event){
        // TODO
    }

    public void selectPosition(String position){
        // TODO
    }

    public Document toDocument(){
        return new Document("id", id)
                .append("email", email)
                .append("password", password)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("role", role)
                .append("registrationDate", registrationDate);
    }
}
