package VolunteerLink;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    // Other admin-related endpoints and methods can go here
}
