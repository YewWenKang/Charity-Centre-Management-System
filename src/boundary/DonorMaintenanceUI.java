package boundary;

import ADT.ListInterface;
import control.DonorMaintenance;
import entity.Donor;
import java.util.Scanner;
import utility.ValidationUI;

public class DonorMaintenanceUI {
    private Scanner scanner;
    private DonorMaintenance donorMaintenance;

    public DonorMaintenanceUI() {
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
        String donorId, name, contactNumber, email, address, donorType, donationPreference, donorTimes, totalAmount;

        while (true) {
            System.out.print("Enter Donor ID (format DAxxx where xxx is digits): ");
            donorId = scanner.nextLine();
            if (ValidationUI.isNotEmpty(donorId) && donorId.matches("^DA\\d{3}$")) break;
            System.out.println("Invalid Donor ID format. It should start with 'DA' followed by three digits.");
            if (!retryOrExit()) return;
        }

        while (true) {
            System.out.print("Enter Name: ");
            name = scanner.nextLine();
            if (ValidationUI.isNotEmpty(name)) break;
            System.out.println("Name cannot be empty.");
            if (!retryOrExit()) return;
        }

        while (true) {
            System.out.print("Enter Contact Number (format 01xxxxxxxxx): ");
            contactNumber = scanner.nextLine();
            if (ValidationUI.isValidPhoneNumber(contactNumber)) break;
            System.out.println("Invalid contact number format. It should start with '01' and be 10 or 11 digits long.");
            if (!retryOrExit()) return;
        }

        while (true) {
            System.out.print("Enter Email: ");
            email = scanner.nextLine();
            if (ValidationUI.isValidEmail(email)) break;
            System.out.println("Invalid email format.");
            if (!retryOrExit()) return;
        }

        while (true) {
            System.out.print("Enter Address: ");
            address = scanner.nextLine();
            if (ValidationUI.isNotEmpty(address)) break;
            System.out.println("Address cannot be empty.");
            if (!retryOrExit()) return;
        }

        while (true) {
            System.out.print("Enter Donor Type (Government, Private, Public): ");
            donorType = scanner.nextLine();
            if (ValidationUI.isNotEmpty(donorType)) break;
            System.out.println("Donor Type cannot be empty.");
            if (!retryOrExit()) return;
        }

        while (true) {
            System.out.print("Enter Donation Preference (Cash, Bank In, Tng): ");
            donationPreference = scanner.nextLine();
            if (ValidationUI.isNotEmpty(donationPreference)) break;
            System.out.println("Donation Preference cannot be empty.");
            if (!retryOrExit()) return;
        }

        while (true) {
            System.out.print("Enter Donation Times: ");
            donorTimes = scanner.nextLine();
            if (ValidationUI.isNotEmpty(donorTimes)) break;
            System.out.println("Donation Times cannot be empty.");
            if (!retryOrExit()) return;
        }

        while (true) {
            System.out.print("Enter Total Amount: ");
            totalAmount = scanner.nextLine();
            if (ValidationUI.isNotEmpty(totalAmount)) break;
            System.out.println("Total Amount cannot be empty.");
            if (!retryOrExit()) return;
        }

        donorMaintenance.addDonor(donorId, name, contactNumber, email, address, donorType, donationPreference, donorTimes, totalAmount);
        System.out.println("Donor added successfully!");
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
                System.out.println(String.format("6. %-20s : %s", "Donation Preference", donor.getDonationPreference()));
                System.out.println(String.format("7. %-20s : %s", "Donation Times", donor.getDonorTimes()));
                System.out.println(String.format("8. %-20s : %s", "Total Amount", donor.getTotalAmount()));
                System.out.println("9. Save changes and return");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
    
                switch (choice) {
                    case 1:
                        System.out.print("Enter new Name: ");
                        donor.setName(scanner.nextLine());
                        break;
                    case 2:
                        System.out.print("Enter new Contact Number: ");
                        donor.setContactNumber(scanner.nextLine());
                        break;
                    case 3:
                        System.out.print("Enter new Email: ");
                        donor.setEmail(scanner.nextLine());
                        break;
                    case 4:
                        System.out.print("Enter new Address: ");
                        donor.setAddress(scanner.nextLine());
                        break;
                    case 5:
                        System.out.print("Enter new Donor Type: ");
                        donor.setDonorType(scanner.nextLine());
                        break;
                    case 6:
                        System.out.print("Enter new Donation Preference: ");
                        donor.setDonationPreference(scanner.nextLine());
                        break;
                    case 7:
                        System.out.print("Enter new Donation Times: ");
                        donor.setDonorTimes(scanner.nextLine());
                        break;
                    case 8:
                        System.out.print("Enter new Total Amount: ");
                        donor.setTotalAmount(scanner.nextLine());
                        break;
                    case 9:
                        updating = false;
                        break;
                    default:
                        System.out.println("Invalid choice, please try again.");
                }
            }
    
            donorMaintenance.updateDonor(donor.getDonorId(), donor.getName(), donor.getContactNumber(), donor.getEmail(), donor.getAddress(),
                    donor.getDonorType(), donor.getDonationPreference(), donor.getDonorTimes(), donor.getTotalAmount());
            System.out.println("Donor updated successfully.");
        } else {
            System.out.println("Donor not found.");
        }
    }

    private void deleteDonor() {
        System.out.print("Enter Donor ID to delete: ");
        String donorId = scanner.nextLine();
        if (donorMaintenance.deleteDonor(donorId)) {
            System.out.println("Donor deleted successfully.");
        } else {
            System.out.println("Donor not found.");
        }
    }

    private void viewAllDonors() {
        System.out.println("\n--- All Donors ---");
        ListInterface<Donor> donors = donorMaintenance.getAllDonors();
        if (donors.size() > 0) {
            for (int i = 0; i < donors.size(); i++) {
                System.out.println(donors.get(i).toString());
            }
        } else {
            System.out.println("No donors found.");
        }
    }

    private boolean retryOrExit() {
        System.out.print("Would you like to try again? (yes/no): ");
        String choice = scanner.nextLine().trim().toLowerCase();
        return choice.equals("yes");
    }
}
