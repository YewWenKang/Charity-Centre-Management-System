package control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Function;
import java.awt.Desktop;
import java.util.logging.Level;
import java.util.logging.Logger;
import ADT.LinkedList;
import ADT.ListInterface;
import DAO.FileDao;
import boundary.VolunteerMaintenanceUI;
import entity.Event;
import entity.Volunteer;
import utility.MessageUI;

/**
 *
 * @author TAN HAN SHEN
 */
public class VolunteerMaintenance {
    private final FileDao<Volunteer> fileDao;
    private static final String FILE_NAME = "VolunteerData.csv";
    private final ListInterface<String> headers;
    private ListInterface<Volunteer> VolunteerList = new LinkedList<>();
    private VolunteerMaintenanceUI VolunteerUI = new VolunteerMaintenanceUI();
    private int nextId;

    public VolunteerMaintenance() {
        headers = new LinkedList<>();
        headers.add("ID");
        headers.add("Volunteer Type");
        headers.add("Name");
        headers.add("Phone number");
        headers.add("Email");
        headers.add("Address");

        fileDao = new FileDao<>();
        VolunteerList = fileDao.loadDataFromCSV(FILE_NAME, this::mapRowToVolunteer);
        this.nextId = calculateNextId(); // Initialize nextId based on existing IDs

    }

    private int calculateNextId() {
        int maxId = 0;
        for (int i = 1; i <= VolunteerList.getNumberOfEntries(); i++) {
            Volunteer donee = VolunteerList.getEntry(i);
            int currentId = Integer.parseInt(donee.getVolunteerId().substring(2)); // Extract numeric part
            if (currentId > maxId) {
                maxId = currentId;
            }
        }
        return maxId + 1; // Next available ID
    }

    // file code
    private Volunteer mapRowToVolunteer(String[] row) {

        if (row.length < 6) {
            System.out.println("Error: Malformed row detected. Expected at least 6 elements but got: " + row.length);
            return null; // Skip this row if it is malformed
        }
        String volunteerId = row[0];
        String volunteerType = row[1];
        String name = row[2];
        String phoneNumber = row[3];
        String email = row[4];
        String address = row[5];

        Volunteer volunteer = new Volunteer(volunteerId, volunteerType, name, phoneNumber, email, address);
        return volunteer;
    }

    public <T> void writeDataToCSV(String fileName, ListInterface<String> headers, ListInterface<T> data,
            Function<T, ListInterface<String>> mapper) throws IOException {
        File file = new File(fileName);
        boolean append = file.exists(); // Append if file already exists

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, append))) {
            if (!append) {
                // Write headers if creating a new file
                writer.write(String.join(",", headers));
                writer.newLine();
            }

            // Write data rows
            for (T item : data) {
                ListInterface<String> row = mapper.apply(item);
                writer.write(String.join(",", row));
                writer.newLine();
            }
        }
    }

    public boolean saveVolunteersToCSV() {
        try {
            ListInterface<Volunteer> validVolunteers = new LinkedList<>();
            for (int i = 0; i < VolunteerList.size(); i++) {
                Volunteer volunteer = VolunteerList.get(i);
                if (volunteer != null) {
                    validVolunteers.add(volunteer);
                } else {
                    System.out.println("Warning: Null donor found at index " + i);
                }
            }
            fileDao.writeDataToCSV(FILE_NAME, headers, validVolunteers, this::mapVolunteerToRow);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error saving volunteer to CSV: " + e.getMessage());
            return false;
        }
    }

    private ListInterface<String> mapVolunteerToRow(Volunteer Volunteer) {
        ListInterface<String> row = new LinkedList<>();
        row.add(Volunteer.getVolunteerId());
        row.add(Volunteer.getVolunteertype());
        row.add(Volunteer.getName());
        row.add(Volunteer.getPhoneNumber());
        row.add(Volunteer.getEmail());
        row.add(Volunteer.getAddress());
        return row;
    }

    public void writeVolunteerToCSV(Volunteer volunteer) {
        File file = new File("VolunteerData.csv");
        boolean isNewFile = !file.exists(); // Check if the file does not exist (new file)

        try (FileWriter writer = new FileWriter(file, true)) {
            // If it's a new file or an empty file, write the header first
            if (isNewFile || file.length() == 0) {
                writer.append("VolunteerId,VolunteerType,Name,phoneNumber,Email,Address\n");
            }

            // Now write the volunteer data
            writer.append(volunteer.getVolunteerId())
                    .append(',')
                    .append(volunteer.getVolunteertype())
                    .append(',')
                    .append(volunteer.getName())
                    .append(',')
                    .append(volunteer.getPhoneNumber())
                    .append(',')
                    .append(volunteer.getEmail())
                    .append(',')
                    .append(volunteer.getAddress())
                    .append('\n');

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Case 1 : registerNewVolunteer
    public Volunteer inputVolunteerDetails() {
        boolean isExperienced = VolunteerMaintenanceUI.inputVolunteerExperience();
        String volunteerType = isExperienced ? "Experienced" : "Non-Experienced";

        String volunteerId = generateVolunteerId();
        String name = VolunteerUI.inputVolunteerName();
        String phoneNumber = VolunteerUI.inputVolunteerPhoneNumber(VolunteerList);
        String email = VolunteerUI.inputVolunteerEmail(VolunteerList);
        String address = VolunteerUI.inputVolunteerAddress();

        return new Volunteer(volunteerId, volunteerType, name, phoneNumber, email, address);
    }

    private String generateVolunteerId() {
        String prefix = "V";
        String numericPart = String.format("%03d", nextId);
        nextId++; // Increment for the next volunteer
        return prefix + numericPart;
    }

    public void registerNewVolunteer() {
        Volunteer newVolunteer = inputVolunteerDetails();
        if (newVolunteer != null) {
            VolunteerList.add(newVolunteer);
            writeVolunteerToCSV(newVolunteer);
            System.out.println("Volunteer registered successfully!");
        } else {
            System.out.println("Error: Unable to register new volunteer. Please try again.");
        }
    }

    // Case 2 : deleteVolunteerById
    public void deleteVolunteerById() {
        String volunteerId = VolunteerUI.inputVolunteerId(); // Input the volunteer ID

        // Look for the volunteer in the in-memory list
        Volunteer volunteerToRemove = null;
        for (int i = 0; i < VolunteerList.size(); i++) {
            Volunteer volunteer = VolunteerList.get(i);
            if (volunteer.getVolunteerId().equals(volunteerId)) {
                volunteerToRemove = volunteer;
                break;
            }
        }

        // If the volunteer is found, remove it
        if (volunteerToRemove != null) {
            VolunteerList.remove(volunteerToRemove); // Remove from in-memory list

            // Now update the file by re-saving the list to the CSV
            if (saveVolunteersToCSV()) {
                System.out.println("Volunteer " + volunteerId + " has been deleted successfully.");
            } else {
                System.out.println("Error: Could not save changes to file.");
            }
        } else {
            System.out.println("Volunteer with ID " + volunteerId + " not found.");
        }
    }

    // This method is assumed to print the list of volunteers to the console
    private void printVolunteerList() {
        for (int i = 0; i < VolunteerList.getNumberOfEntries(); i++) {
            Volunteer volunteer = VolunteerList.getEntry(i);
            if (volunteer != null) {
                System.out.println(volunteer);
            }
        }
    }

    // case 3 : searchVolunteerById
    public void searchVolunteerById() {
        String volunteerId = VolunteerUI.inputVolunteerId();
        Volunteer volunteer = findVolunteerByIdInFile(volunteerId);
        if (volunteer != null) {
            VolunteerUI.printVolunteerDetails(volunteer);
        } else {
            System.out.println("Volunteer with ID " + volunteerId + " not found.");
        }
    }

    private Volunteer findVolunteerByIdInFile(String volunteerId) {
        String fileName = "VolunteerData.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean skipHeader = true; // To skip the header line

            while ((line = br.readLine()) != null) {
                if (skipHeader) {
                    skipHeader = false;
                    continue; // Skip the header line
                }

                String[] fields = line.split(",");
                if (fields.length >= 6) {
                    String csvVolunteerId = fields[0];
                    if (csvVolunteerId.equals(volunteerId)) {
                        String volunteerType = fields[1];
                        String name = fields[2];
                        String phoneNumber = fields[3];
                        String email = fields[4];
                        String address = fields[5];
                        return new Volunteer(volunteerId, volunteerType, name, phoneNumber, email, address);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the VolunteerData.csv file: " + e.getMessage());
            e.printStackTrace();
        }
        return null; // Volunteer ID not found
    }

    // case 4 : filterVolunteersByExperience
    public void filterVolunteersByExperience() {
        String filterType = VolunteerMaintenanceUI.inputFilterChoice();
        boolean found = false;

        String fileName = "VolunteerData.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean skipHeader = true; // To skip the header line

            while ((line = br.readLine()) != null) {
                if (skipHeader) {
                    skipHeader = false;
                    continue; // Skip the header line
                }

                String[] fields = line.split(",");
                if (fields.length >= 6) {
                    String volunteerType = fields[1];
                    if (volunteerType.equalsIgnoreCase(filterType)) {
                        String volunteerId = fields[0];
                        String name = fields[2];
                        String phoneNumber = fields[3];
                        String email = fields[4];
                        String address = fields[5];
                        Volunteer volunteer = new Volunteer(volunteerId, volunteerType, name, phoneNumber, email,
                                address);
                        System.out.println(volunteer.toString());
                        found = true;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the VolunteerData.csv file: " + e.getMessage());
            e.printStackTrace();
        }

        // If no volunteers found for the given type
        if (!found) {
            System.out.println("No volunteers found for type: " + filterType);
        }
    }

    // case 5 : listAllVolunteers
    public String getAllVolunteers() {
        String outputStr = "";
        for (int i = 1; i <= VolunteerList.getNumberOfEntries(); i++) {
            outputStr += VolunteerList.getEntry(i) + "\n";
        }
        return outputStr;
    }

    // case 6 : assignVolunteerEvent
    public void assignVolunteerEvent(String volunteerId) {
        EventMaintenance eventMaintenance = new EventMaintenance();

        // Hardcode the event details
        Event event = new Event("E001", "Community Clean-Up");

        Volunteer volunteer = findVolunteerByIdInFile(volunteerId);

        if (volunteer != null) {
            if (eventMaintenance.isVolunteerAlreadyAssigned(volunteer, event)) {
                System.out.println("Volunteer " + volunteer.getName() + " has already been assigned to the event '"
                        + event.geteventName() + "'.");
                return;
            }
            eventMaintenance.assignVolunteerToEventAndSave(volunteer, event);
        } else {
            System.out.println("Volunteer with ID " + volunteerId + " not found.");
        }
    }

    private String getEventIdByName(String eventName) {
        String EVENT_FILE_NAME = "Event.csv"; // Declare and initialize the EVENT_FILE_NAME variable with the
                                              // appropriate value
        try (BufferedReader br = new BufferedReader(new FileReader(EVENT_FILE_NAME))) {
            String line;
            boolean skipHeader = true; // To skip the header line

            while ((line = br.readLine()) != null) {
                if (skipHeader) {
                    skipHeader = false;
                    continue; // Skip the header line
                }

                String[] fields = line.split(",");
                if (fields.length >= 2) {
                    String csvEventName = fields[1];

                    if (csvEventName.equalsIgnoreCase(eventName)) {
                        return fields[0]; // Return the event ID
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the Event.csv file: " + e.getMessage());
            e.printStackTrace();
        }

        return null; // Event name not found
    }

    public void printVolunteersByEventName(String eventName) {
        LinkedList<Volunteer> volunteers = (LinkedList<Volunteer>) getVolunteersByEventName(eventName);
        if (volunteers.isEmpty()) {
            System.out.println("No volunteers found for the event: " + eventName);
        } else {
            System.out.println("Volunteers for the event: " + eventName);
            for (Volunteer volunteer : volunteers) {
                System.out.println("Volunteer ID: " + volunteer.getVolunteerId() + ", Name: " + volunteer.getName());
            }
        }
    }

    // public void printVolunteersByEventName() {
    // String eventName = VolunteerUI.inputEventName(); // Assume this method
    // prompts the user for input and returns the event name
    // printVolunteersByEventName(eventName);
    // }

    // case 7 : getVolunteersByEventName
    public ListInterface<Volunteer> getVolunteersByEventName(String eventName) {
        LinkedList<Volunteer> volunteers = new LinkedList();
        String eventId = getEventIdByName(eventName);

        if (eventId == null) {
            System.out.println("Event not found.");
            return volunteers;
        }

        try (BufferedReader br = new BufferedReader(new FileReader("Event.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length >= 4) {
                    String eventFileId = fields[0];
                    if (eventFileId.equals(eventId)) {
                        String volunteerId = fields[2];
                        Volunteer volunteer = findVolunteerByIdInFile(volunteerId);
                        if (volunteer != null) {
                            volunteers.add(volunteer);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return volunteers;
    }

    // case 8 : generateSummaryReport
    public void generateSummaryvolunteerReport() {
        int totalVolunteers = VolunteerList.getNumberOfEntries();
        int experiencedCount = 0;
        int nonExperiencedCount = 0;

        for (int i = 1; i <= VolunteerList.getNumberOfEntries(); i++) {
            Volunteer volunteer = VolunteerList.getEntry(i);

            if (volunteer.isExperienced()) {
                experiencedCount++;
            } else {
                nonExperiencedCount++;
            }
        }

        // Print the summary report
        System.out.println();
        System.out.println("=====================================================");
        System.out.println("                 Volunteer Summary Report            ");
        System.out.println("=====================================================");
        System.out.println("| Volunteer Type                   | Total Count     |");
        System.out.println("|----------------------------------|-----------------|");
        System.out.printf("| %-32s | %-15d |\n", "Experienced Volunteers", experiencedCount);
        System.out.printf("| %-32s | %-15d |\n", "Non-Experienced Volunteers", nonExperiencedCount);
        System.out.println("|----------------------------------|-----------------|");
        System.out.printf("| %-32s | %-15d |\n", "Total Volunteers Registered", totalVolunteers);
        System.out.println("=====================================================");
        System.out.println();

    }

    public void generateSummaryReport() {
        try {
            Desktop.getDesktop().open(new File("VolunteerData.csv"));
            System.out.println(" ");

        } catch (IOException ex) {
            Logger.getLogger(VolunteerMaintenance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void runVolunteerMaintenance() {
        int choice;
        VolunteerMaintenance volunteerMaintenance = new VolunteerMaintenance(); // Declare and initialize the

        String volunteerId = ""; // Declare and initialize the volunteerId variable
        do {
            choice = VolunteerUI.getMenuChoice();
            switch (choice) {
                case 0:
                    MessageUI.displayExitMessage();
                    break;
                case 1:
                    registerNewVolunteer();
                    // VolunteerUI.printVolunteerDetails(VolunteerList.getEntry(VolunteerList.getNumberOfEntries()));
                    break;
                case 2:
                    // volunteerId = VolunteerUI.inputVolunteerId(); // Prompt user for volunteerId
                    deleteVolunteerById();
                    break;
                case 3:
                    searchVolunteerById();
                    break;
                case 4:
                    volunteerMaintenance.filterVolunteersByExperience();
                    break;
                case 5:
                    VolunteerUI.listAllProducts(getAllVolunteers());
                    break;
                case 6:
                    volunteerId = VolunteerUI.inputVolunteerId(); // Prompt user for volunteerId
                    assignVolunteerEvent(volunteerId);
                    break;
                case 7:
                    String eventName = VolunteerUI.inputEventName(); // Assume this method prompts the user for input
                    printVolunteersByEventName(eventName);
                    break;

                case 8:
                    String summaryChoiceString = VolunteerMaintenanceUI.getSummaryChoice();
                    int summaryChoice = Integer.parseInt(summaryChoiceString);
                    if (summaryChoice == 1) {
                        generateSummaryvolunteerReport();
                    } else if (summaryChoice == 2) {
                        generateSummaryReport();
                    } else {
                        MessageUI.displayInvalidChoiceMessage();
                    }
                    break; // Add break statement to exit the switch case
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
