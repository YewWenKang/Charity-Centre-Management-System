package boundary;

import control.DonorMaintenance;
import entity.Donor;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DonorMaintenanceUI {
    private List<Donor> donorList;
    private Scanner scanner;
    private DonorMaintenance donorMaintenance;

    public DonorMaintenanceUI() {
        donorList = new ArrayList<>();
        scanner = new Scanner(System.in);
        donorMaintenance = new DonorMaintenance();
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
                    System.out.println("Exiting Donor Maintenance System.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    private void showMenu() {
        System.out.println("\n--- Donor Maintenance System ---");
        System.out.println("1. Add Donor");
        System.out.println("2. Update Donor");
        System.out.println("3. Delete Donor");
        System.out.println("4. View All Donors");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    private void addDonor() {
        System.out.println("\n--- Add Donor ---");
        System.out.print("Enter Donor ID: ");
        String donorId = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Contact Number: ");
        String contactNumber = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();
        System.out.print("Enter Donor Type (Government, Private, Public): ");
        String donorType = scanner.nextLine();
        System.out.print("Enter Donation Preference (Cash, Bank In, Tng): ");
        String donationPreference = scanner.nextLine();
        System.out.print("Enter Donotian Times: ");
        String donorTimes = scanner.nextLine();

        donorMaintenance.addDonor(donorId, name, contactNumber, email, address, donorType, donationPreference, donorTimes);

        System.out.println("Donor added successfully!");
    }

    private void updateDonor() {
        System.out.println("\n--- Update Donor ---");
        System.out.print("Enter Donor ID to update: ");
        String donorId = scanner.nextLine();
        Donor donor = donorMaintenance.findDonorById(donorId);

        if (donor != null) {
            System.out.print("Enter New Name (current: " + donor.getName() + "): ");
            String name = scanner.nextLine();
            System.out.print("Enter New Contact Number (current: " + donor.getContactNumber() + "): ");
            String contactNumber = scanner.nextLine();
            System.out.print("Enter New Email (current: " + donor.getEmail() + "): ");
            String email = scanner.nextLine();
            System.out.print("Enter New Address (current: " + donor.getAddress() + "): ");
            String address = scanner.nextLine();
            System.out.print("Enter New Donor Type (current: " + donor.getDonorType() + "): ");
            String donorType = scanner.nextLine();
            System.out.print("Enter New Donation Preference (current: " + donor.getDonationPreference() + "): ");
            String donationPreference = scanner.nextLine();
            System.out.print("Enter New Donotion Times (current: " + donor.getDonorTimes() + "): ");
            String donorTimes = scanner.nextLine();

            donorMaintenance.updateDonor(donorId, name, contactNumber, email, address, donorType, donationPreference, donorTimes);

            System.out.println("Donor updated successfully!");
        } else {
            System.out.println("Donor not found.");
        }
    }

    private void deleteDonor() {
        System.out.println("\n--- Delete Donor ---");
        System.out.print("Enter Donor ID to delete: ");
        String donorId = scanner.nextLine();
        donorMaintenance.deleteDonor(donorId);
    }

    private void viewAllDonors() {
        System.out.println("\n--- View All Donors ---");
        donorList = donorMaintenance.getAllDonors();
        if (donorList.isEmpty()) {
            System.out.println("No donors found.");
        } else {
            for (Donor donor : donorList) {
                System.out.println(donor);
            }
        }
    }
}
