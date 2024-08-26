/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import java.util.Scanner;
import java.util.regex.Pattern;
import ADT.ListInterface;
import entity.Volunteer;
import entity.Event; // Add this import statement
import entity.Volunteer;
import entity.Event;

public class VolunteerMaintenanceUI {

    private Scanner scanner = new Scanner(System.in);

    public int getMenuChoice() {
        int choice = -1; // Initialize to an invalid choice

        while (choice < 0 || choice > 7) {
            System.out.println("=====================================");
            System.out.println("       Volunteer Registration Menu   ");
            System.out.println("=====================================");
            System.out.println("| 1. Register as New Volunteer       |");
            System.out.println("| 2. Remove Existing Volunteer       |");
            System.out.println("| 3. Search Volunteer                |");
            System.out.println("| 4. Filter Volunteers               |");
            System.out.println("| 5. List All Volunteers             |");
            System.out.println("| 6. Add Volunteer to Event          |");
            System.out.println("| 7. Search Volunteer from Event     |");
            System.out.println("| 0. Quit                            |");
            System.out.println("=====================================");
            System.out.print("Please enter your choice: ");
            
            String input = scanner.nextLine(); // Read input as a string
            
            if (isNumeric(input)) {
                choice = Integer.parseInt(input);

                if (choice < 0 || choice > 7) {
                    System.out.println("Invalid choice. Please enter a number between 0 and 7.");
                }
            } else {
                System.out.println("Invalid input. Please enter a numeric value between 0 and 7.");
            }
        }

        return choice;
    }

    // Helper method to check if a string is numeric
    private boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void printVolunteerDetails(Volunteer Volunteer) {

        System.out.println("=====================================");
        System.out.println("           Volunteer Details         ");
        System.out.println("=====================================");
        System.out.println("Volunteer ID  : " + Volunteer.getVolunteerId());
        System.out.println("Name          : " + Volunteer.getName());
        System.out.println("Phone Number  : " + Volunteer.getPhoneNumber());
        System.out.println("Email         : " + Volunteer.getEmail());
        System.out.println("Address       : " + Volunteer.getAddress());
        System.out.println("===================================== \n");
    }

    public void listAllProducts(String products) {
        System.out.println("All Volunteers");
        System.out.println(products);
    }

    public String inputVolunteerId(ListInterface<Volunteer> volunteerList) {
        String volunteerId;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter your Volunteer Id: ");
            volunteerId = scanner.nextLine();

            if (!volunteerId.startsWith("V")) {
                System.out.println("Invalid Volunteer Id. Please enter an Id starting with 'V'.");
            } else {
                boolean exists = false;
                for (int i = 1; i <= volunteerList.getNumberOfEntries(); i++) {
                    Volunteer volunteer = volunteerList.getEntry(i);
                    if (volunteer != null && volunteer.getVolunteerId().equals(volunteerId)) {
                        exists = true;
                        break;
                    }
                }

                if (exists) {
                    System.out.println("Volunteer Id already exists. Please enter a different Id.");
                } else {
                    break;
                }
            }
        }

        return volunteerId;
    }

    public String inputVolunteerName() {
        String name;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter your name: ");
            name = scanner.nextLine().trim(); // Read input and remove leading/trailing whitespace

            // Regular expression to match only alphabetic characters and spaces
            if (name.matches("[a-zA-Z]+([\\s][a-zA-Z]+)*")) {
                break; // Exit loop if name is valid
            } else {
                System.out.println("Invalid name. Please enter only alphabetic characters and spaces.");
            }
        }

        return name;
    }

    public String inputVolunteerAddress() {
        String address;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter your address: ");
            address = scanner.nextLine().trim(); // Read input and remove leading/trailing whitespace

            // Basic validation: ensure address is not empty and has at least some
            // alphanumeric characters
            if (address.matches("[a-zA-Z0-9\\s,.'-]+")) {
                break; // Exit loop if address is valid
            } else {
                System.out.println("Invalid address. Please enter a valid address.");
            }
        }

        return address;
    }

    public String inputVolunteerPhoneNumber(ListInterface<Volunteer> volunteerList) {
        String phoneNumber;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter your phone number: ");
            phoneNumber = scanner.nextLine().trim(); // Remove leading/trailing spaces

            // Validate that the phone number only contains digits
            if (!phoneNumber.matches("\\d+")) {
                System.out.println("Invalid phone number. Please enter digits only.");
                continue; // Restart the loop if validation fails
            }

            // Check for duplicates
            boolean isDuplicate = false;
            for (int i = 1; i <= volunteerList.getNumberOfEntries(); i++) {
                Volunteer existingVolunteer = volunteerList.getEntry(i);
                if (existingVolunteer != null && existingVolunteer.getPhoneNumber().equals(phoneNumber)) {
                    isDuplicate = true;
                    break;
                }
            }

            if (isDuplicate) {
                System.out.println("This phone number already exists. Please enter a different phone number.");
            } else {
                break; // No duplicates, valid phone number
            }
        }

        return phoneNumber;
    }

    public String inputVolunteerEmail(ListInterface<Volunteer> volunteerList) {
        String email;
        Scanner scanner = new Scanner(System.in);

        // Regular expression pattern for valid email format
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern emailPattern = Pattern.compile(emailRegex);

        while (true) {
            System.out.print("Enter your email: ");
            email = scanner.nextLine().trim();

            // Validate the email format
            if (!emailPattern.matcher(email).matches()) {
                System.out.println("Invalid email format. Please enter a valid email.");
                continue; // Restart the loop if validation fails
            }

            // Check for duplicates in the volunteer list
            boolean isDuplicate = false;
            for (int i = 1; i <= volunteerList.getNumberOfEntries(); i++) {
                Volunteer existingVolunteer = volunteerList.getEntry(i);
                if (existingVolunteer != null && existingVolunteer.getEmail().equalsIgnoreCase(email)) {
                    isDuplicate = true;
                    break;
                }
            }

            if (isDuplicate) {
                System.out.println("This email already exists. Please enter a different email.");
            } else {
                break; // No duplicates and valid email format
            }
        }

        return email;
    }

    public int inputVolunteerIndex() {
        Scanner scanner = new Scanner(System.in);
        int index = -1;

        while (true) {
            System.out.print("Enter the index of the volunteer to remove: ");
            try {
                // Check if input is an integer
                index = Integer.parseInt(scanner.nextLine().trim());
                break; // Break the loop if input is valid
            } catch (NumberFormatException e) {
                // Handle invalid input
                System.out.println("Invalid input. Please enter a numeric index.");
            }
        }

        return index;
    }

    public int inputSearchVolunteerIndex() {
        Scanner scanner = new Scanner(System.in);
        int index = -1;

        while (true) {
            System.out.print("Enter the index of the volunteer to Search: ");
            try {
                // Check if input is an integer
                index = Integer.parseInt(scanner.nextLine().trim());
                break; // Break the loop if input is valid
            } catch (NumberFormatException e) {
                // Handle invalid input
                System.out.println("Invalid input. Please enter a numeric index.");
            }
        }
        return index;
    }

    public String inputVolunteerId() {
        String VolunteerId;
        while (true) {
            System.out.print("Enter your Volunteer Id: ");
            VolunteerId = scanner.nextLine();
            if (VolunteerId.startsWith("V")) {
                break;
            } else {
                System.out.println("Invalid Volunteer Id. Please enter an Id starting with 'V'.");
            }
        }
        return VolunteerId;
    }

    public static int inputRemoveChoice() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public int inputUpdatedVolunteerDetails() {

        System.out.print("Enter the index of the volunteer to Update: ");
        int index = scanner.nextInt();
        scanner.nextLine();
        return index;

    }


    public static boolean inputVolunteerExperience() {
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            System.out.print("Is the volunteer experienced? (yes/no): ");
            input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("yes") || input.equals("no")) {
                break; // Valid input, exit the loop
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }
        }

        return input.equals("yes");
    }


    public static String inputFilterChoice() {
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            System.out.print("Enter the type of volunteer (Experienced/Non-Experienced): ");
            input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("experienced") || input.equals("non-experienced")) {
                break; // Valid input, exit the loop
            } else {
                System.out.println("Invalid input. Please enter 'Experienced' or 'Non-Experienced'.");
            }
        }

        // Capitalize the first letter of each word for consistent output
        if (input.equals("experienced")) {
            return "Experienced";
        } else {
            return "Non-Experienced";
        }
    }

    public void printVolunteerList(ListInterface<Volunteer> volunteers) {
        System.out.println("=====================================");
        System.out.println("            Volunteers List          ");
        System.out.println("=====================================");

        for (int i = 0; i < volunteers.getNumberOfEntries(); i++) {
            Volunteer volunteer = volunteers.getEntry(i + 1);

            System.out.println("Volunteer ID  : " + volunteer.getVolunteerId());
            System.out.println("Name          : " + volunteer.getName());
            System.out.println("Phone Number  : " + volunteer.getPhoneNumber());
            System.out.println("Email         : " + volunteer.getEmail());
            System.out.println("Address       : " + volunteer.getAddress());
            System.out.println("-------------------------------------");
        }

        System.out.println("=====================================");
    }

    // public static String inputeventChoice() {

    // Scanner scanner = new Scanner(System.in);

    // System.out.print("Enter event choice (create/select): ");

    // String choice = scanner.nextLine();

    // return choice;

    // }

    public static String inputEventId() {
        Scanner scanner = new Scanner(System.in);
        String eventId;

        while (true) {
            System.out.print("Enter a valid event ID (e.g., E001, E002): ");
            eventId = scanner.nextLine().trim().toUpperCase();

            // Check if the input matches hardcoded valid event IDs
            if (eventId.equals("E001") || eventId.equals("E002") || eventId.equals("E003")) {
                break; // Valid event ID, exit the loop
            } else {
                System.out.println("Invalid event ID. Please enter a valid event ID.");
            }
        }

        return eventId;
    }

    public static String inputEventName() {

        // Implement the logic to prompt the user for event name and return the name.

        // For example:

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter event name: ");

        String eventName = scanner.nextLine();

        return eventName;
    }


}

