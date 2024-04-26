package VolunteerLink;

import java.util.Date;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;

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

    //Just for adding user to db....registration date is only needed when signing up for event?
    public User(String email,String password,String firstName,String lastName,String role){
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    // Parse database for matching email and password, then return User.
    // Is this still needed if we already have the login method in the Database class?
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

    public Document toDocument() {
        Document user = new Document();
        user.append("email", this.email)
           .append("password", this.password)
           .append("firstName", this.firstName)
           .append("lastName", this.lastName)
           .append("role", this.role);
        return user;
    }

    // ***********************************************
    // *** Getters and setters for database fields ***
    // ***********************************************

    // private Document getFromId(String id) {
    //     ObjectId objectId = new ObjectId(id);
    //     Document doc = userCollection.find(Filters.eq("_id", objectId)).first();
    //     return doc;
    // }
}
