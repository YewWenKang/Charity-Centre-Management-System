package boundary;

import control.DonationMaintenance;
import entity.Donation;
import entity.Donation.DonationType;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class DonationMaintenanceUI {
    private Scanner scanner;
    private DonationMaintenance donationMaintenance;

    public DonationMaintenanceUI(DonationMaintenance donationMaintenance) {
        this.scanner = new Scanner(System.in);
        this.donationMaintenance = donationMaintenance;
    }

    // Method to display the main menu
    public void displayMenu() {
        while (true) {
            System.out.println("Donation Maintenance Menu");
            System.out.println("1. Create Donation");
            System.out.println("2. View Donation");
            System.out.println("3. Update Donation");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

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
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    // Method to create a new donation
    private void createDonation() {
        System.out.print("Enter Donation ID: ");
        String donationId = scanner.nextLine();

        System.out.print("Enter Donor ID: ");
        String donorId = scanner.nextLine();

        System.out.print("Enter Amount (RM): ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter Date (yyyy-MM-dd): ");
        String dateString = scanner.nextLine();
        Date date = parseDate(dateString);

        System.out.print("Enter Event ID: ");
        String eventId = scanner.nextLine();

        System.out.print("Enter Payment Method: ");
        String paymentMethod = scanner.nextLine();

        System.out.print("Enter Receipt Number: ");
        String receiptNumber = scanner.nextLine();

        System.out.print("Enter Donation Type (FOOD, DAILY_EXPENSES, CASH): ");
        String typeString = scanner.nextLine();
        DonationType donationType = DonationType.valueOf(typeString.toUpperCase());

        System.out.print("Enter Notes: ");
        String notes = scanner.nextLine();

        Donation donation = new Donation(donationId, donorId, amount, date, eventId, paymentMethod, receiptNumber, donationType, notes);
        donationMaintenance.addDonation(donation);
        System.out.println("Donation created: " + donation);
    }

    // Method to view a donation
    private void viewDonation() {
        System.out.print("Enter Donation ID to view: ");
        String donationId = scanner.nextLine();

        Donation donation = donationMaintenance.getDonationById(donationId);
        if (donation != null) {
            System.out.println("Donation Details: " + donation);
        } else {
            System.out.println("Donation not found.");
        }
    }

    // Method to update a donation
    private void updateDonation() {
        System.out.print("Enter Donation ID to update: ");
        String donationId = scanner.nextLine();

        Donation donation = donationMaintenance.getDonationById(donationId);
        if (donation != null) {
            System.out.println("Current Details: " + donation);
            // Update details (for simplicity, assume user enters new values)
            System.out.print("Enter new Amount (RM): ");
            double amount = scanner.nextDouble();
            scanner.nextLine(); // Consume newline

            System.out.print("Enter new Date (yyyy-MM-dd): ");
            String dateString = scanner.nextLine();
            Date date = parseDate(dateString);

            // Other fields can be updated similarly
            donation.setAmount(amount);
            donation.setDate(date);

            // Re-add donation to maintain consistency
            donationMaintenance.deleteDonation(donationId);
            donationMaintenance.addDonation(donation);
            System.out.println("Donation updated: " + donation);
        } else {
            System.out.println("Donation not found.");
        }
    }

    // Helper method to parse date
    private Date parseDate(String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(dateString);
        } catch (Exception e) {
            System.out.println("Invalid date format. Using current date.");
            return new Date();
        }
    }

    public static void main(String[] args) {
        DonationMaintenance donationMaintenance = new DonationMaintenance();
        DonationMaintenanceUI ui = new DonationMaintenanceUI(donationMaintenance);
        ui.displayMenu();
    }
}
