package VolunteerLink;

//import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;

//import java.util.Date;

import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserController {
    
    @PostMapping("/register")
    public String postMethodName(@RequestParam("email") String email,
                                @RequestParam("password") String password,
                                @RequestParam("firstName") String firstName,
                                @RequestParam("lastName") String lastName,
                                @RequestParam("role") String role) {
        System.out.println("adding user");
        //TODO: process POST request
        try {
            System.out.println("adding user");
            User user = new User(email, password, firstName, lastName, role);
            Database.getInstance().getUsersCollection().insertOne(user.toDocument());
            return "redirect:/FrontEnd/login-page.html";//needs to login user or redirect to login page
        } catch (Exception e) {
            // Log error and provide feedback to the user or system
            return "errorPage";
        }
    }  
    
}
