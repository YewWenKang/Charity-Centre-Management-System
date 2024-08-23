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
                System.out.println("8. Save and Exit");
                System.out.print("Enter your choice (1-8): ");
                
                int choice = Integer.parseInt(scanner.nextLine());
    
                switch (choice) {
                    case 1:
                        System.out.print("Enter New Name: ");
                        String name = scanner.nextLine();
                        donor.setName(name);
                        break;
                    case 2:
                        System.out.print("Enter New Contact Number: ");
                        String contactNumber = scanner.nextLine();
                        donor.setContactNumber(contactNumber);
                        break;
                    case 3:
                        System.out.print("Enter New Email: ");
                        String email = scanner.nextLine();
                        donor.setEmail(email);
                        break;
                    case 4:
                        System.out.print("Enter New Address: ");
                        String address = scanner.nextLine();
                        donor.setAddress(address);
                        break;
                    case 5:
                        System.out.print("Enter New Donor Type: ");
                        String donorType = scanner.nextLine();
                        donor.setDonorType(donorType);
                        break;
                    case 6:
                        System.out.print("Enter New Donation Preference: ");
                        String donationPreference = scanner.nextLine();
                        donor.setDonationPreference(donationPreference);
                        break;
                    case 7:
                        System.out.print("Enter New Donation Times: ");
                        String donorTimes = scanner.nextLine();
                        donor.setDonorTimes(donorTimes);
                        break;
                    case 8:
                        donorMaintenance.updateDonor(donorId, donor.getName(), donor.getContactNumber(), donor.getEmail(), donor.getAddress(), donor.getDonorType(), donor.getDonationPreference(), donor.getDonorTimes());
                        updating = false;
                        System.out.println("Donor updated successfully!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
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
