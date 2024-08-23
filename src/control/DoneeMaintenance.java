/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import ADT.LinkedList;
import ADT.ListInterface;
import boundary.DoneeMaintenanceUI;
import entity.Donee;
import utility.MessageUI;
import utility.SaveFile;

/**
 *
 * @author User
 */
public class DoneeMaintenance {

    private ListInterface<Donee> doneeList = new LinkedList<>();
    private DoneeMaintenanceUI doneeUI = new DoneeMaintenanceUI();
    private static final String FILE_NAME = "doneeData.csv";
    private final String headers = "ID,Name,Address,Phone Number,Email,Donee Type,Organization Name";

    public DoneeMaintenance() {
        loadDoneeData();
    }

    // Method to load donee data from file
    private void loadDoneeData() {
        SaveFile.loadFromFile(FILE_NAME, doneeList, line -> {
            String[] parts = line.split(","); // Delimiter is comma
            
            String id = parts[0]; 
            String name = parts[1];
            String address = parts[2];
            String phoneNumber = parts[3];
            String email =parts[4];
            String doneeType = parts[5];
            String organizationName = parts.length > 6 ? parts[6] : "";
            

            return new Donee(id, name, address, phoneNumber, email, doneeType, organizationName);
        });
    }

    public void saveDoneeData() {
        SaveFile.saveToFile(FILE_NAME, doneeList, headers);
    }

    public Donee inputDoneeDetails() {

        String doneeName = doneeUI.inputDoneeName();
        String doneeAddress = doneeUI.inputDoneeAddress();
        String doneePhoneNumber = doneeUI.inputDoneePhoneNumber();
        String doneeEmail = doneeUI.inputDoneeEmail();
        String doneeType = doneeUI.inputDoneeType();

        String doneeOrganizationName = "";
        if (doneeType.equalsIgnoreCase("Y")) {
            doneeType = "Organization";
            doneeOrganizationName = doneeUI.inputDoneeOrganizationName();
        } else {
            doneeType = "Individual";
            doneeOrganizationName = "";
        }

        return new Donee(String.valueOf(doneeList.getNumberOfEntries() + 1), doneeName, doneeAddress, doneePhoneNumber, doneeEmail, doneeType, doneeOrganizationName);
    }

    //Choice 1
    public void registerNewDonee() {
        Donee newDonee = inputDoneeDetails();
        doneeList.add(newDonee);
    }

    //Choice 2
    public void removeDonee() {
        String nameToRemove = doneeUI.inputRemovedDoneeName();

        boolean removed = false;
        for (int i = 1; i <= doneeList.getNumberOfEntries(); i++) {
            Donee donee = doneeList.getEntry(i);
            if (donee.getName().equalsIgnoreCase(nameToRemove)) {
                doneeList.remove(i);
                removed = true;
                System.out.println("Donee " + nameToRemove + " has been removed.");
                break;
            }
        }

        if (!removed) {
            System.out.println("Donee " + nameToRemove + " not found.");
        }
    }

    //Choice 3
    public void updateDoneeDetails() {
        System.out.print("Enter the name of the Donee to update: ");
        String nameToUpdate = doneeUI.inputDoneeName();

        for (int i = 1; i <= doneeList.getNumberOfEntries(); i++) {
            Donee donee = doneeList.getEntry(i);
            if (donee.getName().equalsIgnoreCase(nameToUpdate)) {
                System.out.println("Updating details for Donee " + nameToUpdate);
                Donee updatedDonee = inputDoneeDetails();
                doneeList.replace(i, updatedDonee);
                System.out.println("Donee " + nameToUpdate + " has been updated.");
                return;
            }
        }

        System.out.println("Donee " + nameToUpdate + " not found.");
    }

    //choice 4
    public void searchDoneeDetails() {
        System.out.print("Enter the name of the Donee to search: ");
        String nameToSearch = doneeUI.inputDoneeName();

        for (int i = 1; i <= doneeList.getNumberOfEntries(); i++) {
            Donee donee = doneeList.getEntry(i);
            if (donee.getName().equalsIgnoreCase(nameToSearch)) {
                doneeUI.printDoneeDetails(donee);
                return;
            }
        }

        System.out.println("Donee " + nameToSearch + " not found.");
    }

    //choice 5
//    public void listDoneesWithDonations() {
//        for (int i = 1; i <= doneeList.getNumberOfEntries(); i++) {
//            Donee donee = doneeList.getEntry(i);
//            System.out.println("Donee: " + donee.getName());
//            // Assuming a method getDonations() that returns a list of donations for the donee
//            donee.getDonations().forEach(donation -> System.out.println("\tDonation: " + donation));
//        }
//    }
    //choice 6
    public void filterDonees() {
        System.out.print("Enter the type of donee to filter (Organization/Individual): ");
        String type = doneeUI.inputDoneeType();

        for (int i = 1; i <= doneeList.getNumberOfEntries(); i++) {
            Donee donee = doneeList.getEntry(i);
            if (donee.getDoneeType().equalsIgnoreCase(type)) {
                doneeUI.printDoneeDetails(donee);
            }
        }
    }

    //choice 7
    public void generateSummaryReport() {
        int totalDonees = doneeList.getNumberOfEntries();
        int organizationCount = 0;
        int individualCount = 0;

        for (int i = 1; i <= doneeList.getNumberOfEntries(); i++) {
            Donee donee = doneeList.getEntry(i);
            if (donee.getDoneeType().equalsIgnoreCase("Organization")) {
                organizationCount++;
            } else if (donee.getDoneeType().equalsIgnoreCase("Individual")) {
                individualCount++;
            }
        }

        System.out.println("Summary Report:");
        System.out.println("Total Donees: " + totalDonees);
        System.out.println("Total Organizations: " + organizationCount);
        System.out.println("Total Individuals: " + individualCount);
    }

    public String getAllDonees() {
        String outputStr = "";
        for (int i = 1; i <= doneeList.getNumberOfEntries(); i++) {
            outputStr += doneeList.getEntry(i) + "\n";
        }
        return outputStr;
    }

    public void runDoneeMaintenance() {
        int choice = 0;
        do {
            choice = doneeUI.getMenuChoice();
            switch (choice) {
                case 0:
                    MessageUI.displayExitMessage();
                    saveDoneeData();
                    break;
                case 1:
                    registerNewDonee();
                    doneeUI.printDoneeDetails(doneeList.getEntry(doneeList.getNumberOfEntries()));
                    break;
                case 2:
                    removeDonee();
                    break;
                case 3:
                    System.out.println(getAllDonees());
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice != 0);
    }

    public static void main(String[] args) {
        DoneeMaintenance doneeMaintenance = new DoneeMaintenance();
        doneeMaintenance.runDoneeMaintenance();
    }

}
