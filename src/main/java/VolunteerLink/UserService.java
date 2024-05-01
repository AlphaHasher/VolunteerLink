package VolunteerLink;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.stereotype.Service;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

@Service
public class UserService {

    // Method to retrieve all users from the database
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        // Retrieve the users collection from the database
        MongoCollection<Document> usersCollection = Database.getInstance().getUsersCollection();

        // Query all documents from the users collection
        FindIterable<Document> userDocuments = usersCollection.find();

        // Iterate over the documents and convert them to User objects
        try (MongoCursor<Document> cursor = userDocuments.iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                User user = convertDocumentToUser(doc);
                users.add(user);
            }
        }

        return users;
    }

    // Method to convert a Document object to a User object
    private static User convertDocumentToUser(Document doc) {
        String email = doc.getString("email");
        String password = doc.getString("password");
        String firstName = doc.getString("firstName");
        String lastName = doc.getString("lastName");
        String role = doc.getString("role");

        return new User(email, password, firstName, lastName, role);
    }
}

// =========================================================================
// This was code written for SpringBoot security, but was never implemented.
// =========================================================================