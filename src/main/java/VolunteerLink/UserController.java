package VolunteerLink;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;

@Controller
public class UserController {

    @PostMapping("/register")
    public String postMethodName(@RequestParam("email") String email,
                                @RequestParam("password") String password,
                                @RequestParam("confirmPassword") String confirmPassword,
                                @RequestParam("firstName") String firstName,
                                @RequestParam("lastName") String lastName,
                                @RequestParam("role") String role) {

        //TODO: process POST request

        if (!password.equals(confirmPassword)) {
            return "errorPage";
        }

        Date accountCreationDate = new Date();
        List<ObjectId> eventRole_id = null;

        try {
            User user = new User(email, password, firstName, lastName, role, accountCreationDate, eventRole_id);
            Database.getInstance().getUsersCollection().insertOne(user.toDocument());

            return "redirect:/FrontEnd/login-page.html";//needs to login user or redirect to login page

        } catch (Exception e) {
            return "errorPage";
        }
    }
}
