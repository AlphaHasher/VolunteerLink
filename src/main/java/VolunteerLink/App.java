package VolunteerLink;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

public class App
{
    public static void main( String[] args ) {

        // Optimization checker
        long startTime = System.nanoTime();

        // System.out.println(Utility.getFieldValueFromDocument("Events", "661de2af56e40f5a3601da9d", "whenCreated", Date.class));

        // colinTesting();
        // Utility.updateFieldInDocument("Events", "661de2af56e40f5a3601da9d", "startDate", new Date());
        // Utility.updateFieldInDocument("Events", "6612159eddad0fe4253600b8", "eventStatus", "Pending");

        // Searches for all events with a status of "Pending"
        List<Document> pendingEvents = Utility.findDocumentsByFieldValue("Events", "eventStatus", "Pending");
        for (Document event : pendingEvents) {
            System.out.println(event);
        }

        // System.out.println(Utility.getFieldValueFromDocument("Events", "661de2af56e40f5a3601da9d", "startDate", Date.class));

        long endTime = System.nanoTime();
        long durationInMilliseconds = (endTime - startTime) / 1_000_000;
        System.out.println("Execution time: " + durationInMilliseconds + " milliseconds");
    }

    // Method for Colin to put his testing code to help keep main clean.
    public static void colinTesting() {
            // Testing section for Colin

            String[] locationsArr = Utility.getEventField("Events", "location", String.class);
            int size = locationsArr.length; // used for the loop later on
            String[] descriptionsArr = Utility.getEventField("Events", "eventDescription", String.class);
            Date[] startDateArr = Utility.getEventField("Events", "startDate", Date.class);
            String[] statusesArr = Utility.getEventField("Events", "eventStatus", String.class);
            String[] eventNamesArr = Utility.getEventField("Events", "eventName", String.class);

            ObjectId[] IdsArr = Utility.getEventField("Events", "_id", ObjectId.class);

            // This for loop will display details for every event in the system.
            for (int i = 0; i < size; ++i) {
                System.out.printf("Location: %-20s Description: %-15s Start Date %-15s Status: %-15s Name: %-15s _id: %-20s%n",
                locationsArr[i], descriptionsArr[i], startDateArr[i], statusesArr[i], eventNamesArr[i], IdsArr[i]);
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
