package control;

import ADT.LinkedStack;
import ADT.LinkedList;
import ADT.ListInterface;
import boundary.DoneeMaintenanceUI;
import entity.Donee;
import utility.MessageUI;
import DAO.DoneeDAO;
import utility.ValidationUI;
import ADT.StackInterface;
import entity.Distribution;
import entity.Donation;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class DoneeMaintenance {

    private ListInterface<Donee> doneeList = new LinkedList<>();
    private DoneeMaintenanceUI doneeUI = new DoneeMaintenanceUI();
    private static final String FILE_NAME = "doneeData.csv";
    private final String headers = "ID,Name,Address,Phone Number,Email,Donee Type,Organization Name";
    private int nextId;
    private StackInterface<Command> undoStack = new LinkedStack<>();
    private StackInterface<Command> redoStack = new LinkedStack<>();

    DonationManager manager = new DonationManager();

    public DoneeMaintenance() throws ParseException {
        loadDoneeData();
        this.nextId = calculateNextId(); // Initialize nextId based on existing IDs
    }

    // Method to load donee data from file
    private void loadDoneeData() {
        DoneeDAO.loadFromFile(FILE_NAME, doneeList, line -> {
            // Split on commas not inside quotes
            String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            // Remove extra quotes around fields if necessary
            for (int i = 0; i < parts.length; i++) {
                parts[i] = parts[i].replaceAll("^\"|\"$", "").trim();
            }

            String id = parts[0];
            String name = parts[1];
            String address = parts[2];
            String phoneNumber = parts[3];
            String email = parts[4];
            String doneeType = parts[5];
            String organizationName = parts.length > 6 ? parts[6] : "";

            return new Donee(id, name, address, phoneNumber, email, doneeType, organizationName);
        });
    }

    public void saveDoneeData() {
        DoneeDAO.saveToFile(FILE_NAME, doneeList, headers);
    }

    public Donee inputDoneeDetails() {
        doneeUI.printBox("Donee Registration");
        String doneeId = generateDoneeId();
        String doneeName;
        String doneeAddress;
        String doneePhoneNumber;
        String doneeEmail;
        String doneeType;
        String doneeOrganizationName = "";

        // Input and validation for Donee Name
        do {
            doneeName = doneeUI.inputDoneeName();
            if (ValidationUI.isNotEmpty(doneeName)) {
                break;
            } else {
                System.out.println("Name cannot be empty. Please try again.");
            }
        } while (true);

        // Input and validation for Donee Address
        do {
            doneeAddress = doneeUI.inputDoneeAddress();
            if (ValidationUI.isNotEmpty(doneeAddress)) {
                break;
            } else {
                System.out.println("Address cannot be empty. Please try again.");
            }
        } while (true);

        // Input and validation for Donee Phone Number
        do {
            doneePhoneNumber = doneeUI.inputDoneePhoneNumber();
            if (ValidationUI.isValidPhoneNumber(doneePhoneNumber)) {
                break;
            } else if (!ValidationUI.isNotEmpty(doneePhoneNumber)) {
                System.out.println("Phone Number cannot be empty. Please try again.");
            } else {
                System.out.println("Invalid phone number. Please enter a valid phone number starting with '01' and 10 or 11 digits long.");
            }
        } while (true);

        // Input and validation for Donee Email
        do {
            doneeEmail = doneeUI.inputDoneeEmail();
            if (ValidationUI.isValidEmail(doneeEmail)) {
                break;
            } else {
                System.out.println("Invalid email format. Please enter a valid email address in the format xxx@xxxx.com.");
            }
        } while (true);

        // Input and validation for Donee Type
        do {
            doneeType = doneeUI.inputDoneeType();
            if (doneeType.equalsIgnoreCase("Y") || doneeType.equalsIgnoreCase("N")) {
                break;
            } else {
                System.out.println("Invalid input(Y/N only).");
            }
        } while (true);

        //OrganizationName validate
        if (doneeType.equalsIgnoreCase("Y")) {
            doneeType = "Organization";
            // Input and validation for Donee Organization Name
            do {
                doneeOrganizationName = doneeUI.inputDoneeOrganizationName();
                if (ValidationUI.isNotEmpty(doneeOrganizationName)) {
                    break;
                } else {
                    System.out.println("Organization name cannot be empty. Please try again.");
                }
            } while (true);
        } else {
            doneeType = "Individual";
        }

        return new Donee(doneeId, doneeName, doneeAddress, doneePhoneNumber, doneeEmail, doneeType, doneeOrganizationName);
    }

    private String generateDoneeId() {
        String prefix = "DE";
        String numericPart = String.format("%03d", nextId);
        nextId++; // Increment for the next donee
        return prefix + numericPart;
    }

    //Choice 1
    public void registerNewDonee() {
        Donee newDonee = inputDoneeDetails();

        Command addCommand = new AddDoneeCommand(doneeList, newDonee);
        addCommand.execute();
        undoStack.push(addCommand);
        redoStack.clear(); // Clear redo stack after new action
    }

    //Choice 2
    public void removeDonee() {
        String nameToRemove = doneeUI.inputRemovedDoneeName();

        boolean removed = false;
        for (int i = 1; i <= doneeList.getNumberOfEntries(); i++) {
            Donee donee = doneeList.getEntry(i);
            if (donee.getName().equalsIgnoreCase(nameToRemove)) {
                //undo
                Command removeCommand = new RemoveDoneeCommand(doneeList, donee);
                removeCommand.execute();
                undoStack.push(removeCommand);
                redoStack.clear(); // Clear redo stack after new action

                removed = true;
                System.out.println("Donee " + nameToRemove + " has been removed.");

                break;
            }
        }

        if (!removed) {
            System.out.println("Donee " + nameToRemove + " not found.");
        }
    }

    //Choice 3
    public void updateDoneeDetails() {
        // Display all donees
        System.out.println("List of Donees:");
        for (int i = 1; i <= doneeList.getNumberOfEntries(); i++) {
            Donee donee = doneeList.getEntry(i);
            System.out.println(donee.getId() + ": " + donee.getName());
        }

        String idToUpdate = doneeUI.inputDoneeIdUpdate();

        // Search for the donee with the specified ID
        for (int i = 1; i <= doneeList.getNumberOfEntries(); i++) {
            Donee donee = doneeList.getEntry(i);
            if (donee.getId().equalsIgnoreCase(idToUpdate)) {
                // Capture the original state of the donee
                Donee originalDonee = new Donee(donee.getId(), donee.getName(), donee.getAddress(), donee.getPhoneNumber(), donee.getEmail(), donee.getDoneeType(), donee.getOrganizationName());

                boolean keepUpdating = true;
                while (keepUpdating) {
                    int choice = doneeUI.getUpdateDoneeChoice();

                    switch (choice) {
                        case 1:
                            String updatedName = doneeUI.inputDoneeName();
                            donee.setName(updatedName);
                            System.out.println("Name updated successfully.");
                            break;
                        case 2:
                            String updatedAddress = doneeUI.inputDoneeAddress();
                            donee.setAddress(updatedAddress);
                            System.out.println("Address updated successfully.");
                            break;
                        case 3:
                            String updatedPhoneNumber = doneeUI.inputDoneePhoneNumber();
                            donee.setPhoneNumber(updatedPhoneNumber);
                            System.out.println("Phone number updated successfully.");
                            break;
                        case 4:
                            String updatedEmail = doneeUI.inputDoneeEmail();
                            donee.setEmail(updatedEmail);
                            System.out.println("Email updated successfully.");
                            break;
                        case 5:
                            String updatedDoneeType = doneeUI.inputDoneeType();
                            if (updatedDoneeType.equalsIgnoreCase("Y")) {
                                donee.setDoneeType("Organization");
                                System.out.println("Donee type updated to Organization.");
                            } else {
                                donee.setDoneeType("Individual");
                                donee.setOrganizationName(""); // Clear organization name if changing to Individual
                                System.out.println("Donee type updated to Individual.");
                            }
                            break;
                        case 6:
                            if (donee.getDoneeType().equalsIgnoreCase("Organization")) {
                                String updatedOrganizationName = doneeUI.inputDoneeOrganizationName();
                                donee.setOrganizationName(updatedOrganizationName);
                                System.out.println("Organization name updated successfully.");
                            } else {
                                System.out.println("This donee is not an organization. Cannot update Organization Name.");
                            }
                            break;
                        case 0:
                            keepUpdating = false;
                            System.out.println("Finished updating donee.");
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }
                }
                doneeList.replace(i, donee);
                Command updateCommand = new UpdateDoneeCommand(doneeList, originalDonee, donee);
                undoStack.push(updateCommand);
                redoStack.clear(); // Clear redo stack after new action
                return;
            }
        }

        System.out.println("Donee with ID " + idToUpdate + " not found.");
    }

    //choice 4 search Donee by id
    public void searchDoneeDetails() {
        String idToSearch = doneeUI.inputDoneeIdToSearch();
        Donee target = new Donee(idToSearch, "", "", "", "", "", ""); // Create Donee with the given ID

        // Perform the linear search
        Donee result = doneeList.linearSearch(target);

        // Print the result
        if (result != null) {
            System.out.println("Found donee: " + result);

            // Ask user if they want to update the donee
            String updateChoice = doneeUI.askUpdateDonee();

            if (updateChoice.equalsIgnoreCase("y")) {
                boolean keepUpdating = true;
                while (keepUpdating) {
                    int choice = doneeUI.getUpdateDoneeChoice();

                    switch (choice) {
                        case 1:
                            String updatedName = doneeUI.inputDoneeName();
                            result.setName(updatedName);
                            System.out.println("Name updated successfully.");
                            break;
                        case 2:
                            String updatedAddress = doneeUI.inputDoneeAddress();
                            result.setAddress(updatedAddress);
                            System.out.println("Address updated successfully.");
                            break;
                        case 3:
                            String updatedPhoneNumber = doneeUI.inputDoneePhoneNumber();
                            result.setPhoneNumber(updatedPhoneNumber);
                            System.out.println("Phone number updated successfully.");
                            break;
                        case 4:
                            String updatedEmail = doneeUI.inputDoneeEmail();
                            result.setEmail(updatedEmail);
                            System.out.println("Email updated successfully.");
                            break;
                        case 5:
                            String updatedDoneeType = doneeUI.inputDoneeType();
                            if (updatedDoneeType.equalsIgnoreCase("Y")) {
                                result.setDoneeType("Organization");
                                System.out.println("Donee type updated to Organization.");
                            } else {
                                result.setDoneeType("Individual");
                                result.setOrganizationName(""); // Clear organization name if changing to Individual
                                System.out.println("Donee type updated to Individual.");
                            }
                            break;
                        case 6:
                            if (result.getDoneeType().equalsIgnoreCase("Organization")) {
                                String updatedOrganizationName = doneeUI.inputDoneeOrganizationName();
                                result.setOrganizationName(updatedOrganizationName);
                                System.out.println("Organization name updated successfully.");
                            } else {
                                System.out.println("This donee is not an organization. Cannot update Organization Name.");
                            }
                            break;
                        case 0:
                            keepUpdating = false;
                            System.out.println("Finished updating donee.");
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }
                }
                // Replace the donee in the list
                for (int i = 1; i <= doneeList.getNumberOfEntries(); i++) {
                    if (doneeList.getEntry(i).getId().equalsIgnoreCase(idToSearch)) {
                        doneeList.replace(i, result);
                        break;
                    }
                }
            }
        } else {
            System.out.println("Donee with ID " + target.getId() + " not found.");
        }
    }

    //choice 5
//    public void listDoneesWithDonations() {
//        for (int i = 1; i <= doneeList.getNumberOfEntries(); i++) {
//            Donee donee = doneeList.getEntry(i);
//            System.out.println("Donee: " + donee.getName());
//            // Assuming a method getDonations() that returns a list of donations for the donee
//            donee.getDonations().forEach(donation -> System.out.println("\tDonation: " + donation));
//        }
//    }
    //choice 6
    public void filterDonees() {
        String type = doneeUI.inputDoneeTypeFilter();
        String location = doneeUI.inputDoneeAddressFilter();

        // If both filters are empty, print all donees without filtering
        if (type.isEmpty() && location.isEmpty()) {
            System.out.println("No filters applied. Displaying all donees:");
            for (int i = 1; i <= doneeList.getNumberOfEntries(); i++) {
                Donee donee = doneeList.getEntry(i);
                doneeUI.printDoneeDetails(donee);
            }
            return; // Exit the method early
        }

        // Filter donees based on the provided criteria
        for (int i = 1; i <= doneeList.getNumberOfEntries(); i++) {
            Donee donee = doneeList.getEntry(i);
            boolean matchesType = type.isEmpty() || donee.getDoneeType().equalsIgnoreCase(type);
            boolean matchesLocation = location.isEmpty() || donee.getAddress().toLowerCase().contains(location.toLowerCase());

            if (matchesType && matchesLocation) {
                doneeUI.printDoneeDetails(donee);
            }
        }
    }

    //choice 7
    public void generateSummaryReport() {
        int totalDonees = doneeList.getNumberOfEntries();
        int organizationCount = 0;
        int individualCount = 0;

        for (int i = 1; i <= doneeList.getNumberOfEntries(); i++) {
            Donee donee = doneeList.getEntry(i);
            if (donee.getDoneeType().equalsIgnoreCase("Organization")) {
                organizationCount++;
            } else if (donee.getDoneeType().equalsIgnoreCase("Individual")) {
                individualCount++;
            }
        }

        System.out.println("Summary Report:");
        System.out.println("Total Donees: " + totalDonees);
        System.out.println("Total Organizations: " + organizationCount);
        System.out.println("Total Individuals: " + individualCount);
    }

    public String getAllDonees() {
        String outputStr = "";
        for (int i = 1; i <= doneeList.getNumberOfEntries(); i++) {
            outputStr += doneeList.getEntry(i) + "\n";
        }
        return outputStr;
    }

    private int calculateNextId() {
        int maxId = 0;
        for (int i = 1; i <= doneeList.getNumberOfEntries(); i++) {
            Donee donee = doneeList.getEntry(i);
            int currentId = Integer.parseInt(donee.getId().substring(2)); // Extract numeric part
            if (currentId > maxId) {
                maxId = currentId;
            }
        }
        return maxId + 1; // Next available ID
    }

    public void runDoneeMaintenance() {
        String choice = "";
        do {
            choice = doneeUI.getMenuChoice();

            switch (choice) {
                case "0":
                    MessageUI.displayExitMessage();
                    saveDoneeData();
                    break;
                case "1":
                    registerNewDonee();
                    if (!doneeList.isEmpty()) {
                        doneeUI.printDoneeDetails(doneeList.getEntry(doneeList.getNumberOfEntries()));
                    } else {
                        System.out.println("No donees to display.");
                    }
                    break;
                case "2":
                    System.out.println(getAllDonees());
                    removeDonee();
                    break;
                case "3":
                    updateDoneeDetails();
                    break;
                case "4":
                    searchDoneeDetails();

                    break;
                case "5":
                    // Pending implementation
                    String aidChoice;
                    Donation.DonationType donationType = null;
                    do {
                        aidChoice = doneeUI.getDonationMenuChoice();
                        switch (aidChoice) {
                            case "1":
                                donationType = Donation.DonationType.FOOD;
                                break;
                            case "2":
                                donationType = Donation.DonationType.DAILY_EXPENSES;
                                break;
                            case "3":
                                donationType = Donation.DonationType.CASH;
                                break;
                            case "4":
                                manager.printAvailableDonations();
                                aidChoice = "";
                                break;
                            default:
                                MessageUI.displayInvalidChoiceMessage();
                                break;
                        }
                        if (donationType != null) {
                            String doneeID = doneeUI.inputDoneeID();
                            Donee target = new Donee(doneeID, "", "", "", "", "", ""); // Create Donee with the given ID
                            double doubleAmount;

                            // Perform the linear search
                            Donee result = doneeList.linearSearch(target);
                            if (result != null) {

                                // Input and validation for Donee Name
                                do {
                                    String strAmount = doneeUI.inputAmount();
                                    if (ValidationUI.isNotEmpty(strAmount) && ValidationUI.isValidAmount(strAmount)) {
                                        doubleAmount = Double.parseDouble(strAmount);
                                        break;
                                    } else {
                                        System.out.println("Please enter valid amount.");
                                    }
                                } while (true);

                                manager.distributeDonation(result.getId(), doubleAmount, new Date(), donationType);

                            }
                        }

                    } while (!aidChoice.equals("0"));

                    break;

                case "6":
                    filterDonees();
                    break;
                case "7":
                    generateSummaryReport();
                    break;
                case "8":
                    undo();
                    break;
                case "9":
                    redo();
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
                    if (!ValidationUI.isDigit(choice)) {
                        MessageUI.displayDigitOnlyMessage();
                    } else {
                        System.out.println("Please enter choice within 0 to 7.");
                    }
            }
        } while (!choice.equals("0"));
    }

    // Command interface for undo/redo operations
    public interface Command {

        void execute();

        void undo();

        void redo();
    }

// Example command implementation for adding a Donee
    public class AddDoneeCommand implements Command {

        private ListInterface<Donee> doneeList;
        private Donee donee;

        public AddDoneeCommand(ListInterface<Donee> doneeList, Donee donee) {
            this.doneeList = doneeList;
            this.donee = donee;
        }

        @Override
        public void execute() {
            doneeList.add(donee);
        }

        @Override
        public void undo() {
            doneeList.remove(donee);
            System.out.println("Add command have been undo.");
        }

        @Override
        public void redo() {
            System.out.println("Add command have been redo.");
            doneeList.add(donee);
        }
    }

// Example command implementation for removing a Donee
    public class RemoveDoneeCommand implements Command {

        private ListInterface<Donee> doneeList;
        private Donee donee;

        public RemoveDoneeCommand(ListInterface<Donee> doneeList, Donee donee) {
            this.doneeList = doneeList;
            this.donee = donee;
        }

        @Override
        public void execute() {
            doneeList.remove(donee);
        }

        @Override
        public void undo() {
            doneeList.add(donee);
            System.out.println("Remove command have been undo.");
        }

        @Override
        public void redo() {
            doneeList.remove(donee);
            System.out.println("Remove command have been undo.");
        }
    }

    public class UpdateDoneeCommand implements Command {

        private ListInterface<Donee> doneeList;
        private Donee originalDonee;
        private Donee updatedDonee;

        public UpdateDoneeCommand(ListInterface<Donee> doneeList, Donee originalDonee, Donee updatedDonee) {
            this.doneeList = doneeList;
            this.originalDonee = originalDonee;
            this.updatedDonee = updatedDonee;
        }

        @Override
        public void execute() {
            // Apply the update
            updateDonee(updatedDonee);
            System.out.println("Update command have been undo.");
        }

        @Override
        public void undo() {
            // Revert to the original state
            updateDonee(originalDonee);
            System.out.println("Update command have been undo.");
        }

        @Override
        public void redo() {
            // Reapply the update
            updateDonee(updatedDonee);
        }

        private void updateDonee(Donee donee) {
            for (int i = 1; i <= doneeList.getNumberOfEntries(); i++) {
                if (doneeList.getEntry(i).getId().equals(donee.getId())) {
                    doneeList.replace(i, donee);
                    break;
                }
            }
        }
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            Command command = undoStack.pop();
            command.undo();
            redoStack.push(command);
        } else {
            System.out.println("No actions to undo.");
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            Command command = redoStack.pop();
            command.redo();
            undoStack.push(command);
        } else {
            System.out.println("No actions to redo.");
        }
    }

    public static class DonationManager {

        private LinkedList<Donation> donationList;
        private LinkedList<Distribution> distributionList;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        public DonationManager() throws ParseException {
            this.donationList = new LinkedList<>();
            this.distributionList = new LinkedList<>();
            loadDonations();
            loadDistributions();
        }

        // Method to load donations from donation.csv
        public void loadDonations() throws ParseException {

            try (Scanner scanner = new Scanner(new File("Donation.csv"))) {
                // Skip the header line
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }
                while (scanner.hasNextLine()) {
                    String[] data = scanner.nextLine().split(",");
                    String donationID = data[0];
                    String donorID = data[1];
                    double amount = Double.parseDouble(data[2]);
                    Date date;
                    try {
                        date = dateFormat.parse(data[3]);
                    } catch (ParseException e) {
                        System.err.println("Error parsing date: " + e.getMessage());
                        continue; // Skip this line and continue with the next
                    }
                    String paymentMethod = data[4];
                    String receiptNumber = data[5];
                    Donation.DonationType donationType;
                    try {
                        donationType = Donation.DonationType.valueOf(data[6].toUpperCase());
                    } catch (IllegalArgumentException e) {
                        System.err.println("Error parsing donation type: " + e.getMessage());
                        continue; // Skip this line and continue with the next
                    }
                    String notes = data[7];

                    donationList.add(new Donation(donationID, donorID, amount, date, paymentMethod, receiptNumber, donationType, notes));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        // Method to load distributions from donationDistribution.csv
        public void loadDistributions() throws ParseException {
            try (Scanner scanner = new Scanner(new File("donationDistribution.csv"))) {
                // Skip the header line
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }
                while (scanner.hasNextLine()) {
                    String[] data = scanner.nextLine().split(",");
                    String distributionID = data[0];
                    String donationID = data[1];
                    String doneeID = data[2];
                    double distributedAmount = Double.parseDouble(data[3]);
                    Date date;
                    try {
                        date = dateFormat.parse(data[4]);
                    } catch (ParseException e) {
                        System.err.println("Error parsing date: " + e.getMessage());
                        continue; // Skip this line and continue with the next
                    }
                    String type = data[5];

                    distributionList.add(new Distribution(distributionID, donationID, doneeID, distributedAmount, dateFormat.format(date), type));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        // Method to distribute a donation
        public boolean distributeDonation(String doneeID, double distributedAmount, Date date, Donation.DonationType donationType) {
            // Filter donations by the specified type
            LinkedList<Donation> eligibleDonations = new LinkedList<>();
            for (Donation donation : donationList) {
                if (donation.getDonationType().equals(donationType)) {
                    // Calculate total distributed amount for this donation ID
                    double totalDistributedAmount = 0.0;
                    for (Distribution distribution : distributionList) {
                        if (distribution.getDonationID().equals(donation.getDonationId())) {
                            totalDistributedAmount += distribution.getDistributedAmount();
                        }
                    }

                    double availableAmount = donation.getAmount() - totalDistributedAmount;
                    if (availableAmount > 0) {
                        eligibleDonations.add(new Donation(donation.getDonationId(), donation.getDonorId(), availableAmount, donation.getDate(), donation.getPaymentMethod(), donation.getReceiptNumber(), donation.getDonationType(), donation.getNotes()));
                    }
                }
            }

            if (eligibleDonations.isEmpty()) {
                System.out.println("Error: No eligible donations available for the specified type.");
                return false;
            }

            double remainingAmount = distributedAmount;
            for (Donation donation : eligibleDonations) {
                double availableAmount = donation.getAmount();
                if (remainingAmount <= availableAmount) {
                    // Create a new Distribution entry
                    String distributionID = generateDistributionID();
                    Distribution newDistribution = new Distribution(distributionID, donation.getDonationId(), doneeID, remainingAmount, dateFormat.format(date), donationType.name());

                    // Add the new distribution to the distribution list
                    distributionList.add(newDistribution);

                    // Save the updated distribution data
                    saveDistributionData();

                    System.out.println("Distribution successfully created.");
                    System.out.println(distributionList.getEntry(distributionList.getNumberOfEntries()));
                    return true;
                } else {
                    // Create a new Distribution entry with the maximum available amount
                    String distributionID = generateDistributionID();
                    Distribution newDistribution = new Distribution(distributionID, donation.getDonationId(), doneeID, availableAmount, dateFormat.format(date), donationType.name());

                    // Add the new distribution to the distribution list
                    distributionList.add(newDistribution);

                    // Update the remaining amount to be distributed
                    remainingAmount -= availableAmount;
                }
            }

            System.out.println("Error: Distributed amount exceeds available donation amounts.");
            return false;
        }

        private String generateDistributionID() {
            return "DIST" + (distributionList.size() + 1);
        }

        // Method to save distribution data to donationDistribution.csv
        private void saveDistributionData() {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("donationDistribution.csv"))) {
                // Write the header line
                writer.write("DistributionID,DonationID,DoneeID,DistributedAmount,IssuedDate,AidType");
                writer.newLine();

                // Write the distribution data
                for (Distribution distribution : distributionList) {
                    writer.write(distribution.getDistributionID() + ","
                            + distribution.getDonationID() + ","
                            + distribution.getdoneeID() + ","
                            + distribution.getDistributedAmount() + ","
                            + distribution.getDate() + ","
                            + distribution.getType());
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Method to print all donations
        public void printDonations() {

            for (Donation donation : donationList) {
                System.out.println(donation);
            }
        }

        // Method to print available donations based on the remaining amount
        public void printAvailableDonations() {

            System.out.println("Available Donations:");
            for (Donation donation : donationList) {
                // Calculate total distributed amount for this donation ID
                double totalDistributedAmount = 0.0;
                for (Distribution distribution : distributionList) {
                    if (distribution.getDonationID().equals(donation.getDonationId())) {
                        totalDistributedAmount += distribution.getDistributedAmount();
                    }
                }

                double availableAmount = donation.getAmount() - totalDistributedAmount;

                if (availableAmount > 0) {
                    // Print the donation information along with the available amount
                    System.out.println("Donation ID: " + donation.getDonationId()
                            //                            + ", Donor ID: " + donation.getDonorId()
                            //                            + ", Amount: " + donation.getAmount()
                            + ", Available Amount: " + availableAmount
                            //                            + ", Date: " + dateFormat.format(donation.getDate())
                            //                            + ", Payment Method: " + donation.getPaymentMethod()
                            //                            + ", Receipt Number: " + donation.getReceiptNumber()
                            + ", Donation Type: " + donation.getDonationType()
                            + ", Notes: " + donation.getNotes());

                }
            }
        }

    }

    public static void main(String[] args) throws ParseException {
        DoneeMaintenance doneeMaintenance = new DoneeMaintenance();
        doneeMaintenance.runDoneeMaintenance();
    }

}
