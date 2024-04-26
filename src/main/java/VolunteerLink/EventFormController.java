package VolunteerLink;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import org.bson.Document;
import org.bson.types.ObjectId;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EventFormController {

    @PostMapping("/submitEvent")
    public String submitEvent(@RequestParam("eventName") String eventName,
                              @RequestParam("location") String location,
                              @RequestParam("eventDescription") String eventDescription,
                              @RequestParam("startDate") String startDate,
                              @RequestParam("startTime") String startTime,
                              @RequestParam("endDate") String endDate,
                              @RequestParam("endTime") String endTime,
                              @RequestParam("roleNames") List<String> roleNames,
                              @RequestParam("roleDescriptions") List<String> roleDescriptions,
                              @RequestParam("numbersNeeded") List<Integer> numbersNeeded,
                              @RequestParam("tags") List<String> tags) {
        if (roleNames == null || roleNames.isEmpty()) {
            return "errorPage";
        }
        try {
            LocalDateTime startDateTime = parseDateTime(startDate, startTime);
            LocalDateTime endDateTime = parseDateTime(endDate, endTime);
            Event event = new Event(eventName, eventDescription, location, startDateTime, endDateTime, 0, 0, tags);
            ObjectId event_id = saveEvent(event);
            saveRoles(event_id, roleNames, roleDescriptions, numbersNeeded);
            return "redirect:/FrontEnd/index-test.html";
        } catch (Exception e) {
            // Log error and provide feedback to the user or system
            return "errorPage";
        }
    }

    private LocalDateTime parseDateTime(String date, String time) {
        return LocalDateTime.of(LocalDate.parse(date), LocalTime.parse(time));
    }

    private ObjectId saveEvent(Event event) {
        Database.getInstance().getEventCollection().insertOne(event.toDocument());
        return Database.getInstance().getEventCollection().find(Filters.eq("eventName", event.getEventName())).first().getObjectId("_id");
        // !!! Need a better way to return the ID of the event we just created because there is a change that the event name is not unique
    }

    private void saveRoles(ObjectId event_id, List<String> roleNames, List<String> roleDescriptions, List<Integer> numbersNeeded) {
        int totalVolunteersNeeded = 0;
        for (int i = 0; i < roleNames.size(); i++) {
            EventRole role = new EventRole(roleNames.get(i), roleDescriptions.get(i), event_id, new ArrayList<ObjectId>(), numbersNeeded.get(i), "Open");
            Database.getInstance().getEventRolesCollection().insertOne(role.toDocument());
            totalVolunteersNeeded += numbersNeeded.get(i);
        }
        Utility.updateFieldInDocument("Events", event_id.toString(), "volunteersNeeded", totalVolunteersNeeded);
    }


    @GetMapping("/events")
    public ResponseEntity<List<Event>> getEvents() {
        List<Event> events = new ArrayList<>();
        FindIterable<Document> eventDocuments = Database.getInstance().getEventCollection().find();

        for (Document doc : eventDocuments) {
            Event event = Event.convertDocumentToEvent(doc); // Implement this method to convert Document to Event
            events.add(event);
        }

        return ResponseEntity.ok(events);
    }


}
