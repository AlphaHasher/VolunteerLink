package VolunteerLink;

import org.bson.types.ObjectId;

public class App
{
    public static void main( String[] args ) {

        // Optimization checker
        long startTime = System.nanoTime();

        Database database = Database.getInstance();

        testEventsPage(); // Calls method to displaying details of all events in DB


        /* User newuser = new User();
        String[] userData = newuser.getUser("6611f81958ee2db88bd6d9ea");
        for (int i = 0; i < userData.length; i++) {
            System.out.println(userData[i]);
        }*/


        // ----------------- Event Creation Test -----------------
        // Document event = new Document("name", "Sample Event")
        //     .append("description", "This is a sample event.")
        //     .append("startDate", "2017-02-08T12:30:00")
        //     .append("endDate", "2017-02-08T20:10:00")
        //     .append("location", "Sample Location")
        //     .append("volunteersNeeded", 10)
        //     .append("volunteersRegistered", 0)
        //     .append("eventStatus", "Pending");

        // eventManager.addEvent(event);
        // ----------------- Event Creation Test -----------------

        // ----------------- Event Deletion Test -----------------
        // eventManager.deleteEvent("21");
        // ----------------- Event Deletion Test -----------------

            // ----------------- Event Approval Test -----------------
            // admin.denyEvent("Sample Event");
            // admin.approveEvent("Sample Event");
            // ----------------- Event Approval Test -----------------
        long endTime = System.nanoTime();
        long durationInMilliseconds = (endTime - startTime) / 1_000_000;
        System.out.println("Execution time: " + durationInMilliseconds + " milliseconds");
    }



    // Method for Colin to put his testing code to help keep main clean.
    public static void colinTesting() {
            // Testing section for Colin
            //Event testEvent = new Event("eventName", "description", "startDate", "endDate", "location", 2, 1, "Pending"); // test import
            //Document testDocument = testEvent.toDocument();
            //Events.insertOne(testDocument);
            //MongoDatabase MongoDB = database.getDatabase();
            //MongoDB.getCollection("Events");
            //MongoCollection<Document> eventCollection = MongoDB.getCollection("Events");
            Database database = null;
            database = database.getInstance();



            ObjectId eventId = new ObjectId();
            System.out.println(eventId.toString());


            int size = database.getEventLocations().length;
            String[] locationsArr = database.getEventLocations();
            String[] descriptionsArr = database.getEventDescriptions();
            String[] statusesArr = database.getEventStatuses();

            String[] eventNamesArr = database.getEventField("eventName");

            ObjectId[] IdsArr = database.getEventIds(); 

            // This for loop will display details for every event in the system.
            for (int i = 0; i < size; ++i) {
                System.out.printf("Location: %-20s Description: %-30s Status: %-15s Name: %-15s _id: %-20s%n",
                locationsArr[i], descriptionsArr[i], statusesArr[i], eventNamesArr[i], IdsArr[i]);
            }
            //System.out.println(testUser.getEvents());
            //testUser.viewEventNames();
            /* String[] arr = testEvent.getLocations();
            for (int i = 0; i < arr.length - 1; ++i) {
                System.out.println("EventLocations: " + i + " " + arr[i]);
            }*/
            //testUser.viewEventNames();

            // Colin testing section end

    }

    // Methods to test functionality of other methods, ensuring we are prepared for integration with front end
    // Prints all Open events as if on the main events page.
    public static void testEventsPage() {
        Database database = null;
            database = database.getInstance();



            ObjectId eventId = new ObjectId();
            System.out.println(eventId.toString());


            int size = database.getEventLocations().length;
            String[] locationsArr = database.getEventLocations();
            String[] descriptionsArr = database.getEventDescriptions();
            String[] statusesArr = database.getEventStatuses();

            String[] eventNamesArr = database.getEventField("eventName");

            ObjectId[] IdsArr = database.getEventIds(); 

            // This for loop will display details for every event in the system.
            for (int i = 0; i < size; ++i) {
                System.out.printf("Location: %-20s Description: %-30s Status: %-15s Name: %-15s _id: %-20s%n",
                locationsArr[i], descriptionsArr[i], statusesArr[i], eventNamesArr[i], IdsArr[i]);
            }


    }
    // Prints all Pending events as if on the admin page
    public void testAdminPage() {


    }
    // Prompts user for event details as if creating in the front end.
    // Will need to set up Task class to test approvals
    public void createEvent() {

    }
    // Prints all events that the selected user is signed up for
    // Will need to set up eventRoles class to properly test
    public void testMyEvents() {

    }
    // Prints all info for an event as if viewing details for one event on the website
    public void testOneEvent() {

    }
    // Prints all info for an event (including roles) as if viewing as an event organizer
    // Will need to set up eventRole class and Tasks class
    public void testOneEventOrganizer() {

    }





}


