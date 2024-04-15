package VolunteerLink;

public class EventRole {

    private String _id; // MongoDB ID
    private String roleName;
    private String roleDescription;
    private Double hoursParticipated;
    private String event_id; // MongoDB ID

    // Default constructor
    public EventRole() {
    }

    // Parameterized constructor
    public EventRole(String _id, String roleName, String roleDescription, Double hoursParticipated, String event_id) {
        this._id = _id;
        this.roleName = roleName;
        this.roleDescription = roleDescription;
        this.hoursParticipated = hoursParticipated;
        this.event_id = event_id;
    }

    // Getter and setter for _id
    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    // Getter and setter for roleName
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    // Getter and setter for roleDescription
    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    // Getter and setter for hoursParticipated
    public Double getHoursParticipated() {
        return hoursParticipated;
    }

    public void setHoursParticipated(Double hoursParticipated) {
        this.hoursParticipated = hoursParticipated;
    }

    // Getter and setter for event_id
    public String getEventId() {
        return event_id;
    }

    public void setEventId(String event_id) {
        this.event_id = event_id;
    }

    // toString method for printing
    @Override
    public String toString() {
        return "EventRole{" +
                "_id='" + _id + '\'' +
                ", roleName='" + roleName + '\'' +
                ", roleDescription='" + roleDescription + '\'' +
                ", hoursParticipated=" + hoursParticipated +
                ", event_id='" + event_id + '\'' +
                '}';
    }
}
