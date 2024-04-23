package VolunteerLink;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import org.bson.types.ObjectId;

// import javax.swing.text.Document;

// FormController.java
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.GetMapping;

import com.mongodb.client.model.Filters;


//have to designate classes as controllers to work with front end.
//EventManager has been changed to EventFormController
//Method below used in side project and needs to be updated to accept all relevant Event data.
@Controller
public class EventFormController {


    //Defines Type of mapping and endpoint
    @PostMapping("/submitEvent")

    public String submitEvent(
        @RequestParam("eventName") String eventName,
        @RequestParam("location") String location,
        @RequestParam("eventDescription") String eventDescription,
        @RequestParam("startDate") String startDate,
        @RequestParam("startTime") String startTime,
        @RequestParam("endDate") String endDate,
        @RequestParam("endTime") String endTime,
        @RequestParam("roleNames") List<String> roleNames,
        @RequestParam("roleDescriptions") List<String> roleDescriptions,
        @RequestParam("numbersNeeded") List<Integer> numbersNeeded) {

        LocalDateTime startDateTime = LocalDateTime.of(LocalDate.parse(startDate), LocalTime.parse(startTime));
        LocalDateTime endDateTime = LocalDateTime.of(LocalDate.parse(endDate), LocalTime.parse(endTime));
        Event event = new Event(eventName, eventDescription, location, startDateTime, endDateTime, 0, 0);
        Database.getInstance().getEventCollection().insertOne(event.toDocument());

        // search for the event we just created to get the event_id
        ObjectId event_id = Database.getInstance().getEventCollection().find(Filters.eq("eventName", eventName)).first().getObjectId("_id");

        // Adding roles to the database and updating volunteer count
        int totalVolunteersNeeded = 0;
        for (int i = 0; i < roleNames.size(); i++) {
            EventRole role = new EventRole(roleNames.get(i), roleDescriptions.get(i), event_id, new ArrayList<ObjectId>(), numbersNeeded.get(i), "Open");
            Database.getInstance().getEventRolesCollection().insertOne(role.toDocument());
            totalVolunteersNeeded += numbersNeeded.get(i);
        }
        Utility.updateFieldInDocument("Events", event_id.toString(), "totalVolunteersNeeded", totalVolunteersNeeded);

        return "redirect:/FrontEnd/index.html"; // currently redirects to index page
    }

}
