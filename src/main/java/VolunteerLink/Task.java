package VolunteerLink;

import java.util.Date;

public class Task {
    private String _id; // ObjectId type
    private String taskName;
    private String taskDescription;
    private String assignedTo_id; // ObjectId type
    private String createdBy_id; // ObjectId type
    private Date deadline;
    private Date completionDate;
    private String taskStatus;
    private int priority;
    private String event_id; // ObjectId type

    // Default constructor
    public Task() {
    }

    // Parameterized constructor
    public Task(String _id, String taskName, String taskDescription, String assignedTo_id,
                String createdBy_id, Date deadline, Date completionDate, String taskStatus,
                int priority, String event_id) {
        this._id = _id;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.assignedTo_id = assignedTo_id;
        this.createdBy_id = createdBy_id;
        this.deadline = deadline;
        this.completionDate = completionDate;
        this.taskStatus = taskStatus;
        this.priority = priority;
        this.event_id = event_id;
    }

    // Getters and setters
    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getAssignedToId() {
        return assignedTo_id;
    }

    public void setAssignedToId(String assignedTo_id) {
        this.assignedTo_id = assignedTo_id;
    }

    public String getCreatedById() {
        return createdBy_id;
    }

    public void setCreatedById(String createdBy_id) {
        this.createdBy_id = createdBy_id;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getEventId() {
        return event_id;
    }

    public void setEventId(String event_id) {
        this.event_id = event_id;
    }

    // toString method for printing
    @Override
    public String toString() {
        return "Task{" +
                "_id='" + _id + '\'' +
                ", taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", assignedTo_id='" + assignedTo_id + '\'' +
                ", createdBy_id='" + createdBy_id + '\'' +
                ", deadline=" + deadline +
                ", completionDate=" + completionDate +
                ", taskStatus='" + taskStatus + '\'' +
                ", priority=" + priority +
                ", event_id='" + event_id + '\'' +
                '}';
    }
}
