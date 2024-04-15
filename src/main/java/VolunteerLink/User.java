package VolunteerLink;

import org.bson.Document;
// import static com.mongodb.client.model.Filters.eq;
// import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
// import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
// import com.mongodb.client.model.Filters;
// import com.mongodb.client.model.UpdateOptions;
// import com.mongodb.client.model.Updates;
// import com.mongodb.client.result.UpdateResult;
import com.mongodb.client.model.Filters;

// import java.util.ArrayList;
// import java.util.List;
import java.util.Date;

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

    public User(String email, String password, String firstName, String lastName, String role, Date registrationDate){
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.registrationDate = registrationDate;
    }
    // Creates new user, must have check to verify if user already exists
    // Method may not be necessary, perhaps we will just manually create users
    public void createUser(String email, String password) {

    }
    // Parse database for matching email and password, then return User.
    public User logInUser(String email, String password) {



        User newUser = new User(email, password, firstName, lastName, role, registrationDate);
        return newUser;
    }
    

    // test method, this will NOT return data that is not of the String type
    public String[] getUser(String id) {
        Document doc = getFromId(id);
        // get user data from database
        String[] userData = new String[6];
        userData[0] = doc.getObjectId("_id").toString();
        userData[1] = doc.getString("email");
        userData[2] = doc.getString("password");
        userData[3] = doc.getString("firstName");
        userData[4] = doc.getString("lastName");
        userData[5] = doc.getString("role");
        return userData;

    }

    private Document getFromId(String id) {
        ObjectId objectId = new ObjectId(id);
        Document doc = userCollection.find(Filters.eq("_id", objectId)).first();
        return doc;
    }

    public String getName(String id){
        Document doc = getFromId(id);
        return doc.getString("firstName") + " " + doc.getString("lastName");
    }

    public void setName(String id, String first, String last){
        Document doc = getFromId(id);
        doc.put("firstName", first);
        doc.put("lastName", last);
        userCollection.replaceOne(Filters.eq("_id", new ObjectId(id)), doc);
    }

    public String getPassword(String id, String password){
        Document doc = getFromId(id);
        return doc.getString("password");
    }

    public void setPassword(String id, String password){
        Document doc = getFromId(id);
        doc.put("password", password);
        userCollection.replaceOne(Filters.eq("_id", new ObjectId(id)), doc);
    }

    public String getEmail(String id, String email){
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        Document doc = getFromId(id);
        return doc.getString("email");
    }

    public void setEmail(String id, String email){
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        Document doc = getFromId(id);
        doc.put("email", email);
        userCollection.replaceOne(Filters.eq("_id", new ObjectId(id)), doc);
    }

    public String getRole(){
        return role;
    }

    public void setRole(String id, String role) {
        try {
            if (!"volunteer".equals(role) && !"event organizer".equals(role) && !"admin".equals(role)) {
                throw new IllegalArgumentException("Invalid role. Please enter 'volunteer', 'event organizer', or 'admin'.");
            }
            Document doc = getFromId(id);
            doc.put("role", role);
            userCollection.replaceOne(Filters.eq("_id", new ObjectId(id)), doc);
        } catch (IllegalArgumentException e) {
            System.err.println("Error setting role: " + e.getMessage());
            throw e; // Rethrowing the exception after logging it
        }
    }

    public Date getRegistrationDate(String id){
        Document doc = getFromId(id);
        return doc.getDate("registrationDate");
    }

    public void setRegistrationDate(String id, Date registrationDate){
        Document doc = getFromId(id);
        doc.put("registrationDate", registrationDate);
        userCollection.replaceOne(Filters.eq("_id", new ObjectId(id)), doc);
    }

    public void selectEvent(String id){
        // TODO
    }

    public void selectPosition(String id){
        // TODO
    }
}
