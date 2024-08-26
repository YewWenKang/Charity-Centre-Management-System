package boundary;

import entity.Donee;
import java.util.Scanner;

public class DoneeMaintenanceUI {

    Scanner scanner = new Scanner(System.in);

    public void printBox(String message) {
        int width = message.length() + 8; // Adjust width based on message length
        String border = "-".repeat(width);
        System.out.println();
        System.out.println(border);
        System.out.println("|   " + message + "   |");
        System.out.println(border);
    }

    private void printDash(int amount) {
        String dash = "-".repeat(amount);
        System.out.print(dash);
    }

    public String getMenuChoice() {
        System.out.println();
        printDash(40);
        System.out.println("\n|              Donee Menu              |");
        printDash(40);
        System.out.println();
        System.out.println("| 1. | Register as new Donee           |");
        System.out.println("| 2. | Remove Donee                    |");
        System.out.println("| 3. | Update Current Donee Details    |");
        System.out.println("| 4. | Search Donee                    |");
        System.out.println("| 5. | Apply for Aid                   |");
        System.out.println("| 6. | Filter Donee                    |");
        System.out.println("| 7. | Generate Report                 |");
        System.out.println("| 8. | Undo Last Action                |");
        System.out.println("| 9. | Redo Last Action                |");
        System.out.println("| 0. | Quit                            |");
        printDash(40);
        System.out.println();
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();
        System.out.println();
        return choice;
    }

    public String getDonationMenuChoice() {
        System.out.println();
        printDash(38);
        System.out.println("\n|              Aid Menu              |");
        printDash(38);
        System.out.println();
        System.out.println("| 1. | Apply for food                |");
        System.out.println("| 2. | Apple for daily expenses      |");
        System.out.println("| 3. | Apply for cash                |");
        System.out.println("| 3. | Show Available Aid            |");
        System.out.println("| 0. | Quit                          |");
        printDash(40);
        System.out.println();
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();
        System.out.println();
        return choice;
    }

    public void printDoneeDetails(Donee donee) {
        printBox("Donee Details");
        System.out.printf("%-20s: %s%n", "Name", donee.getName());
        System.out.printf("%-20s: %s%n", "Address", donee.getAddress());
        System.out.printf("%-20s: %s%n", "Phone Number", donee.getPhoneNumber());
        System.out.printf("%-20s: %s%n", "Email", donee.getEmail());
        System.out.printf("%-20s: %s%n", "Donee Type", donee.getDoneeType());
        System.out.printf("%-20s: %s%n", "Organization Name", (donee.getOrganizationName() == null || donee.getOrganizationName().trim().isEmpty()) ? "N/A" : donee.getOrganizationName());
    }
    
    public String inputDoneeID() {
        System.out.print("Enter your ID: ");
        String id = scanner.nextLine();
        return id;
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

    public String askUpdateDonee() {
        System.out.print("Do you want to update donee details?(Y/N) :");
        String updateDonee = scanner.nextLine();
        return updateDonee;
    }
    
    public String inputAmount(){
        System.out.print("Enter your amount needed (RM) :");
        String amount = scanner.nextLine();
        return amount;
    }

    public void printDoneeDetailsRow(Donee donee) {
        printBox("Donee Details");

        // Define the column widths
        int idWidth = 8;
        int nameWidth = 30;
        int addressWidth = 35;
        int phoneWidth = 20;
        int emailWidth = 30;
        int doneeTypeWidth = 15;
        int orgNameWidth = 25;

        // Field headers with adjusted width
        String headers = String.format("%-" + idWidth + "s %-" + nameWidth + "s %-" + addressWidth + "s %-" + phoneWidth + "s %-" + emailWidth + "s %-" + doneeTypeWidth + "s %-" + orgNameWidth + "s",
                "ID", "Name", "Address", "Phone Number", "Email", "Donee Type", "Organization Name");
        
        // Print headers
        System.out.println(headers);
        printDash(headers.length());
        System.out.println();

//        // Print each field value, align and handle overflow
//        printField(donee.getId(), idWidth, 0);
//        printField(donee.getName(), nameWidth, idWidth);
//        printField(donee.getAddress(), addressWidth, nameWidth + idWidth);
//        printField(donee.getPhoneNumber(), phoneWidth, nameWidth + idWidth + addressWidth);
//        printField(donee.getEmail(), emailWidth, nameWidth + idWidth + addressWidth + phoneWidth);
//        printField(donee.getDoneeType(), doneeTypeWidth, nameWidth + idWidth + addressWidth + phoneWidth + emailWidth);
//        printField(
//                (donee.getOrganizationName() == null || donee.getOrganizationName().trim().isEmpty())
//                ? "N/A" : donee.getOrganizationName(),
//                orgNameWidth, nameWidth + idWidth + addressWidth + phoneWidth + emailWidth + doneeTypeWidth
//        );
//
//        System.out.println();
//        printDash(headers.length());
//        System.out.println();
    }

//    private void printField(String value, int width, String fieldName) {
//        final int maxLength = width; // Maximum length for one line
//        int start = 0;
//
//        while (start < value.length()) {
//            int end = Math.min(start + maxLength, value.length());
//            String line = value.substring(start, end);
//
//            // Print the line with proper padding to align with the rest of the fields
//            if (start == 0) {
//                System.out.printf("%" + (offset + width + 1) + "s", line);
//            } else {
//                // Subsequent lines should align with the start of the column
//                System.out.println();
//                System.out.printf("%" + (offset + 1) + "s", line);
//            }
//
//            start = end;
//            // Move to the next line if there is more data to print
//            if (start < value.length()) {
//                System.out.println();
//                // Indentation for overflow lines
//                System.out.print(" ".repeat(offset + width));
//            }
//        }
//    }
}
