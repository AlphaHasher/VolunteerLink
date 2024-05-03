package VolunteerLink;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @GetMapping("/admin-test")
    public String getUsersAndEvents(@RequestParam(required = false, defaultValue = "10") int loadMoreCount, Model model) {
        List<User> users = userService.getAllUsers();
        List<Event> events = eventService.getNextEvents(loadMoreCount);

        model.addAttribute("users", users);
        model.addAttribute("events", events);

        return "admin-test"; // Return the name of your Thymeleaf template
    }

    
    @PostMapping("/deleteEvent")
    public String adminDeleteEvent(@RequestParam String eventId) {
    // Call your service method to delete the event
    Database.deleteEvent(eventId);
    System.out.println(eventId);
    return "redirect:/admin-test";
    }

    @PostMapping("/promoteUser")
    public String promote(@RequestParam String eventId) {
    // Call your service method to delete the event
    //Database.promoteUser(eventId);
    return "redirect:/admin-test";
    }

    @PostMapping("/revokeUser")
    public String revoke(@RequestParam String eventId) {
    // Call your service method to delete the event
    //Database.revokeUser(eventId);
    return "redirect:/admin-test";
    }

    // Other admin-related endpoints and methods can go here
}
