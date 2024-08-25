package boundary;

import ADT.ListInterface;
import control.DonationMaintenance;
import entity.Donation;
import entity.Donation.DonationType;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import utility.ValidationUI;

public class DonationMaintenanceUI {
    private Scanner scanner;
    private DonationMaintenance donationMaintenance;

    public DonationMaintenanceUI(DonationMaintenance donationMaintenance) {
        this.scanner = new Scanner(System.in);
        this.donationMaintenance = donationMaintenance;
    }

    // Method to display the main menu
    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
    
        while (true) {
            System.out.println("\n=============================");
            System.out.println("  Donation Maintenance Menu");
            System.out.println("=============================");
            System.out.println(" 1. Create Donation");
            System.out.println(" 2. View Donation");
            System.out.println(" 3. Update Donation");
            System.out.println(" 4. Delete Donation");
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
                    updateDonation();
                    break;
                case 4:
                    deleteDonation();
                    return;
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
            date = parseDate(dateString);
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
        System.out.println("Donation created: " + donation);
    }

    // Method to view a donation
    private void viewDonation() {
        // Create a Scanner instance for user input
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter a donor ID
        System.out.print("Enter Donor ID to view: ");
        String donorId = scanner.nextLine().trim(); // Retrieve donor ID and trim any leading/trailing spaces

        // Validate the donor ID
        if (donorId.isEmpty()) {
            System.out.println("Donor ID cannot be empty.");
            return;
        }

        // Retrieve the list of donations associated with the provided donor ID from CSV
        ListInterface<Donation> donations = donationMaintenance.getDonationsByDonorIdFromCSV(donorId);

        // Check if donations were found and display them
        if (donations != null && !donations.isEmpty()) {
            System.out.println("Donation Details:");
            System.out.printf("%-12s %-10s %-10s %-20s %-15s %-15s %-10s %-30s%n",
                    "Donation ID", "Donor ID", "Amount", "Date", "Payment Method",
                    "Receipt No.", "Donation Type", "Notes");
            System.out.println(
                    "------------------------------------------------------------------------------------------------" +
                            "-------------------------------");

            for (Donation donation : donations) {
                System.out.printf("%-12s %-10s %-10.2f %-20s %-15s %-15s %-10s %-30s%n",
                        donation.getDonationId(),
                        donation.getDonorId(),
                        donation.getAmount(),
                        donation.getDate(),
                        donation.getPaymentMethod(),
                        donation.getReceiptNumber(),
                        donation.getDonationType(),
                        donation.getNotes());
            }
        } else {
            // Inform the user if no donations were found
            System.out.println("No donations found with the given Donor ID.");
        }
    }

    // Method to update a donation
    private void updateDonation() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Donation ID to update: ");
        String donationId = scanner.nextLine();

        // Retrieve the list of donations for the given ID
        ListInterface<Donation> donations = donationMaintenance.getDonationsById(donationId);
        if (donations != null && !donations.isEmpty()) {
            System.out.println("Current Details:");
            for (Donation donation : donations) {
                System.out.println(donation);
            }

            System.out.println("Select a donation to update (enter the index):");
            for (int i = 0; i < donations.size(); i++) {
                System.out.println(i + ": " + donations.get(i));
            }

            int index = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (index >= 0 && index < donations.size()) {
                Donation donationToUpdate = donations.get(index);

                System.out.println("Updating details for: " + donationToUpdate);
                // Update details (for simplicity, assume user enters new values)
                System.out.print("Enter new Amount (RM): ");
                double amount = scanner.nextDouble();
                scanner.nextLine(); // Consume newline

                System.out.print("Enter new Date (yyyy-MM-dd): ");
                String dateString = scanner.nextLine();
                Date date = parseDate(dateString);

                // Update other fields as necessary
                donationToUpdate.setAmount(amount);
                donationToUpdate.setDate(date);

                // Remove old donation and re-add updated donation
                donationMaintenance.deleteDonation(donationToUpdate.getDonationId());
                donationMaintenance.addDonation(donationToUpdate);

                System.out.println("Donation updated: " + donationToUpdate);
            } else {
                System.out.println("Invalid index selected.");
            }
        } else {
            System.out.println("No donations found with the given ID.");
        }
    }

    // delete donation
    private void deleteDonation() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Remove a single donation");
        System.out.println("2. Clear all donations");
        System.out.println("0. Return to menu");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                // Remove a single donation
                System.out.print("Enter Donation ID to remove: ");
                String donationId = scanner.nextLine();
                donationMaintenance.deleteDonation(donationId);
                System.out.println("Donation removed.");
                break;
            case 2:
                // Clear all donations
                donationMaintenance.clearAllDonations();
                System.out.println("All donations cleared.");
                break;
            case 0:
                // Return to menu
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    // Helper method to parse date
    private Date parseDate(String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(dateString);
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        DonationMaintenance donationMaintenance = new DonationMaintenance();
        DonationMaintenanceUI ui = new DonationMaintenanceUI(donationMaintenance);
        ui.displayMenu();
    }
}
