// package VolunteerLink;

// import org.bson.Document;

// import static com.mongodb.client.model.Filters.eq;
// import com.mongodb.client.MongoCollection;

// public class EventManager {

//     private MongoCollection<Document> eventCollection;

//     public EventManager() {
//         this.eventCollection = Database.getInstance().getEventCollection();
//     }

//     // returns every event in the database (will need to implement a way to filter later)
//     public String getEvents() {
//         StringBuilder eventsString = new StringBuilder();
//         Iterable<Document> documents = eventCollection.find();

//         for (Document document : documents) {
//             eventsString.append(document.toString()).append("\n");
//         }
//         return eventsString.toString();
//     }



//     public void deleteEvent(String id){
//         eventCollection.deleteOne(eq("_id", id));
//     }
// }


// Looks like this entire class is deprecated since all its functions
// have been moved to the Database class. We'll see.