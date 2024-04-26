package VolunteerLink;

import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

public class User {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
    private Date accountCreationDate;
    private List<ObjectId> eventRole_id;

    public User(String email, String password, String firstName, String lastName, String role, Date accountCreationDate, List<ObjectId> eventRole_id){
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.accountCreationDate = accountCreationDate;
        this.eventRole_id = eventRole_id;
    }

    // Parse database for matching email and password, then return User.
    // Is this still needed if we already have the login method in the Database class?
    public User logInUser(String email, String password) {
        User newUser = new User(email, password, firstName, lastName, role, accountCreationDate, eventRole_id);
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

    public Date getaccountCreationDate() {
        return accountCreationDate;
    }

    public void setaccountCreationDate(Date accountCreationDate) {
        this.accountCreationDate = accountCreationDate;
    }

    public List<ObjectId> getEventRole_id() {
        return eventRole_id;
    }

    public void setEventRole_id(List<ObjectId> eventRole_id) {
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
           .append("role", this.role)
           .append("accountCreationDate", this.accountCreationDate)
           .append("eventRole_id", this.eventRole_id);
        return user;
    }

    public static User convertDocoumentToUesr(Document doc){
        if (doc == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }

        User user = new User(
            doc.getString("email"),
            doc.getString("password"),
            doc.getString("firstName"),
            doc.getString("lastName"),
            doc.getString("role"),
            doc.getDate("accountCreationDate"),
            (List<ObjectId>) doc.get("eventRole_id")
        );

        return user;
    }

}
