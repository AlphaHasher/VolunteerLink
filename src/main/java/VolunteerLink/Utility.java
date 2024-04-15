package VolunteerLink;

import java.util.Date;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;

public class Utility {

    // Method to get field value from a document in the specified collection
    public static <T> T getFieldValueFromDocument(String collectionName, String id, String fieldName, Class<T> fieldType) {
        MongoDatabase database = Database.getInstance().getDatabase(); // Ensure there's a method to get the database
        MongoCollection<Document> collection = database.getCollection(collectionName);
        ObjectId objectId = new ObjectId(id);
        Document document = collection.find(eq("_id", objectId)).first();
        if (document != null) {
            return document.get(fieldName, fieldType);
        }
        return null;
    }

    // Method to update a field in a document in the specified collection
    public static void updateFieldInDocument(String collectionName, String id, String fieldName, Object newValue) {
        MongoDatabase database = Database.getInstance().getDatabase(); // Ensure there's a method to get the database
        MongoCollection<Document> collection = database.getCollection(collectionName);
        ObjectId objectId = new ObjectId(id);
        collection.updateOne(eq("_id", objectId), new Document("$set", new Document(fieldName, newValue)));
    }

    public static void convertToDate() {
        // Depending on how we implement the date (how we get it from the user)
        // We will need to convert it to a Date object to store in the database
        // This is a placeholder method for now
    }
}
