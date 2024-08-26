package boundary;

import ADT.DictionaryInterface;
import ADT.HashedDictionary;
import ADT.TreeMapImplementation;
import ADT.TreeMapInterface;
import control.DonorMaintenance;
import entity.Donor;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import utility.ValidationUI;

public class DonorMaintenanceUI {
    private final Scanner scanner;
    private final DonorMaintenance donorMaintenance;
    private final Random random;
    private static final String CSV_FILE_NAME = "donorData.csv"; // Specify the CSV file name

    public DonorMaintenanceUI() {
        scanner = new Scanner(System.in);
        donorMaintenance = new DonorMaintenance();
        random = new Random();
    }

    // Method to generate a unique Donor ID
    private String generateUniqueDonorId() {
        DictionaryInterface<Integer, Void> existingIds = getExistingDonorIds();
        int newId;

        do {
            newId = random.nextInt(999) + 1; // Generate a random number between 1 and 999
        } while (existingIds.contains(newId));

        return String.format("DA%03d", newId); // Format the ID as DA### with leading zeros
    }

    // Method to read existing donor IDs from the CSV file
    private DictionaryInterface<Integer, Void> getExistingDonorIds() {
        DictionaryInterface<Integer, Void> existingIds = new HashedDictionary<>();
        Pattern pattern = Pattern.compile("DA(\\d{3})");

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_NAME))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false; // Skip the header line
                    continue;
                }
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    int id = Integer.parseInt(matcher.group(1));
                    existingIds.add(id, null); // Add ID to dictionary with null value
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return existingIds;
    }

    public void start() {
        int choice;
        do {
            showMenu();
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1:
                    addDonor();
                    break;
                case 2:
                    updateDonor();
                    break;
                case 3:
                    deleteDonor();
                    break;
                case 4:
                    viewAllDonors();
                    break;
                case 5:
                    searchDonors();
                    break;
                case 6:
                    filterDonors();
                    break;
                case 7:
                    showReportMenu();
                    break;
                case 8:
                    System.out.println("Exiting Donor Maintenance System.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    private void showMenu() {
        System.out.println("\n====================================");
        System.out.println("       Donor Maintenance System");
        System.out.println("====================================");
        System.out.println(" 1. Add Donor");
        System.out.println(" 2. Update Donor");
        System.out.println(" 3. Delete Donor");
        System.out.println(" 4. View All Donors");
        System.out.println(" 5. Search Donors");
        System.out.println(" 6. Filter Donors");
        System.out.println(" 7. Donor Report");
        System.out.println(" 8. Exit");
        System.out.println("------------------------------------");
        System.out.print("Enter your choice: ");
    }

    private void addDonor() {
        String name, contactNumber, email, address, donorType, donationPreference, donorTimes, totalAmount;

        while (true) {
            System.out.print("Enter Name: ");
            name = scanner.nextLine();
            if (ValidationUI.isNotEmpty(name))
                break;
            System.out.println("Name cannot be empty.");
            if (!ValidationUI.retryOrExit())
                return;
        }

        while (true) {
            System.out.print("Enter Contact Number: ");
            contactNumber = scanner.nextLine();
            if (ValidationUI.isValidPhoneNumber(contactNumber))
                break;
            System.out.println("Invalid contact number format. It should start with '01' and be 10 or 11 digits long.");
            if (!ValidationUI.retryOrExit())
                return;
        }

        while (true) {
            System.out.print("Enter Email: ");
            email = scanner.nextLine();
            if (ValidationUI.isValidEmail(email))
                break;
            System.out.println("Invalid email format.");
            if (!ValidationUI.retryOrExit())
                return;
        }

        while (true) {
            System.out.print("Enter Address: ");
            address = scanner.nextLine();
            if (ValidationUI.isNotEmpty(address))
                break;
            System.out.println("Address cannot be empty.");
            if (!ValidationUI.retryOrExit())
                return;
        }

        while (true) {
            System.out.print("Enter Donor Type (Government, Private, Public): ");
            donorType = scanner.nextLine();

            if (ValidationUI.isNotEmpty(donorType) && donorType.matches("(?i)^(Government|Private|Public)$")) {
                break;
            }

            System.err.println("Donor Type is invalid! It must be one of: Government, Private, Public.");

            if (!ValidationUI.retryOrExit()) {
                return;
            }
        }

        while (true) {
            System.out.print("Enter Donation Preference (Cash, Bank In, Tng): ");
            donationPreference = scanner.nextLine();

            if (ValidationUI.isNotEmpty(donationPreference) &&
                    donationPreference.matches("(?i)^(Cash|Bank In|Tng)$")) {
                break;
            }

            System.out.println("Donation Preference is invalid! It must be one of: Cash, Bank In, Tng.");

            if (!ValidationUI.retryOrExit()) {
                return;
            }
        }

        while (true) {
            System.out.print("Enter Donation Times: ");
            donorTimes = scanner.nextLine();

            if (ValidationUI.isNotEmpty(donorTimes) && ValidationUI.isDigit(donorTimes)) {
                break;
            }

            System.out.println("Donation Times cannot be empty and must be a valid number.");

            if (!ValidationUI.retryOrExit()) {
                return;
            }
        }

        while (true) {
            System.out.print("Enter Total Amount (RM): ");
            totalAmount = scanner.nextLine();

            if (ValidationUI.isNotEmpty(totalAmount) && ValidationUI.isValidAmount(totalAmount)) {
                break;
            }

            System.out.println("Total Amount cannot be empty and must be a valid number.");

            if (!ValidationUI.retryOrExit()) {
                return;
            }
        }

        // Generate sequential Donor ID after all details are entered
        // Inside the addDonor method
        String donorId = generateUniqueDonorId();
        System.out.println("Generated Donor ID: " + donorId);

        donorMaintenance.addDonor(donorId, name, contactNumber, email, address, donorType, donationPreference,
                donorTimes, totalAmount);

    }

    private void updateDonor() {
        System.out.println("\n--- Update Donor ---");
        System.out.print("Enter Donor ID to update: ");
        String donorId = scanner.nextLine();
        Donor donor = donorMaintenance.findDonorById(donorId);

        if (donor != null) {
            boolean updating = true;

            while (updating) {
                System.out.println("\nSelect the field to update:");
                System.out.println(String.format("1. %-20s : %s", "Name", donor.getName()));
                System.out.println(String.format("2. %-20s : %s", "Contact Number", donor.getContactNumber()));
                System.out.println(String.format("3. %-20s : %s", "Email", donor.getEmail()));
                System.out.println(String.format("4. %-20s : %s", "Address", donor.getAddress()));
                System.out.println(String.format("5. %-20s : %s", "Donor Type", donor.getDonorType()));
                System.out
                        .println(String.format("6. %-20s : %s", "Donation Preference", donor.getDonationPreference()));
                System.out.println("7. Save changes and return");
                System.out.print("Enter your choice: ");

                int choice = -1;
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input, please enter a number.");
                    continue;
                }

                switch (choice) {
                    case 1:
                        String name;
                        while (true) {
                            System.out.print("Enter new Name: ");
                            name = scanner.nextLine();
                            if (ValidationUI.isNotEmpty(name)) {
                                donor.setName(name);
                                break;
                            } else {
                                System.out.println("Name cannot be empty.");
                                if (!ValidationUI.retryOrExit())
                                    return;
                            }
                        }
                        break;
                    case 2:
                        String contactNumber;
                        while (true) {
                            System.out.print("Enter new Contact Number: ");
                            contactNumber = scanner.nextLine();
                            if (ValidationUI.isValidPhoneNumber(contactNumber)) {
                                donor.setContactNumber(contactNumber);
                                break;
                            } else {
                                System.out.println(
                                        "Invalid contact number format. It should start with '01' and be 10 or 11 digits long.");
                                if (!ValidationUI.retryOrExit())
                                    return;
                            }
                        }
                        break;
                    case 3:
                        String email;
                        while (true) {
                            System.out.print("Enter new Email: ");
                            email = scanner.nextLine();
                            if (ValidationUI.isValidEmail(email)) {
                                donor.setEmail(email);
                                break;
                            } else {
                                System.out.println("Invalid email format.");
                                if (!ValidationUI.retryOrExit())
                                    return;
                            }
                        }
                        break;
                    case 4:
                        String address;
                        while (true) {
                            System.out.print("Enter new Address: ");
                            address = scanner.nextLine();
                            if (ValidationUI.isNotEmpty(address)) {
                                donor.setAddress(address);
                                break;
                            } else {
                                System.out.println("Address cannot be empty.");
                                if (!ValidationUI.retryOrExit())
                                    return;
                            }
                        }
                        break;
                    case 5:
                        String donorType;
                        while (true) {
                            System.out.print("Enter new Donor Type (Government, Private, Public): ");
                            donorType = scanner.nextLine();
                            if (ValidationUI.isNotEmpty(donorType)
                                    && donorType.matches("(?i)^(Government|Private|Public)$")) {
                                donor.setDonorType(donorType);
                                break;
                            } else {
                                System.err.println(
                                        "Donor Type is invalid! It must be one of: Government, Private, Public.");
                                if (!ValidationUI.retryOrExit())
                                    return;
                            }
                        }
                        break;
                    case 6:
                        String donationPreference;
                        while (true) {
                            System.out.print("Enter new Donation Preference (Cash, Bank In, Tng): ");
                            donationPreference = scanner.nextLine();
                            if (ValidationUI.isNotEmpty(donationPreference) &&
                                    donationPreference.matches("(?i)^(Cash|Bank In|Tng)$")) {
                                donor.setDonationPreference(donationPreference);
                                break;
                            } else {
                                System.out.println(
                                        "Donation Preference is invalid! It must be one of: Cash, Bank In, Tng.");
                                if (!ValidationUI.retryOrExit())
                                    return;
                            }
                        }
                        break;
                    case 7:
                        updating = false;
                        break;
                    default:
                        System.out.println("Invalid choice, please try again.");
                }
            }

            if (donorMaintenance.updateDonor(donor.getDonorId(), donor.getName(), donor.getContactNumber(),
                    donor.getEmail(), donor.getAddress(),
                    donor.getDonorType(), donor.getDonationPreference())) {
                System.out.println("Donor updated successfully.");
            } else {
                System.out.println("Failed to update donor details.");
            }
        } else {
            System.out.println("Donor not found.");
        }
    }

    private void deleteDonor() {
        System.out.print("Enter Donor ID to delete: ");
        String donorId = scanner.nextLine();
    
        Donor donor = donorMaintenance.findDonorById(donorId);
        if (donor == null) {
            System.out.println("Donor not found.");
            return;
        }
    
        while (true) {
            System.out.println("Are you sure you want to delete the donor with ID: " + donorId + "?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            System.out.print("Enter your choice: ");
    
            String input = scanner.nextLine();
    
            if (ValidationUI.isDigit(input)) {
                int choice = Integer.parseInt(input);
    
                switch (choice) {
                    case 1:
                        // User confirmed deletion
                        if (donorMaintenance.deleteDonor(donorId)) {
                            System.out.println("Donor deleted successfully.");
                        } else {
                            System.out.println("Failed to delete donor.");
                        }
                        return;
    
                    case 2:
                        // User canceled deletion
                        System.out.println("Deletion canceled.");
                        return;
    
                    default:
                        // Invalid choice
                        System.out.println("Invalid choice. Please enter 1 for Yes or 2 for No.");
                        break;
                }
            } else {
                // Invalid input
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
    

    private void viewAllDonors() {
        while (true) {
            // Display the list of all donors
            donorMaintenance.viewAllDonors();
    
            // Present sorting options to the user
            displaySortingOptions();
    
            // Read and validate the user's choice
            String input = getUserChoice();
    
            // Process the user's choice
            if (ValidationUI.isDigit(input)) {
                int choice = Integer.parseInt(input);
                handleSortingChoice(choice);
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
    
    private void displaySortingOptions() {
        System.out.println("\n--- Sort Donors ---");
        System.out.println("1. Sort by Donor ID");
        System.out.println("2. Sort by Donor Name");
        System.out.println("3. Sort by Total Amount");
        System.out.println("4. Exit");
    }
    
    private String getUserChoice() {
        System.out.print("Enter your choice: ");
        return scanner.nextLine(); // Read input as String for validation
    }
    
    private void handleSortingChoice(int choice) {
        switch (choice) {
            case 1:
                sortById();
                break;
            case 2:
                sortByName();
                break;
            case 3:
                sortByAmount();
                break;
            case 4:
                exitToMainMenu();
                return; // Exit the method to avoid re-prompting
            default:
                System.out.println("Invalid choice. Please enter a number between 1 and 4.");
                break;
        }
    }
    
    private void exitToMainMenu() {
        System.out.println("Exiting to the main menu...");
        start(); // Assumes this method returns to the main menu
    }
    

    // Sort by ID
    private void sortById() {
        while (true) {
            System.out.println("Sort by Donor ID");
            System.out.println("1. Sort by Descending Order");
            System.out.println("2. Sort by Ascending Order");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            String input = scanner.nextLine(); // Read input as String for validation

            if (ValidationUI.isDigit(input)) {
                int choice = Integer.parseInt(input);
                switch (choice) {
                    case 1:
                        donorMaintenance.sortByIdDescending();
                        break;
                    case 2:
                        donorMaintenance.sortByIdAscending();
                        break;
                    case 3:
                        return; // Return to the previous menu (viewAllDonors)
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        continue; // Re-display the sorting menu
                }
                // Re-display the donors list after sorting
                viewAllDonors();
                return;
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    // Sort by Name
    private void sortByName() {
        while (true) {
            System.out.println("Sort by Donor Name");
            System.out.println("1. Sort by Descending Order");
            System.out.println("2. Sort by Ascending Order");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            String input = scanner.nextLine(); // Read input as String for validation

            if (ValidationUI.isDigit(input)) {
                int choice = Integer.parseInt(input);
                switch (choice) {
                    case 1:
                        donorMaintenance.sortByNameDescending();
                        break;
                    case 2:
                        donorMaintenance.sortByNameAscending();
                        break;
                    case 3:
                        return; // Return to the previous menu (viewAllDonors)
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        continue; // Re-display the sorting menu
                }
                // Re-display the donors list after sorting
                viewAllDonors();
                return;
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    // Sort by Amount
    private void sortByAmount() {
        while (true) {
            System.out.println("Sort by Total Amount");
            System.out.println("1. Sort by Descending Order");
            System.out.println("2. Sort by Ascending Order");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            String input = scanner.nextLine(); // Read input as String for validation

            if (ValidationUI.isDigit(input)) {
                int choice = Integer.parseInt(input);
                switch (choice) {
                    case 1:
                        donorMaintenance.sortByAmountDescending();
                        break;
                    case 2:
                        donorMaintenance.sortByAmountAscending();
                        break;
                    case 3:
                        return; // Return to the previous menu (viewAllDonors)
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        continue; // Re-display the sorting menu
                }
                // Re-display the donors list after sorting
                viewAllDonors();
                return;
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private void searchDonors() {
        while (true) {
            // Display search menu
            System.out.println("\n--- Search Donors ---");
            System.out.println("1. Search by Donor ID");
            System.out.println("2. Search by Donor Name");
            System.out.println("3. Search by Contact Number");
            System.out.println("4. Search by Email");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
    
            String input = scanner.nextLine(); // Read input as String for validation
    
            if (ValidationUI.isDigit(input)) {
                int choice = Integer.parseInt(input);
                Donor foundDonor = null; // Variable to store the found donor
                String searchValue;
    
                // Handle search options based on user choice
                switch (choice) {
                    case 1:
                        System.out.print("Enter Donor ID to search: ");
                        searchValue = scanner.nextLine();
                        foundDonor = donorMaintenance.findDonorById(searchValue);
                        break;
                    case 2:
                        System.out.print("Enter Donor Name to search: ");
                        searchValue = scanner.nextLine();
                        foundDonor = donorMaintenance.findDonorByName(searchValue);
                        break;
                    case 3:
                        System.out.print("Enter Contact Number to search: ");
                        searchValue = scanner.nextLine();
                        foundDonor = donorMaintenance.findDonorByContactNumber(searchValue);
                        break;
                    case 4:
                        System.out.print("Enter Email to search: ");
                        searchValue = scanner.nextLine();
                        foundDonor = donorMaintenance.findDonorByEmail(searchValue);
                        break;
                    case 5:
                        exitToMainMenu();
                        return; // Exit the method
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                        continue; // Re-prompt the menu
                }
    
                // Display the search result
                if (foundDonor != null) {
                    System.out.println("\n" + foundDonor);
                } else {
                    System.out.println("Donor not found.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
    

    // filter donors
    private void filterDonors() {
        // Initialize TreeMap to store filtered donors
        TreeMapInterface<String, Donor> filteredDonors = new TreeMapImplementation<>();

        while (true) {
            System.out.println("\n--- Filter Donors ---");
            // Show the categories to filter in the menu
            System.out.println("1. Filter by Donor Type");
            System.out.println("2. Filter by Donation Preference");
            System.out.println("3. Filter by Donation Times");
            System.out.println("4. Filter by Total Amount (RM)");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            String input = scanner.nextLine(); // Read input as String for validation

            if (ValidationUI.isDigit(input)) {
                int choice = Integer.parseInt(input);
                String filterValue;

                switch (choice) {
                    case 1:
                        System.out.print("Enter Donor Type to filter: ");
                        filterValue = scanner.nextLine();
                        filteredDonors = donorMaintenance.filterByDonorType(filterValue);
                        break;
                    case 2:
                        System.out.print("Enter Donation Preference to filter: ");
                        filterValue = scanner.nextLine();
                        filteredDonors = donorMaintenance.filterByDonationPreference(filterValue);
                        break;
                    case 3:
                        System.out.print("Enter Donation Times to filter: ");
                        filterValue = scanner.nextLine();
                        filteredDonors = donorMaintenance.filterByDonationTimes(filterValue);
                        break;
                    case 4:
                        System.out.print("Enter minimum Total Amount (RM) to filter: ");
                        String minAmountStr = scanner.nextLine();
                        System.out.print("Enter maximum Total Amount (RM) to filter: ");
                        String maxAmountStr = scanner.nextLine();

                        double minAmount;
                        double maxAmount;
                        try {
                            minAmount = Double.parseDouble(minAmountStr);
                            maxAmount = Double.parseDouble(maxAmountStr);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid amount entered. Please enter valid numbers.");
                            continue; // Re-prompt the menu
                        }

                        filteredDonors = donorMaintenance.filterByTotalAmount(minAmount, maxAmount);
                        break;
                    case 5:
                        return; // Exit the method
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        continue; // Continue to re-prompt the menu
                }

                // Display the filtered donors
                donorMaintenance.displayFilteredDonors(filteredDonors);

            } else {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    //------------------------------------------------------------------------------------------------

    private void showReportMenu() {
        while (true) {
            System.out.println("\n--- Donor Report Menu ---");
            System.out.println("1. Detailed Donor Report");
            System.out.println("2. Summary Donor Report");
            System.out.println("3. Exit to Main Menu");
            System.out.print("Enter your choice: ");
    
            String input = scanner.nextLine(); // Read input as String for validation
    
            if (ValidationUI.isDigit(input)) {
                int choice = Integer.parseInt(input);
                switch (choice) {
                    case 1:
                        donorMaintenance.generateDetailedDonorReport();
                        break;
                    case 2:
                        donorMaintenance.generateDonorSummaryReport();
                        break;
                    case 3:
                        return; // Exit to main menu
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
    

}
