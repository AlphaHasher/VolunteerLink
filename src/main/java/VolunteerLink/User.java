package VolunteerLink;

// import java.util.ArrayList;
// import java.util.List;
import java.util.Date;

import org.bson.Document;
// import static com.mongodb.client.model.Filters.eq;
// import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

// import com.mongodb.client.MongoClient;
// import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
// import com.mongodb.client.MongoDatabase;
// import com.mongodb.client.model.Filters;
// import com.mongodb.client.model.UpdateOptions;
// import com.mongodb.client.model.Updates;
// import com.mongodb.client.result.UpdateResult;
import com.mongodb.client.model.Filters;


public class User {
    private MongoCollection<Document> userCollection;

    public User() {
        this.userCollection = Database.getInstance().getUsersCollection();
    }

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
    private Date registrationDate;
    private ObjectId eventRole_id;

    public User(String email, String password, String firstName, String lastName, String role, Date registrationDate, ObjectId eventRole_id){
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.registrationDate = registrationDate;
        this.eventRole_id = eventRole_id;
    }
    // Creates new user, must have check to verify if user already exists
    // Method may not be necessary, perhaps we will just manually create users
    public void createUser(String email, String password) {

    }

    // Parse database for matching email and password, then return User.
    public User logInUser(String email, String password) {
        User newUser = new User(email, password, firstName, lastName, role, registrationDate, eventRole_id);
        return newUser;
    }

    // ********************************************
    // *** Getters and setters for class fields ***
    // ********************************************

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String first) {
        this.firstName = first;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String last) {
        this.lastName = last;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        if (!"volunteer".equals(role) && !"event organizer".equals(role) && !"admin".equals(role)) {
                throw new IllegalArgumentException("Invalid role. Please enter 'volunteer', 'event organizer', or 'admin'.");
            }
            this.role = role;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public ObjectId getEventRole_id() {
        return eventRole_id;
    }

    public void setEventRole_id(ObjectId eventRole_id) {
        // adds to the eventRole_id array
        this.eventRole_id = eventRole_id;
    }

    public void deleteEventRole_id(ObjectId eventRole_id) {
        // removes from the eventRole_id array
        this.eventRole_id = null;
    }

    // ***********************************************
    // *** Getters and setters for database fields ***
    // ***********************************************

    private Document getFromId(String id) {
        ObjectId objectId = new ObjectId(id);
        Document doc = userCollection.find(Filters.eq("_id", objectId)).first();
        return doc;
    }

    // public String getName(String id){
    //     Document doc = getFromId(id);
    //     return doc.getString("firstName") + " " + doc.getString("lastName");
    // }

    // public void setFirstName(String id, String first){
    //     Document doc = getFromId(id);
    //     doc.put("firstName", first);
    //     userCollection.replaceOne(Filters.eq("_id", new ObjectId(id)), doc);
    // }

    // public void setLastName(String id, String last){
    //     Document doc = getFromId(id);
    //     doc.put("lastName", last);
    //     userCollection.replaceOne(Filters.eq("_id", new ObjectId(id)), doc);
    // }

    // public String getPassword(String id, String password){
    //     Document doc = getFromId(id);
    //     return doc.getString("password");
    // }

    // public void setPassword(String id, String password){
    //     Document doc = getFromId(id);
    //     doc.put("password", password);
    //     userCollection.replaceOne(Filters.eq("_id", new ObjectId(id)), doc);
    // }

    // public String getEmail(String id, String email){
    //     Document doc = getFromId(id);
    //     return doc.getString("email");
    // }

    public void setEmail(String id, String email){
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        Document doc = getFromId(id);
        doc.put("email", email);
        userCollection.replaceOne(Filters.eq("_id", new ObjectId(id)), doc);
    }

    // public String getRole(String id){
    //     Document doc = getFromId(id);
    //     return doc.getString("role");
    // }

    public void setRole(String id, String role) {
        if (!"volunteer".equals(role) && !"event organizer".equals(role) && !"admin".equals(role)) {
                throw new IllegalArgumentException("Invalid role. Please enter 'volunteer', 'event organizer', or 'admin'.");
            }
        Document doc = getFromId(id);
        doc.put("role", role);
        userCollection.replaceOne(Filters.eq("_id", new ObjectId(id)), doc);
    }

    // public Date getRegistrationDate(String id){
    //     Document doc = getFromId(id);
    //     return doc.getDate("registrationDate");
    // }

    // public void setRegistrationDate(String id, Date registrationDate){
    //     Document doc = getFromId(id);
    //     doc.put("registrationDate", registrationDate);
    //     userCollection.replaceOne(Filters.eq("_id", new ObjectId(id)), doc);
    // }

    public ObjectId getEventRole_id(String id){
        Document doc = getFromId(id);
        return doc.getObjectId("eventRole_id");
    }

    public void setEventRole_id(String id, ObjectId eventRole_id){
        // adds to the eventRole_id array
        Document doc = getFromId(id);
        doc.put("eventRole_id", eventRole_id);
        userCollection.replaceOne(Filters.eq("_id", new ObjectId(id)), doc);
    }

    public void deleteEventRole_id(String id, ObjectId eventRole_id){
        // removes from the eventRole_id array
        Document doc = getFromId(id);
        doc.remove("eventRole_id", eventRole_id);
        userCollection.replaceOne(Filters.eq("_id", new ObjectId(id)), doc);
    }

}
