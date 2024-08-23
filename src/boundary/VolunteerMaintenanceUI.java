/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import java.util.Scanner;
import entity.Volunteer;

public class VolunteerMaintenanceUI {

    Scanner scanner = new Scanner(System.in);

    public int getMenuChoice() {
        System.out.println("\nVolunteer Registeriation Menu");
        System.out.println("1.Register as new Volunteer");
        System.out.println("2.Remove  existing Volunteer");
        System.out.println("3.Search volunteer");
        System.out.println("4.List all volunteers");
        System.out.println("0.Quit");
        int choice = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
        return choice;
    }
    
    public void printVolunteerDetails(Volunteer Volunteer) {
    
    System.out.println("\n Volunteer Details");
    System.out.println("Volunteer ID: " + Volunteer.getVolunteerId());
    System.out.println("Name:" + Volunteer.getName());
    System.out.println("Address: " + Volunteer.getAddress());
    System.out.println("Phone Number: " + Volunteer.getPhoneNumber());
    System.out.println("Email: " + Volunteer.getEmail());
  }

    public void listAllProducts(String products) {
        System.out.println("All Volunteers");
        System.out.println(products);
    }

    public String inputVolunteerId() {
        System.out.print("Enter your Volunteer Id: ");
        String name = scanner.nextLine();
        return name;
    }

    public String inputVolunteerName() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        return name;
    }

    public String inputVolunteerAddress() {
        System.out.print("Enter your address: ");
        String address = scanner.nextLine();
        return address;
    }

    public String inputVolunteerPhoneNumber() {
        System.out.print("Enter your phone number: ");
        String phoneNumber = scanner.nextLine();
        return phoneNumber;
    }

    public String inputVolunteerEmail() {
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        return email;
    }


    public int inputVolunteerIndex() {
        System.out.print("Enter the index of the volunteer to remove: ");
        int index = scanner.nextInt();
        scanner.nextLine();
        return index;
    }

    public int inputSearchVolunteerIndex()
    {
        System.out.print("Enter the index of the Volunteer to search: ");
        int index = scanner.nextInt();
        scanner.nextLine();
        return index;

    }

    
}
