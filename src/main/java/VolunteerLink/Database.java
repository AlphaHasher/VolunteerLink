package VolunteerLink;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Database {

    private static Database instance;
    private MongoClient mongoClient;
    private MongoDatabase database;

    private static final String DATABASE_NAME = "VolunteerLink";

    private Database() {
        String uri;
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties\nVerify that the config.properties file is in the VolunteerLink/src/main/java folder.");
                return;
            }
            prop.load(input);
            uri = prop.getProperty("MongoDBKey");
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        mongoClient = MongoClients.create(uri);
        database = mongoClient.getDatabase(DATABASE_NAME);
    }

    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public MongoCollection<Document> getEventCollection() {
        return database.getCollection("Events");
    }
}
