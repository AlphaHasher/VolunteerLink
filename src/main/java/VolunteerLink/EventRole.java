package VolunteerLink;

import org.bson.Document;
import org.bson.types.ObjectId;

// import com.mongodb.client.MongoCollection;
// import com.mongodb.client.model.Filters;

public class EventRole {

    private String roleName;
    private String roleDescription;
    private ObjectId eventRole_id;
    private ObjectId [] assignedUsers;
    private int numberAssigned;
    private int numberNeeded;
    private String roleStatus;


    // private MongoCollection<Document> eventRolesCollection;

    // Default constructor
    // public EventRole() {
    //     this.eventRolesCollection = Database.getInstance().getEventRolesCollection();
    // }

    // Parameterized constructor
    public EventRole(String roleName, String roleDescription, ObjectId eventRole_id, ObjectId [] assignedUsers, int numberAssigned, int numberNeeded, String roleStatus) {
        this.roleName = roleName;
        this.roleDescription = roleDescription;
        this.eventRole_id = eventRole_id;
        this.assignedUsers = assignedUsers;
        this.numberNeeded = numberNeeded;
        this.roleStatus = roleStatus;
    }

    // private Document getFromId(String id) {
    //     ObjectId objectId = new ObjectId(id);
    //     Document doc = eventRolesCollection.find(Filters.eq("_id", objectId)).first();
    //     return doc;
    // }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public ObjectId getEventRole_id() {
        return eventRole_id;
    }

    public void setEventRole_id(ObjectId eventRole_id) {
        this.eventRole_id = eventRole_id;
    }

    public ObjectId[] getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(ObjectId[] assignedUsers) {
        this.assignedUsers = assignedUsers;
    }

    public int getNumberAssigned() {
        return numberAssigned;
    }

    public void setNumberAssigned(int numberAssigned) {
        this.numberAssigned = numberAssigned;
    }

    public int getNumberNeeded() {
        return numberNeeded;
    }

    public void setNumberNeeded(int numberNeeded) {
        this.numberNeeded = numberNeeded;
    }

    public String getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(String roleStatus) {
        this.roleStatus = roleStatus;
    }

    public Document toDocument() {
        Document doc = new Document("roleName", roleName)
            .append("roleDescription", roleDescription)
            .append("eventRole_id", eventRole_id)
            .append("assignedUsers", assignedUsers)
            .append("numberAssigned", 0) // initially there are no assigned users
            .append("numberNeeded", numberNeeded)
            .append("roleStatus", roleStatus);
        return doc;
    }
}
