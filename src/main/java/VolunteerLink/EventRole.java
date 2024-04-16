package VolunteerLink;

// import org.bson.Document;
// import org.bson.types.ObjectId;

// import com.mongodb.client.MongoCollection;
// import com.mongodb.client.model.Filters;

public class EventRole {

    private String roleName;
    private String roleDescription;
    private Double hoursParticipated;
    private String event_id; // MongoDB ID

    // private MongoCollection<Document> eventRolesCollection;

    // Default constructor
    // public EventRole() {
    //     this.eventRolesCollection = Database.getInstance().getEventRolesCollection();
    // }

    // Parameterized constructor
    public EventRole(String roleName, String roleDescription, Double hoursParticipated, String event_id) {
        this.roleName = roleName;
        this.roleDescription = roleDescription;
        this.hoursParticipated = hoursParticipated;
        this.event_id = event_id;
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
}
