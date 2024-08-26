package control;

import boundary.DonorMaintenanceUI;
import boundary.MainUI;
import java.text.ParseException;
import utility.MessageUI;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) throws ParseException {
        MainUI mainUI = new MainUI();
        int choice = getValidChoice();
        while (choice != 0) {
            switch (choice) {
                case 0:
                    MessageUI.displayExitMessage();
                    break;
                case 1:
                    DoneeMaintenance doneeMaintenance = new DoneeMaintenance();
                    doneeMaintenance.runDoneeMaintenance();
                    break;
                case 2:
                    DonorMaintenanceUI donorUI = new DonorMaintenanceUI();
                    donorUI.start();
                    break;
                case 3:
                    VolunteerMaintenance volunteerMaintenance = new VolunteerMaintenance();
                    volunteerMaintenance.runVolunteerMaintenance();
                    break;
                default:
                    System.out.println("Invalid choice");
            }
            choice = getValidChoice();
        }
    }
    
    private static int getValidChoice() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        while (choice < 0) {
            try {
                System.out.print("Enter your choice (0-3): ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Clear the newline
                if (choice < 0 || choice > 3) {
                    System.out.println("Choice must be between 0 and 3.");
                    choice = -1; // Reset choice to prompt user again
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
        return choice;
    }

    
}
