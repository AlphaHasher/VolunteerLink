package VolunteerLink;
import org.bson.Document;

//import org.w3c.dom.events.Event;
// template for our events class. Can be adjusted in future after inputs are more defined.
public class Event {

    private String id;
    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private int volunteersNeeded;
    private int volunteersRegistered;
    private String eventStatus;
    private boolean approved;
    private String location;
    

    //Constructor
    public Event(String name, String description, String location){
        this.name = name;
        this.description = description;
        this.location = location;
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
