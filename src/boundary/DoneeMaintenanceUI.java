/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import entity.Donee;
import java.util.Scanner;

public class DoneeMaintenanceUI {

    Scanner scanner = new Scanner(System.in);

    public int getMenuChoice() {
        System.out.println("\nDonee Menu");
        System.out.println("1.Register as new Donee");
        System.out.println("2.Remove Donee");
        System.out.println("3.Update Current Donee Details");
        System.out.println("4.Search Donee");
        System.out.println("5.List Donations Receiver");
        System.out.println("6.Filter Donee");
        System.out.println("7.Report");
        System.out.println("0.Quit");
        int choice = scanner.nextInt();
        scanner.nextLine();
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
    
    public String inputRemovedDoneeName(){
        System.out.print("Enter the name of the Donee to remove: ");
        String nameToRemove = scanner.nextLine();
        return nameToRemove;
    }

    
}
