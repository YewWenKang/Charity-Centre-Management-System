package control;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import ADT.LinkedList;
import ADT.ListInterface;
import DAO.FileDao;
import boundary.VolunteerMaintenanceUI;
import entity.Event;
import entity.Volunteer;
import utility.MessageUI;
import utility.SaveFile;

    /**
     *
     * @author User
     */
    public class VolunteerMaintenance {
        private final FileDao<Volunteer> fileDao;
        private static final String FILE_NAME = "VolunteerData.csv";
        private final ListInterface<String> headers;
        private ListInterface<Volunteer> VolunteerList = new LinkedList<>();
        private VolunteerMaintenanceUI VolunteerUI = new VolunteerMaintenanceUI();
    
        public VolunteerMaintenance() {
            headers = new LinkedList<>();
            headers.add("ID");
            headers.add("Volunteer Type");
            headers.add("Name");
            headers.add("Contact Number");
            headers.add("Email");
            headers.add("Address");
            headers.add("EventName");

 
    
            fileDao = new FileDao<>();
            VolunteerList = fileDao.loadDataFromCSV(FILE_NAME, this::mapRowToVolunteer);
            
        }
        

        // private Volunteer mapRowToVolunteer(String[] row) {
        //     if (row.length == 6) {
        //         return new Volunteer(row[0], row[1], row[2], row[3], row[4], row[5]);
        //     } else {
        //         System.out.println("Warning: Incomplete or malformed row detected, skipping: " + String.join(",", row));
        //     }
        //     return null;
        // }

        // String volunteerId = row[0];
        //     String volunteerType = row[1];
        //     String name = row[2];
        //     String address = row[3];
        //     String phoneNumber = row[4];
        //     String email = row[5];

        private Volunteer mapRowToVolunteer(String[] row) {

            if (row.length < 6) {
                System.out.println("Error: Malformed row detected. Expected at least 6 elements but got: " + row.length);
                return null; // Skip this row if it is malformed
            }
            String volunteerId = row[0];
            String volunteerType = row[1];
            String name = row[2];
            String address = row[3];
            String phoneNumber = row[4];
            String email = row[5];

            Event assignedEvent = null;
            if (row.length > 6 && !row[6].isEmpty()) {
                assignedEvent = new Event(row[6], "Hardcoded Event Name"); // Adjust as needed
            }

            Volunteer volunteer = new Volunteer(volunteerId, volunteerType, name, address, phoneNumber, email);
            volunteer.setAssignedEvent(assignedEvent);
            return volunteer;
        }
        
        private ListInterface<String> mapVolunteerToRow(Volunteer Volunteer) {
            ListInterface<String> row = new LinkedList<>();
            row.add(Volunteer.getVolunteerId());
            row.add(Volunteer.getVolunteertype());
            row.add(Volunteer.getName());
            row.add(Volunteer.getPhoneNumber());
            row.add(Volunteer.getEmail());
            row.add(Volunteer.getAddress());

            if (Volunteer.getAssignedEvent() != null) {
                row.add(Volunteer.getAssignedEvent().geteventId()); // or whatever identifier you use
            } else {
                row.add(""); // or some other default value
            }
            return row;
        }

        private boolean saveVolunteersToCSV() {
            try {
                ListInterface<Volunteer> validVolunteers = new LinkedList<>();
                for (int i = 0; i < VolunteerList.size(); i++) {
                    Volunteer Volunteer = VolunteerList.get(i);
                    if (Volunteer != null) {
                        validVolunteers.add(Volunteer);
                    } else {
                        System.out.println("Warning: Null Volunteer found at index " + i);
                    }
                }
                fileDao.writeDataToCSV(FILE_NAME, headers, validVolunteers, this::mapVolunteerToRow);
                return true;
            } catch (Exception e) {
                e.printStackTrace(); // Log the error stack trace for debugging
                System.out.println("Error saving Volunteers to CSV: " + e.getMessage());
                return false;
            }
        }

        // public void saveVolunteersToCSV() {
        //     try (FileWriter writer = new FileWriter("VolunteerData.csv", true)) {
        //         for (int i = 0; i < VolunteerList.getNumberOfEntries(); i++) {
        //             Volunteer volunteer = VolunteerList.getEntry(i);
        //             writer.append(volunteer.getVolunteerId())
        //                   .append(',')
        //                   .append(volunteer.getVolunteerType())
        //                   .append(',')
        //                   .append(volunteer.getName())
        //                   .append(',')
        //                   .append(volunteer.getAddress())
        //                   .append(',')
        //                   .append(volunteer.getPhoneNumber())
        //                   .append(',')
        //                   .append(volunteer.getEmail())
        //                   .append('\n');
        //         }
        //     } catch (IOException e) {
        //         e.printStackTrace();
        //     }
        public void writeVolunteerToCSV(Volunteer volunteer) {
            try (FileWriter writer = new FileWriter("VolunteerData.csv", true)) {
                writer.append(volunteer.getVolunteerId())
                .append(',')
                .append(volunteer.getVolunteertype())
                .append(',')
                .append(volunteer.getName())
                .append(',')
                .append(volunteer.getAddress())
                .append(',')
                .append(volunteer.getPhoneNumber())
                .append(',')
                .append(volunteer.getEmail())
                .append(',')
                .append(volunteer.getAssignedEvent() != null ? volunteer.getAssignedEvent().geteventId() : "")
                .append('\n');
        
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            if (volunteer.getVolunteertype().equalsIgnoreCase("Non-Experienced")) {
                VolunteerList.add(volunteer);
            }
        }
//     public LinkedList<Volunteer> readVolunteersFromCSV(String filePath) {
//     LinkedList<Volunteer> volunteers = new LinkedList<>();
//     try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
//         String line;
//         while ((line = br.readLine()) != null) {
//             String[] values = line.split(",");
//             Volunteer volunteer = new Volunteer(values[0], values[1], values[2],values[3],values[4],values[5]); // Adjust based on your CSV structure
//             volunteers.add(volunteer);
//         }
//     } catch (IOException e) {
//         e.printStackTrace();
//     }


// public Volunteer inputVolunteerDetails() {
//     String volunteerId = VolunteerUI.inputVolunteerId();
//     boolean isExperienced = VolunteerMaintenanceUI.inputVolunteerExperience();
//     String volunteerType = isExperienced ? "Experienced" : "Non-Experienced"; // Set volunteerType based on isExperienced
//     String name = VolunteerUI.inputVolunteerName();
//     String address = VolunteerUI.inputVolunteerAddress();
//     String phoneNumber = VolunteerUI.inputVolunteerPhoneNumber();
//     String email = VolunteerUI.inputVolunteerEmail();

//     return new Volunteer(volunteerId, volunteerType, name, address, phoneNumber, email);
// }

public Volunteer inputVolunteerDetails() {
    boolean isExperienced = VolunteerMaintenanceUI.inputVolunteerExperience(); // Assume this method returns true for "yes" and false for "no"
    String volunteerType = isExperienced ? "Experienced" : "Non-Experienced"; // Set volunteerType based on user input
    
    String volunteerId = VolunteerUI.inputVolunteerId(VolunteerList);
    String name = VolunteerUI.inputVolunteerName();
    String address = VolunteerUI.inputVolunteerAddress();
    String phoneNumber = VolunteerUI.inputVolunteerPhoneNumber(VolunteerList);
    String email = VolunteerUI.inputVolunteerEmail(VolunteerList);

    return new Volunteer(volunteerId, volunteerType, name, address, phoneNumber, email);
}

public void registerNewVolunteer() {
    Volunteer newVolunteer = inputVolunteerDetails();
    if (newVolunteer != null) {
        VolunteerList.add(newVolunteer);
        writeVolunteerToCSV(newVolunteer);
    } else {
        System.out.println("Error: Unable to register new volunteer. Please try again.");
    }
}



        // public String getAllVolunteers() {
        //     StringBuilder outputStr = new StringBuilder("Volunteer List:\n");
        //     for (int i = 1; i <= VolunteerList.getNumberOfEntries(); i++) {
        //         outputStr.append(String.format("%d. %s%n", i + 1, VolunteerList.getEntry(i)));
        //     }
        //     return outputStr.toString();
        // }

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
                saveVolunteersToCSV();
            } else {
                System.out.println("Error: Invalid Volunteer Index");
            }
        }

        

        public void removeVolunteerByName(String name) {
            boolean found = false;
            for (int i = 0; i <= VolunteerList.getNumberOfEntries(); i++) {
                Volunteer volunteer = VolunteerList.getEntry(i);
                if (volunteer != null && volunteer.getName().equalsIgnoreCase(name)) {
                    VolunteerList.remove(i);
                    found = true;
                    saveVolunteersToCSV();
                    System.out.println("Volunteer " + name + " has been removed.");
                    break;
                }
            }
            if (!found) {
                System.out.println("Error: Volunteer with name " + name + " not found.");
            }
            return;
        }

        private void removeVolunteer() {
            VolunteerUI.listAllProducts(getAllVolunteers());
            System.out.print("Do you want to remove a volunteer by (1) Index or (2) Name? ");
            int removeChoice = VolunteerUI.inputRemoveChoice();
            if (removeChoice == 1) {
                int volunteerIndex = VolunteerUI.inputVolunteerIndex();
                removeVolunteerByPosition(volunteerIndex);
            } else if (removeChoice == 2) {
                System.out.print("Enter the name of the volunteer to remove: ");
                String name = VolunteerUI.inputVolunteerName();
                removeVolunteerByName(name);
            } else {
                System.out.println("Invalid choice. Please enter 1 or 2.");
            }
        }

        public Volunteer findVolunteerById(String volunteerId) {
            try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] row = line.split(",");
                    if (row.length >= 6) {
                        String id = row[0];
                        if (id.startsWith("V") && id.equals(volunteerId)) {
                            return new Volunteer(row[0], row[1], row[2], row[3], row[4], row[5]);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null; // Volunteer not found
        }
    
        public void searchVolunteerById() {
            String volunteerId = VolunteerUI.inputVolunteerId(VolunteerList);
            Volunteer volunteer = findVolunteerById(volunteerId);
            if (volunteer != null) {
                VolunteerUI.printVolunteerDetails(volunteer);
            } else {
                System.out.println("Volunteer with ID " + volunteerId + " not found.");
            }
        }
        // public Volunteer findVolunteerById(String volunteerId) {
        //     for (int i = 0; i < VolunteerList.getNumberOfEntries(); i++) {
        //         Volunteer volunteer = VolunteerList.getEntry(i);
        //         if (volunteer != null && volunteer.getVolunteerId().equals(volunteerId)) {
        //             return volunteer;
        //         }
        //     }
        //     return null;
        // }

        // public void searchVolunteerById() {
        //     String volunteerId = VolunteerUI.inputVolunteerId();
        //     Volunteer volunteer = findVolunteerById(volunteerId);
        //     if (volunteer != null) {
        //         VolunteerUI.printVolunteerDetails(volunteer);
        //     } else {
        //         System.out.println("Volunteer with ID " + volunteerId + " not found.");
        //     }
        


        public void searchVolunteerByType() {
            String type = VolunteerUI.inputFilterChoice(); // Assume this method prompts the user for input and returns the type
            ListInterface<Volunteer> result = new LinkedList<>();
        
            for (int i = 1; i <= VolunteerList.getNumberOfEntries(); i++) {
                Volunteer volunteer = VolunteerList.getEntry(i);
                if (volunteer.getType().equalsIgnoreCase(type)) {
                    result.add(volunteer);
                }
            }
        
            if (result.getNumberOfEntries() > 0) {
                VolunteerUI.printVolunteerList(result); // Assume this method prints the details of volunteers in the list
            } else {
                System.out.println("ERROR");
            }
        }


        // private void updateVolunteer() {
        //     int position = VolunteerUI.inputUpdatedVolunteerDetails();
        //     Volunteer updatedVolunteer = inputVolunteerDetails();
        //     LinkedList<Volunteer> volunteers = (LinkedList<Volunteer>) fileDao.loadDataFromCSV("VolunteerData.csv", this::mapRowToVolunteer);
            
        //     if (position >= 0 && position < volunteers.size()) {
        //         volunteers.replace(position, updatedVolunteer);
        //         saveVolunteersToCSV();
        //         System.out.println("Volunteer at position " + position + " has been updated.");
        //     } else {
        //         System.out.println("Error: Invalid position.");
        //     }
        // }

        public void assignVolunteerToEvent(String volunteerId) {
            // Load volunteers from CSV
            LinkedList<Volunteer> volunteers = (LinkedList<Volunteer>) fileDao.loadDataFromCSV("VolunteerData.csv", this::mapRowToVolunteer);
        
            // Find the volunteer by ID
            for (int i = 1; i <= volunteers.getNumberOfEntries(); i++) {
                Volunteer volunteer = volunteers.getEntry(i);
                if (volunteer.getVolunteerId().equals(volunteerId)) {
                    // Hardcode the event details
                    String eventId = "E001"; // Hardcoded event ID
                    String eventName = "Community Clean-Up"; // Hardcoded event name
                    
                    // Create and assign the event to the volunteer
                    Event event = new Event(eventId, eventName);
                    volunteer.setAssignedEvent(event);
        
                    // Save the updated volunteer information
                    writeVolunteerToCSV(volunteer);
        
                    System.out.println("Volunteer " + volunteerId + " has been assigned to event " + eventName);
                    return;
                }
            }
        
            System.out.println("Volunteer with ID " + volunteerId + " not found.");
        }
        

        public void searchVolunteersByEvent() {
            // Input event details
            String eventId = VolunteerUI.inputEventId(); // Assume this prompts user to input the event ID
        
            // List to store volunteers found under the specified event
            ListInterface<Volunteer> foundVolunteers = new LinkedList<>();
        
            // Search volunteers by event
            for (int i = 1; i <= VolunteerList.getNumberOfEntries(); i++) {
                Volunteer volunteer = VolunteerList.getEntry(i);
                Event assignedEvent = volunteer.getAssignedEvent();
                
                if (assignedEvent != null && assignedEvent.geteventId().equals(eventId)) {
                    foundVolunteers.add(volunteer);
                }
            }
        
            // Check if any volunteers were found and display results
            if (foundVolunteers.getNumberOfEntries() > 0) {
                VolunteerUI.printVolunteerList(foundVolunteers); // Assume this method prints the list of volunteers
            } else {
                System.out.println("No volunteers found for event ID: " + eventId);
            }
        }


        public void runVolunteerMaintenance() {
            int choice;
            VolunteerMaintenance volunteerMaintenance = new VolunteerMaintenance(); // Declare and initialize the volunteerMaintenance variable
            String volunteerId = ""; // Declare and initialize the volunteerId variable
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
                        volunteerMaintenance.removeVolunteer();
                        break;
                    case 3:
                        volunteerMaintenance.searchVolunteerById();
                        break;
                    case 4:
                    volunteerMaintenance.searchVolunteerByType();
                        break;
                    case 5:
                        VolunteerUI.listAllProducts(getAllVolunteers());
                        break;
                    case 6:
                    volunteerId = VolunteerUI.inputVolunteerId(); // Prompt user for volunteerId
                    volunteerMaintenance.assignVolunteerToEvent(volunteerId);
                    break;
                    case 7:
                    volunteerMaintenance.searchVolunteersByEvent();
                    break;
                    default:
                        MessageUI.displayInvalidChoiceMessage();
                }
            } while (choice != 0);
        }

        public static void main(String[] args) {
            VolunteerMaintenance volunteerMaintenance = new VolunteerMaintenance();
            volunteerMaintenance.runVolunteerMaintenance();
        }
    }
