package VolunteerLink;

import java.util.Date;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

public class Task {
    private String taskName;
    private String taskDescription;
    private String assignedTo_id; // ObjectId type
    private String createdBy_id; // ObjectId type
    private Date deadline;
    private Date completionDate;
    private String taskStatus;
    private int priority;
    private String event_id; // ObjectId type

    private MongoCollection<Document> tasksCollection;

    // Default constructor
    public Task() {
        this.tasksCollection = Database.getInstance().getTasksCollection();
    }

    // Parameterized constructor
    public Task(String taskName, String taskDescription, String assignedTo_id,
                String createdBy_id, Date deadline, Date completionDate, String taskStatus,
                int priority, String event_id) {
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

    private Document getFromId(String id) {
        ObjectId objectId = new ObjectId(id);
        Document doc = tasksCollection.find(Filters.eq("_id", objectId)).first();
        return doc;
    }

    // ********************************************
    // *** Getters and setters for class fields ***
    // ********************************************

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

    public String getAssignedTo_id() {
        return assignedTo_id;
    }

    public void setAssignedTo_id(String assignedTo_id) {
        this.assignedTo_id = assignedTo_id;
    }

    public String getCreatedBy_id() {
        return createdBy_id;
    }

    public void setCreatedBy_id(String createdBy_id) {
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

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    // Database methods

    // Method to add a task to the database
    public void addTask(Document task) {
        tasksCollection.insertOne(task);
    }

    // Method to delete a task from the database
    public void deleteTask(String id) {
        ObjectId objectId = new ObjectId(id);
        tasksCollection.deleteOne(Filters.eq("_id", objectId));
    }

    // Method to update a task in the database
    public void updateTask(String id, Document newTask) {
        ObjectId objectId = new ObjectId(id);
        tasksCollection.replaceOne(Filters.eq("_id", objectId), newTask);
    }

    // Method to retrieve a task from the database
    public Document getTask(String id) {
        ObjectId objectId = new ObjectId(id);
        Document doc = tasksCollection.find(Filters.eq("_id", objectId)).first();
        return doc;
    }

    // ***********************************************
    // *** Getters and setters for database fields ***
    // ***********************************************

    // public String getTaskName(String id) {
    //     Document doc = getFromId(id);
    //     return doc.getString("taskName");
    // }

    // public void setTaskName(String id, String taskName) {
    //     Document doc = getFromId(id);
    //     doc.put("taskName", taskName);
    // }

    // public String getTaskDescription(String id, String taskDescription) {
    //     Document doc = getFromId(id);
    //     return doc.getString("taskDescription");
    // }

    // public void setTaskDescription(String id, String taskDescription) {
    //     Document doc = getFromId(id);
    //     doc.put("taskDescription", taskDescription);
    // }

    // public String getAssignedToId(String id) {
    //     Document doc = getFromId(id);
    //     return doc.getString("assignedTo_id");
    // }

    // public void setAssignedToId(String id, String assignedTo_id) {
    //     Document doc = getFromId(id);
    //     doc.put("assignedTo_id", assignedTo_id);
    // }

    // public String getCreatedById(String id) {
    //     Document doc = getFromId(id);
    //     return doc.getString("createdBy_id");
    // }

    // public void setCreatedById(String id, String createdBy_id) {
    //     Document doc = getFromId(id);
    //     doc.put("createdBy_id", createdBy_id);
    // }

    // public String getDeadline(String id) {
    //     Document doc = getFromId(id);
    //     return doc.getString("deadline");
    // }

    // public void setDeadline(String id, String deadline) {
    //     Document doc = getFromId(id);
    //     doc.put("deadline", deadline);
    // }

    // public String getCompletionDate(String id) {
    //     Document doc = getFromId(id);
    //     return doc.getString("completionDate");

    // }

    // public void setCompletionDate(String id, String completionDate) {
    //     Document doc = getFromId(id);
    //     doc.put("completionDate", completionDate);
    // }

    // public String getTaskStatus(String id) {
    //     Document doc = getFromId(id);
    //     return doc.getString("taskStatus");

    // }

    // public void setTaskStatus(String id, String taskStatus) {
    //     Document doc = getFromId(id);
    //     doc.put("taskStatus", taskStatus);
    // }

    // public int getPriority(String id) {
    //     Document doc = getFromId(id);
    //     return doc.getInteger("priority");
    // }

    // public void setPriority(String id, int priority) {
    //     Document doc = getFromId(id);
    //     doc.put("priority", priority);
    // }

    // public String getEventId(String id) {
    //     Document doc = getFromId(id);
    //     return doc.getString("event_id");
    // }

    // public void setEventId(String id, String event_id) {
    //     Document doc = getFromId(id);
    //     doc.put("event_id", event_id);
    // }


    // toString method for printing
    @Override
    public String toString() {
        return "Task{" +
                "taskName='" + taskName + '\'' +
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
