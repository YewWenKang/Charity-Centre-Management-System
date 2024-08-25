package boundary;
import java.util.InputMismatchException;
import java.util.Scanner;

import control.DoneeMaintenance;
import control.VolunteerMaintenance;
import utility.MessageUI;

public class MainUI {
    private Scanner scanner = new Scanner(System.in);

    public int MainUI() {
        System.out.println("\nCharity Centre Management System");
        System.out.println("1.Donor Management");
        System.out.println("2.Donee Management");
        System.out.println("3.Volunteer Management");
        System.out.println("0.Quit");
        int choice = -1;
        while (choice < 0 || choice > 3) {
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            if (choice < 0 || choice > 3) {
                System.out.println("Invalid choice. Please try again.");
            }
        }
        System.out.println();
        return choice;
    }

}
