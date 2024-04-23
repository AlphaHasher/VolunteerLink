package VolunteerLink;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

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

    public String submitEvent(@RequestParam("eventName") String eventName,
                          @RequestParam("location") String location,
                          @RequestParam("eventDescription") String eventDescription,
                          @RequestParam("startDate") String startDate,
                          @RequestParam("startTime") String startTime,
                          @RequestParam("endDate") String endDate,
                          @RequestParam("endTime") String endTime,
                          @RequestParam("volunteersNeeded") int volunteersNeeded) {

    LocalDateTime startDateTime = LocalDateTime.of(LocalDate.parse(startDate), LocalTime.parse(startTime));
    LocalDateTime endDateTime = LocalDateTime.of(LocalDate.parse(endDate), LocalTime.parse(endTime));

    //Needs to redirect back to index page after submission to show user that event has been added.
    Event event = new Event(eventName, location, eventDescription, startDateTime, endDateTime, volunteersNeeded, 0);
    Database.getInstance().addEvent(event.toDocument()); // Save the event to the database
    return "redirect:/index.html"; // Redirect to a page that displays events
    }

}
