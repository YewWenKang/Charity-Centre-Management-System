package boundary;

import entity.Donee;
import java.util.Scanner;

public class DoneeMaintenanceUI {

    Scanner scanner = new Scanner(System.in);

    public String getMenuChoice() {
        System.out.println("\nDonee Menu");
        System.out.println("1.Register as new Donee");
        System.out.println("2.Remove Donee");
        System.out.println("3.Update Current Donee Details");
        System.out.println("4.Search Donee");
        System.out.println("5.List Donations Receiver(Wait Donation done)");
        System.out.println("6.Filter Donee");
        System.out.println("7.Report");
        System.out.println("8.Undo last action");
        System.out.println("9.Redo last action");
        System.out.println("0.Quit");
        String choice = scanner.nextLine();
        System.out.println();
        return choice;
    }

    public void printDoneeDetails(Donee donee) {
        System.out.println("Donee Details");
        System.out.println("Name:" + donee.getName());
        System.out.println("Address: " + donee.getAddress());
        System.out.println("Phone Number: " + donee.getPhoneNumber());
        System.out.println("Email: " + donee.getEmail());
        System.out.println("Donee Type: " + donee.getDoneeType());
        System.out.println("Organization Name: " + donee.getOrganizationName());
    }

    public String inputDoneeName() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        return name;
    }

    public String inputDoneeAddress() {
        System.out.print("Enter your address: ");
        String address = scanner.nextLine();
        return address;
    }

    public String inputDoneePhoneNumber() {
        System.out.print("Enter your phone number: ");
        String phoneNumber = scanner.nextLine();
        return phoneNumber;
    }

    public String inputDoneeEmail() {
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        return email;
    }

    public String inputDoneeType() {
        System.out.print("Do you represent any organization?(Y/N)");
        String doneeType = scanner.nextLine();
        return doneeType;
    }

    public String inputDoneeOrganizationName() {
        System.out.print("Enter your organization name: ");
        String organizationName = scanner.nextLine();
        return organizationName;
    }

    public String inputRemovedDoneeName() {
        System.out.print("Enter the name of the Donee to remove: ");
        String nameToRemove = scanner.nextLine();
        return nameToRemove;
    }

    public String inputDoneeTypeFilter() {
        System.out.print("Enter the type of donee to filter (Organization/Individual), or leave blank to skip: ");
        String doneeTypeFilter = scanner.nextLine();
        return doneeTypeFilter;
    }

    public String inputDoneeIdToSearch() {
        System.out.print("Enter the ID of the Donee to search: ");
        String doneeIdToSearch = scanner.nextLine();
        return doneeIdToSearch;

    }

    public String inputDoneeAddressFilter() {
        System.out.print("Enter the location to filter by address (e.g., Kuala Lumpur), or leave blank to skip: ");
        String addressFilter = scanner.nextLine();
        return addressFilter;
    }

    public String inputDoneeIdUpdate() {
        System.out.print("Enter the ID of the Donee to update: ");
        String doneeIdUpdate = scanner.nextLine();
        return doneeIdUpdate;
    }

    public int getUpdateDoneeChoice() {
        // Display update menu
        System.out.println("Select the field to update:");
        System.out.println("1. Name");
        System.out.println("2. Address");
        System.out.println("3. Phone Number");
        System.out.println("4. Email");
        System.out.println("5. Donee Type");
        System.out.println("6. Organization Name (if applicable)");
        System.out.println("0. Finish Updating");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
        return choice;
    }
    
    public String askUpdateDonee(){
        System.out.print("Do you want to update donee details?(Y/N) :");
        String updateDonee = scanner.nextLine();
        return updateDonee;
}
    

}
