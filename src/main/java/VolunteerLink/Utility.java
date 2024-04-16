package VolunteerLink;

import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.bson.types.ObjectId;

public class Utility {

    // Generic method to get field value from a document in the specified collection
    public static <T> T getFieldValueFromDocument(String collectionName, String id, String fieldName, Class<T> fieldType) {
        MongoDatabase database = Database.getInstance().getDatabase();
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
        MongoDatabase database = Database.getInstance().getDatabase();
        MongoCollection<Document> collection = database.getCollection(collectionName);
        ObjectId objectId = new ObjectId(id);
        collection.updateOne(eq("_id", objectId), new Document("$set", new Document(fieldName, newValue)));
    }

    // Method to update a field in a document in the specified collection
    public static <T> T[] getEventField(String collectionName, String field, Class<T> fieldType) {
        MongoDatabase database = Database.getInstance().getDatabase();
        MongoCollection<Document> collection = database.getCollection(collectionName);
        List<T> eventField = new ArrayList<>();
        MongoCursor<Document> cursor = null;

        try {
            cursor = collection.find().iterator();

            while (cursor.hasNext()) {
                Document doc = cursor.next();
                T value = doc.get(field, fieldType);
                eventField.add(value);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        @SuppressWarnings("unchecked")
        T[] array = (T[]) java.lang.reflect.Array.newInstance(fieldType, eventField.size());
        return eventField.toArray(array);
    }

    // Method to get all documents in the specified collection given a field name and value
    public static List<Document> findDocumentsByFieldValue(String collectionName, String fieldName, Object fieldValue) {
        MongoDatabase database = Database.getInstance().getDatabase();
        MongoCollection<Document> collection = database.getCollection(collectionName);
        List<Document> matchingDocuments = new ArrayList<>();

        MongoCursor<Document> cursor = collection.find(Filters.eq(fieldName, fieldValue)).iterator();

        try {
            while (cursor.hasNext()) {
                matchingDocuments.add(cursor.next());
            }
        } finally {
            cursor.close();
        }

        return matchingDocuments;
    }

    public static void convertToDate() {
        // Depending on how we implement the date (how we get it from the user)
        // We will need to convert it to a Date object to store in the database
        // This is a placeholder method for now
    }
}
