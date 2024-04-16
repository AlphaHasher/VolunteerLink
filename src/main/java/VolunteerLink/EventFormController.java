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
// have been moved to the Database/Utility class. We'll see.
package VolunteerLink;

import javax.swing.text.Document;

// FormController.java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

//have to designate classes as controllers to work with front end.
//EventManager has been changed to EventFormController
//Method below used in side project and needs to be updated to accept all relevant Event data.
@Controller
public class EventFormController {


    //Defines Type of mapping and endpoint
    @PostMapping("/submitEvent")

    //needs to be updated to work with all parameters
    public String submitEvent(@RequestParam("eventName") String eventName, @RequestParam("location") String location, @RequestParam("eventDescription") String eventDescription, @RequestParam("startDate") String startDate) {
        // Process the form data
       //Event event = new Event(eventName, eventDescription, location, startDate);
       // System.out.println(event.toString());
        //Database db = Database.getInstance();
       // db.addEvent(event); // Save the event to the database
        return "redirect:/success"; // Redirect to a success page
    }
    
}
