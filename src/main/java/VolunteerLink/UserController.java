package VolunteerLink;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodb.client.MongoCollection;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;
import com.mongodb.client.model.Filters;
import org.springframework.ui.Model;
import java.util.Map;
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

        if (!password.equals(confirmPassword)) {
            return "errorPage";
        }

        Date accountCreationDate = new Date();
        List<ObjectId> eventRole_id = null;

        try {
            User user = new User(email, password, firstName, lastName, role, accountCreationDate, eventRole_id);
            Database.getInstance().getUsersCollection().insertOne(user.toDocument());

            return "redirect:/FrontEnd/login-page.html";

        } catch (Exception e) {
            return "errorPage";
        }
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        HttpSession session) {
        MongoCollection<Document> usersCollection = Database.getInstance().getUsersCollection();
        Document userDoc = usersCollection.find(Filters.eq("email", email)).first();

        if (userDoc != null && userDoc.getString("password").equals(password)) {
            // session.setAttribute("userId", userDoc.getObjectId("_id").toString());
            session.setAttribute("userId", userDoc.getObjectId("_id"));

            // redirect to respective pages. Need to create event organizer page and volunteer page
            if(userDoc != null && userDoc.getString("role").equals("volunteer")){
                return "redirect:/FrontEnd/create-account.html";
            }else if(userDoc != null && userDoc.getString("role").equals("Event Organizer")){
                return "redirect:/FrontEnd/Form.html";

            }else if(userDoc != null && userDoc.getString("role").equals("Admin")){
                return "redirect:/admin-test";
            }
            // return "redirect:/FrontEnd/Form.html";//leaving in for now
        }
        return "redirect:/FrontEnd/login-page.html";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/FrontEnd/login-page.html";
    }

    @GetMapping("/user")
    public String getUser(Model model, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/FrontEnd/login-page.html";
        }

        MongoCollection<Document> usersCollection = Database.getInstance().getUsersCollection();
        Document userDoc = usersCollection.find(Filters.eq("_id", new ObjectId(userId))).first();

        if (userDoc == null) {
            return "redirect:/FrontEnd/login-page.html";
        }

        return userDoc.toJson();
    }

    @GetMapping("/sessionId")
    @ResponseBody
    public Map<String, Object> getSessionData(HttpSession session) {
        Map<String, Object> sessionData = new HashMap<>();
        sessionData.put("userId", session.getAttribute("userId"));
        return sessionData;
    }

}
