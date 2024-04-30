package VolunteerLink;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Arrays;

// import com.mongodb.client.MongoCollection;
// import com.mongodb.client.model.Filters;

public class EventRole {

    private String roleName;
    private String roleDescription;
    private ObjectId eventRole_id;
    private List<ObjectId> assignedUsers;
    private int numberAssigned;
    private int numberNeeded;
    private String roleStatus;

    public EventRole(String roleName, String roleDescription, ObjectId eventRole_id, List<ObjectId> assignedUsers, int numberNeeded, String roleStatus) {
        this.roleName = roleName;
        this.roleDescription = roleDescription;
        this.eventRole_id = eventRole_id;
        this.assignedUsers = assignedUsers;
        this.numberNeeded = numberNeeded;
        this.roleStatus = roleStatus;
    }

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
        return assignedUsers.toArray(new ObjectId[assignedUsers.size()]);
    }

    public void setAssignedUsers(ObjectId[] assignedUsers) {
        this.assignedUsers = Arrays.asList(assignedUsers);
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
