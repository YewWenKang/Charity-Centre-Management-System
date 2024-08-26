package boundary;

import ADT.LinkedList;
import ADT.ListInterface;
import control.DonationMaintenance;
import entity.Donation;
import entity.Donation.DonationType;
import java.util.Date;
import java.util.Scanner;
import utility.ValidationUI;

public class DonationMaintenanceUI {
    private final Scanner scanner ;
    private final DonationMaintenance donationMaintenance;

    public DonationMaintenanceUI(DonationMaintenance donationMaintenance) {
        this.scanner = new Scanner(System.in);
        this.donationMaintenance = donationMaintenance;
    }

    // Method to display the main menu
    public void displayMenu() {

        while (true) {
            System.out.println("\n=============================");
            System.out.println("  Donation Maintenance Menu");
            System.out.println("=============================");
            System.out.println(" 1. Create Donation");
            System.out.println(" 2. View Donation");
            System.out.println(" 3. Filter Donation");
            System.out.println(" 4. Delete Donation");
            System.out.println(" 5. Report");
            System.out.println(" 0. Exit");
            System.out.println("=============================");
            System.out.print(" Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            System.out.println(); // Add an empty line for spacing

            switch (choice) {
                case 1:
                    createDonation();
                    break;
                case 2:
                    viewDonation();
                    break;
                case 3:
                    filterDonation();
                    break;
                case 4:
                    deleteDonation();
                    return;
                case 5:
                    donationReport();
                    break;
                case 0:
                    System.out.println("Exiting menu...");
                    return;
                default:
                    System.out.println("❌ Invalid choice. Please try again.");
                    break;
            }
        }
    }

    // Method to create a new donation with validation
    private void createDonation() {
        String donationId = donationMaintenance.generateDonationId();
        String donorId, amountString, dateString, paymentMethod, typeString, notes;
        double amount;
        Date date;

        // Validate Donor ID
        while (true) {
            System.out.print("Enter Donor ID: ");
            donorId = scanner.nextLine();
            if (ValidationUI.isNotEmpty(donorId) && DonationMaintenance.donorIdExistsInCSV(donorId, "donorData.csv")) {
                break;
            }
            System.out.println("Donor ID cannot be empty or not found in the system.");
            if (!ValidationUI.retryOrExit())
                return;
        }

        // Validate Amount
        while (true) {
            System.out.print("Enter Amount (RM): ");
            amountString = scanner.nextLine();
            if (ValidationUI.isNotEmpty(amountString) && ValidationUI.isValidAmount(amountString)) {
                amount = Double.parseDouble(amountString);
                break;
            }
            System.out.println("Amount cannot be empty and must be a valid number.");
            if (!ValidationUI.retryOrExit())
                return;
        }

        // Validate Date
        while (true) {
            System.out.print("Enter Date (yyyy-MM-dd): ");
            dateString = scanner.nextLine();
            date = donationMaintenance.parseDate(dateString);
            if (date != null)
                break;
            System.out.println("Invalid date format.");
            if (!ValidationUI.retryOrExit())
                return;
        }

        // Validate Payment Method
        while (true) {
            System.out.print("Enter Payment Method: ");
            paymentMethod = scanner.nextLine();
            if (ValidationUI.isNotEmpty(paymentMethod))
                break;
            System.out.println("Payment Method cannot be empty.");
            if (!ValidationUI.retryOrExit())
                return;
        }

        // Generate Receipt Number
        String receiptNumber = donationMaintenance.generateReceiptNumber();

        // Validate Donation Type
        while (true) {
            System.out.print("Enter Donation Type (FOOD, DAILY_EXPENSES, CASH): ");
            typeString = scanner.nextLine();
            if (ValidationUI.isNotEmpty(typeString) &&
                    typeString.matches("(?i)^(FOOD|DAILY_EXPENSES|CASH)$")) {
                break;
            }
            System.out.println("Donation Type is invalid! It must be one of: FOOD, DAILY_EXPENSES, CASH.");
            if (!ValidationUI.retryOrExit())
                return;
        }
        DonationType donationType = DonationType.valueOf(typeString.toUpperCase());

        // Validate Notes
        while (true) {
            System.out.print("Enter Notes: ");
            notes = scanner.nextLine();
            if (ValidationUI.isNotEmpty(notes))
                break;
            System.out.println("Notes cannot be empty.");
            if (!ValidationUI.retryOrExit())
                return;
        }

        // Create the donation
        Donation donation = new Donation(donationId, donorId, amount, date, paymentMethod, receiptNumber, donationType,
                notes);
        donationMaintenance.addDonation(donation);
        System.out.println("Donation created: \n\n" + donation);
    }

    // view donation
    private void viewDonation() {

        while (true) {
            // Prompt the user to enter a donor ID
            System.out.print("Enter Donor ID to view (or type 'exit' to cancel): ");
            String donorId = scanner.nextLine().trim();

            // Exit condition
            if (donorId.equalsIgnoreCase("exit")) {
                return;
            }

            // Validate the donor ID
            if (donorId.isEmpty()) {
                System.out.println("Donor ID cannot be empty.");
                continue; // Re-prompt the user
            }

            // Retrieve the list of donations associated with the provided donor ID from CSV
            ListInterface<Donation> donations = donationMaintenance.getDonationsByDonorIdFromCSV(donorId);

            if (donations == null || donations.isEmpty()) {
                System.out.println("No donations found with the given Donor ID.");
                continue; // Re-prompt for donor ID
            }

            // Prompt for sorting preference
            System.out.println("\nChoose sorting option:");
            System.out.println("1. Sort by Date (Ascending)");
            System.out.println("2. Sort by Date (Descending)");
            System.out.println("3. Sort by Amount (Ascending)");
            System.out.println("4. Sort by Amount (Descending)");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim();

            // Sort based on user choice
            switch (choice) {
                case "1":
                    donations = donationMaintenance.sortDonationsByDate(donations, true);
                    break;
                case "2":
                    donations = donationMaintenance.sortDonationsByDate(donations, false);
                    break;
                case "3":
                    donations = donationMaintenance.sortDonationsByAmount(donations, true);
                    break;
                case "4":
                    donations = donationMaintenance.sortDonationsByAmount(donations, false);
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    continue; // Re-prompt for sorting choice
            }

            // Display the sorted donations
            donationMaintenance.displayDonationsForDonor(donorId, donations);
            break;
        }
    }

    // -----------------------------------------------------------------------------------------------

    public void filterDonation() {
        System.out.println("1. Filter by Donation Type");
        System.out.println("2. Filter by Date Range");
        System.out.println("3. Filter by Donation Amount");
        System.out.println("4. Filter by Payment Method");
        System.out.println("0. Return to menu");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        ListInterface<Donation> filteredDonations = new LinkedList<>();

        switch (choice) {
            case 1:
                // Filter by Donation Type
                System.out.print("Enter Donation Type (FOOD, DAILY_EXPENSES, CASH): ");
                String donationType = scanner.nextLine();
                try {
                    filteredDonations = donationMaintenance.filterDonationsByDonationType(
                            donationMaintenance.getAllDonations(), // Assuming a method to get all donations
                            DonationType.valueOf(donationType.toUpperCase()));
                    donationMaintenance.displayFilteredDonations(filteredDonations);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid Donation Type. Please enter a valid type.");
                }
                break;
            case 2:
                // Filter by Date Range
                System.out.print("Enter Start Date (yyyy-MM-dd): ");
                String startDateString = scanner.nextLine();
                Date startDate = donationMaintenance.parseDate(startDateString);

                System.out.print("Enter End Date (yyyy-MM-dd): ");
                String endDateString = scanner.nextLine();
                Date endDate = donationMaintenance.parseDate(endDateString);

                if (startDate != null && endDate != null) {
                    filteredDonations = donationMaintenance.filterDonationsByDate(
                            donationMaintenance.getAllDonations(), startDate, endDate);
                    donationMaintenance.displayFilteredDonations(filteredDonations);
                } else {
                    System.out.println("Invalid date format. Please enter dates in yyyy-MM-dd format.");
                }
                break;
            case 3:
                // Filter by Donation Amount
                System.out.print("Enter Minimum Amount (RM): ");
                double minAmount = scanner.nextDouble();
                scanner.nextLine(); // Consume newline

                System.out.print("Enter Maximum Amount (RM): ");
                double maxAmount = scanner.nextDouble();
                scanner.nextLine(); // Consume newline

                filteredDonations = donationMaintenance.filterDonationsByAmount(
                        donationMaintenance.getAllDonations(), minAmount, maxAmount);
                donationMaintenance.displayFilteredDonations(filteredDonations);
                break;
            case 4:
                // Filter by Payment Method
                System.out.print("Enter Payment Method: ");
                String paymentMethod = scanner.nextLine();
                filteredDonations = donationMaintenance.filterDonationsByPaymentMethod(
                        donationMaintenance.getAllDonations(), paymentMethod);
                donationMaintenance.displayFilteredDonations(filteredDonations);
                break;
            case 0:
                // Return to menu
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    // -----------------------------------------------------------------------------------------------

    // delete donation
    private void deleteDonation() {
        boolean validInput = false;
    
        while (!validInput) {
            System.out.println("1. Remove a single donation");
            System.out.println("2. Clear all donations");
            System.out.println("0. Return to menu");
            System.out.print("Enter your choice: ");
            
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                switch (choice) {
                    case 1:
                        // Remove a single donation
                        System.out.print("Enter Donation ID to remove: ");
                        String donationId = scanner.nextLine();
                        donationMaintenance.deleteDonation(donationId);
                        validInput = true; // Exit loop after successful operation
                        displayMenu();
                        break;
                    case 2:
                        // Clear all donations
                        donationMaintenance.clearAllDonations();
                        validInput = true; // Exit loop after successful operation
                        displayMenu();
                        break;
                    case 0:
                        // Return to menu
                        validInput = true; // Exit loop and return to menu
                        return; // Exit method
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } else {
                System.out.println("Invalid input. Please enter a number.\n");
                scanner.next(); // Consume invalid input
            }
        }
    }

    // -----------------------------------------------------------------------------------------------
    private void donationReport(){
        System.out.println("1. Donation Summary Report");
        System.out.println("0. Return to menu");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                donationMaintenance.generateDonationSummaryReport();
                break;
            case 0:
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }
    



    public static void main(String[] args) {
        DonationMaintenance donationMaintenance = new DonationMaintenance();
        DonationMaintenanceUI ui = new DonationMaintenanceUI(donationMaintenance);
        ui.displayMenu();
    }
}
