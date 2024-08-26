package control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import entity.Event;
import entity.Volunteer;
import ADT.ListInterface;
import ADT.LinkedList;

public class EventMaintenance {

    private static final String EVENT_FILE_NAME = "Event.csv";
    private ListInterface<Volunteer> assignedVolunteers = new LinkedList<>();

    public EventMaintenance() {
        // Optional: Load existing data from the CSV if needed
    }

    // Method to assign a registered volunteer to a hardcoded event and save to Event.csv
    public void assignVolunteerToEventAndSave(Volunteer volunteer, Event event) {
        // Check if the volunteer is already assigned to the event
        if (!isVolunteerAlreadyAssigned(volunteer, event)) {
            // Assign the event to the volunteer
            volunteer.setAssignedEvent(event);

            // Write the event assignment to Event.csv
            saveAssignedVolunteerToEvent(volunteer, event);

            // Add the volunteer to the list of assigned volunteers (in memory)
            assignedVolunteers.add(volunteer);

            System.out.println("Volunteer " + volunteer.getName() + " has been assigned to event " + event.geteventName() + ".");
        } else {
            System.out.println("Volunteer " + volunteer.getName() + " is already assigned to event " + event.geteventName() + ".");
        }
    }

    // Method to check if the volunteer is already assigned to the event
    public boolean isVolunteerAlreadyAssigned(Volunteer volunteer, Event event) {
        // Check if the volunteer is assigned to any event
        if (volunteer.getAssignedEvent() != null) {
            // Compare the assigned event with the provided event
            return volunteer.getAssignedEvent().geteventId().equals(event.geteventId());
        }
        return false; // Volunteer is not assigned to any event
    }

    // Method to save the assigned volunteer to Event.csv
    private void saveAssignedVolunteerToEvent(Volunteer volunteer, Event event) {
        File file = new File(EVENT_FILE_NAME);
        boolean isNewFile = !file.exists();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            // Write the header if the file is new
            if (isNewFile) {
                writer.write("EventId,EventName,VolunteerId,VolunteerName\n");
            }

            // Write the volunteer assignment
            writer.write(event.geteventId() + "," + event.geteventName() + "," + 
                         volunteer.getVolunteerId() + "," + volunteer.getName() + "\n");

        } catch (IOException e) {
            System.out.println("Error writing to Event.csv: " + e.getMessage());
            e.printStackTrace();
        }
    }

    


}
