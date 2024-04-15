package VolunteerLink;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

public class EventRole {

    private String roleName;
    private String roleDescription;
    private Double hoursParticipated;
    private String event_id; // MongoDB ID

    private MongoCollection<Document> eventRolesCollection;

    // Default constructor
    public EventRole() {
        this.eventRolesCollection = Database.getInstance().getEventRolesCollection();
    }

    // Parameterized constructor
    public EventRole(String roleName, String roleDescription, Double hoursParticipated, String event_id) {
        this.roleName = roleName;
        this.roleDescription = roleDescription;
        this.hoursParticipated = hoursParticipated;
        this.event_id = event_id;
    }

    private Document getFromId(String id) {
        ObjectId objectId = new ObjectId(id);
        Document doc = eventRolesCollection.find(Filters.eq("_id", objectId)).first();
        return doc;
    }

    // ********************************************
    // *** Getters and setters for class fields ***
    // ********************************************

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

    public Double getHoursParticipated() {
        return hoursParticipated;
    }

    public void setHoursParticipated(Double hoursParticipated) {
        this.hoursParticipated = hoursParticipated;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    // ***********************************************
    // *** Getters and setters for database fields ***
    // ***********************************************


    public String getRoleName(String id) {
        Document doc = getFromId(id);
        return doc.getString("roleName");
    }

    public void setRoleName(String id, String roleName) {
        Document doc = getFromId(id);
        doc.put("roleName", roleName);
    }

    public String getRoleDescription(String id) {
        Document doc = getFromId(id);
        return doc.getString("roleDescription");
    }

    public void setRoleDescription(String id, String roleDescription) {
        Document doc = getFromId(id);
        doc.put("roleDescription", roleDescription);
    }

    public Double getHoursParticipated(String id) {
        Document doc = getFromId(id);
        return doc.getDouble("hoursParticipated");
    }

    public void setHoursParticipated(String id, Double hoursParticipated) {
        Document doc = getFromId(id);
        doc.put("hoursParticipated", hoursParticipated);
    }

    public String getEvent_id(String id) {
        Document doc = getFromId(id);
        return doc.getString("event_id");
    }

    public void setEvent_id(String id, String event_id) {
        Document doc = getFromId(id);
        doc.put("event_id", event_id);
    }

    // toString method for printing
    @Override
    public String toString() {
        return "EventRole{" +
                "roleName='" + roleName + '\'' +
                ", roleDescription='" + roleDescription + '\'' +
                ", hoursParticipated=" + hoursParticipated +
                ", event_id='" + event_id + '\'' +
                '}';
    }
}
