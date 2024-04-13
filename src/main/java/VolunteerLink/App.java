package VolunteerLink;

// Java Imports
// import java.io.IOException;
// import java.io.InputStream;
// import java.util.Properties;


public class App
{
    public static void main( String[] args ) {

        // Optimization checker
        // long startTime = System.nanoTime();
        // long endTime = System.nanoTime();
        // long durationInMilliseconds = (endTime - startTime) / 1_000_000;
        // System.out.println("Execution time: " + durationInMilliseconds + " milliseconds");


            // Testing section for Colin
            // Event testEvent = new Event("eventName", "description", "startDate", "endDate", "location", 2, 1, "Pending");
            //Document testDocument = testEvent.toDocument();
            //Events.insertOne(testDocument);


            Event testEvent = new Event();
            // String[] arr = testEvent.getLocations();
            // for (int i = 0; i < arr.length - 1; ++i) {
            //     System.out.println("EventLocations: " + i + " " + arr[i]);
            // }

            // testEvent.setEventName("6612159eddad0fe4253600b8", "Basketball Game");

            System.out.println(testEvent.getEventName("6612159eddad0fe4253600b8"));
            // Colin testing section end

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
    }
}
