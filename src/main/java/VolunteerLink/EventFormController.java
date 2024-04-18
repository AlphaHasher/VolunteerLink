package VolunteerLink;

// import javax.swing.text.Document;

// FormController.java
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.GetMapping;

//have to designate classes as controllers to work with front end.
//EventManager has been changed to EventFormController
//Method below used in side project and needs to be updated to accept all relevant Event data.
@Controller
public class EventFormController {


    //Defines Type of mapping and endpoint
    @PostMapping("/submitEvent")


    public String submitEvent(@RequestParam("eventName") String eventName, @RequestParam("location") String location,
    @RequestParam("eventDescription") String eventDescription, @RequestParam("startDate") String startDate,
    @RequestParam("endDate") String endDate, @RequestParam("volunteersNeeded") int volunteersNeeded) {

        //Need to redirect back to "Events" page after submission to show user that event has been added.
        Event event = new Event(eventName, location, eventDescription, startDate, endDate, volunteersNeeded, 0);
        System.out.println(event.toString());//take out after testing
        Database db = Database.getInstance();
        db.addEvent(event.toDocument()); // Save the event to the database
        return "redirect:/events"; // Redirect to a page that displays events
    }

}
