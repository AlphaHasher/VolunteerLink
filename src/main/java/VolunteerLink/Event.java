package VolunteerLink;
import org.bson.Document;

//import org.w3c.dom.events.Event; // What is the purpose of this import?
// template for our events class. Can be adjusted in future after inputs are more defined.
public class Event {

    private String id; // need a way to generate unique IDs
    private String name;
    private String description;

    private String startDate; // needs to be a date object NOT a string
    private String endDate;

    private String location;
    private int volunteersNeeded;
    private int volunteersRegistered;
    private String eventStatus;
    private boolean approved;

    //Constructor
    public Event(String name, String description, String startDate, String endDate, String location, int volunteersNeeded, int volunteersRegistered, String eventStatus){
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.volunteersNeeded = volunteersNeeded;
        this.volunteersRegistered = volunteersRegistered;
        this.eventStatus = eventStatus;
        this.approved = false;
    }

    //default constructor
    public Event(){
        this.approved = false;
    }


    public Event getEvent(){
        return this;
    }


    public void setEventName(String name){
        this.name = name;
    }
    public String getEventName(){
        return name;
    }


    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }


    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }
    public int getVolunteersNeeded() {
        return volunteersNeeded;
    }


    public void setVolunteersNeeded(int volunteersNeeded) {
        this.volunteersNeeded = volunteersNeeded;
    }
    public int getVolunteersRegistered() {
        return volunteersRegistered;
    }


    public void setVolunteersRegistered(int volunteersRegistered) {
        this.volunteersRegistered = volunteersRegistered;
    }
    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }


    public boolean isApproved() {
        return approved;
    }
    public void setApproved(boolean approved) {
        this.approved = approved;
    }


    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    //Converts events to Documents to be stored in DB.
    public Document toDocument(){
        return new Document("name", name)
                .append("description", description)
                .append("startDate", startDate)
                .append("endDate", endDate)
                .append("volunteersNeeded", volunteersNeeded)
                .append("volunteersRegistered", volunteersRegistered)
                .append("eventStatus", eventStatus)
                .append("approved", approved)
                .append("location", location);
    }

}
