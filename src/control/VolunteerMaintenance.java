/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import ADT.LinkedList;
import ADT.ListInterface; // Add this import statement
import boundary.VolunteerMaintenanceUI;
// import entity.Event;
import entity.Volunteer;
import utility.MessageUI;
import java.io.FileWriter;
import java.io.IOException; 

/**
 *
 * @author User
 */
public class VolunteerMaintenance {

    private static final String FILE_NAME = "VolunteerData.csv";

    private ListInterface<Volunteer> VolunteerList = new LinkedList<>();

    private VolunteerMaintenanceUI VolunteerUI = new VolunteerMaintenanceUI();

    public Volunteer inputVolunteerDetails() {

        String volunteerId = VolunteerUI.inputVolunteerId();
        String VolunteerName = VolunteerUI.inputVolunteerName();
        String VolunteerAddress = VolunteerUI.inputVolunteerAddress();
        String VolunteerPhoneNumber = VolunteerUI.inputVolunteerPhoneNumber();
        String VolunteerEmail = VolunteerUI.inputVolunteerEmail();


        return new Volunteer(volunteerId ,VolunteerName, VolunteerAddress, VolunteerPhoneNumber, VolunteerEmail);
    }

    public void saveVolunteerDataToFile() {
        try {
            FileWriter writer = new FileWriter(FILE_NAME);
            for (int i = 1; i <= VolunteerList.getNumberOfEntries(); i++) {
                Volunteer volunteer = VolunteerList.getEntry(i);
                String line = volunteer.getVolunteerId() + "," +
                              volunteer.getName() + "," +
                              volunteer.getAddress() + "," +
                              volunteer.getPhoneNumber() + "," +
                              volunteer.getEmail();
                writer.write(line);
                writer.write("\n");
            }
            writer.close();
            System.out.println("Volunteer data saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving volunteer data to file.");
        }
    }
    private Volunteer mapRowToDonor(String[] row) {
        if (row.length == 8) {
            return new Volunteer(row[0], row[1], row[2], row[3], row[4]);
        }
        return null;
    }

    public void registerNewVolunteer() {
        Volunteer newVolunteer = inputVolunteerDetails();
        VolunteerList.add(newVolunteer);
    }

    public String getAllVolunteers() {
        String outputStr = "";
        for (int i = 1; i <= VolunteerList.getNumberOfEntries(); i++) {
            outputStr += i + ". " + VolunteerList.getEntry(i) + "\n";
        }
        return outputStr;
    }


    public void removeVolunteerByPosition(int position) {
        if (position >= 1 && position <= VolunteerList.getNumberOfEntries()) {
            VolunteerList.remove(position);
            // VolunteerUI.displayRemoveSuccessMessage();
        } else {
            System.out.println("Error");
            // VolunteerUI.displayInvalidVolunteerIndexMessage();
        }
    }

    public void searchVolunteerByIndex(int index) {
        Volunteer searchVolunteer = VolunteerList.getEntry(index);
        if (searchVolunteer != null) {
            VolunteerUI.printVolunteerDetails(searchVolunteer);
        } else {
            System.out.println("Volunteer not found");
        }
    }

    public void runVolunteerMaintenance() {
        int choice = 0;
        int volunteerIndex = 0; // Declare and initialize the volunteerIndex variable
        do {
            choice = VolunteerUI.getMenuChoice();
            switch (choice) {
                case 0:
                    MessageUI.displayExitMessage();
                    break;
                case 1:
                    registerNewVolunteer();
                    VolunteerUI.printVolunteerDetails(VolunteerList.getEntry(VolunteerList.getNumberOfEntries()));
                    break;
                case 2:
                  VolunteerUI.listAllProducts(getAllVolunteers());
                    volunteerIndex = VolunteerUI.inputVolunteerIndex(); // Assign a value to volunteerIndex
                    removeVolunteerByPosition(volunteerIndex);
                    // VolunteerUI.listAllProducts(getAllVolunteers());
                    break;
                case 3:
                    //VolunteerUI = VolunteerUI.inputVolunteerIndex();
                    searchVolunteerByIndex(choice);
                    break;
                case 4:
                    VolunteerUI.listAllProducts(getAllVolunteers());
                    break;
                
                default:
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice != 0);
    }

    public static void main(String[] args) {
        VolunteerMaintenance VolunteerMaintenance = new VolunteerMaintenance();
        VolunteerMaintenance.runVolunteerMaintenance();
        // System.out.println(VolunteerMaintenance.getAllVolunteers());
    }

}
