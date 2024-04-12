package VolunteerLink;

import java.util.Date;

public class Event {

    private String id; // MongoDB makes _ids automatically unique when imported to the DB
    private String eventName;
    private String eventDescription;

    private Date startDate;
    private Date endDate;

    private String location;
    private int volunteersNeeded;
    private int volunteersRegistered;
    private String eventStatus;
    private boolean approved;

    //Constructor
    public Event(String eventName, String description, Date startDate, Date endDate, String location, int volunteersNeeded, int volunteersRegistered, String eventStatus){
        this.eventName = eventName;
        this.eventDescription = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.volunteersNeeded = volunteersNeeded;
        this.volunteersRegistered = volunteersRegistered;
        this.eventStatus = eventStatus;
        this.approved = false;
    }

    public Event getEvent(){
        return this;
    }

    public void setEventName(String eventName){
        this.eventName = eventName;
    }
    public String getEventName(){
        return eventName;
    }

    public String getDescription() {
        return eventDescription;
    }
    public void setDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

}
