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
import java.util.Date;

public class User {

    private MongoClient mongoClient;
    private MongoCollection<Document> eventCollection;
    private MongoDatabase database;

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
    private Date registrationDate;

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

    //  TODO: make registrationDate a Date object
    public User(String email, String password, String firstName, String lastName, String role, Date registrationDate){
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

    public void viewEvents(){
        // TODO
    }

    public void selectEvent(String event){
        // TODO
    }

    public void selectPosition(String position){
        // TODO
    }

    public String getRole(){
        return role;
    }

    public void setRole(String role){
        if (role != "volunteer" && role != "event organizer" && role != "admin") {
            throw new IllegalArgumentException("Invalid role. Please enter 'volunteer', 'event organizer', or 'admin'.");
        }
        this.role = role;
    }

    public Date getRegistrationDate(){
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate){
        this.registrationDate = registrationDate;
    }
}
