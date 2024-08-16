/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import entity.Donee;
import boundary.DoneeMaintenanceUI;
import utility.MessageUI;
import ADT.LinkedList;
import ADT.ListInterface;

/**
 *
 * @author User
 */
public class DoneeMaintenance {

    private ListInterface<Donee> doneeList = new LinkedList<>();
    private DoneeMaintenanceUI doneeUI = new DoneeMaintenanceUI();

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
        }else{
            doneeType = "Individual";
        }

        return new Donee(doneeName, doneeAddress, doneePhoneNumber, doneeEmail, doneeType, doneeOrganizationName);
    }

    public void registerNewDonee() {
        Donee newDonee = inputDoneeDetails();
        doneeList.add(newDonee);
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
                    break;
                case 1:
                    registerNewDonee();
                    doneeUI.printDoneeDetails(doneeList.getEntry(doneeList.getNumberOfEntries()));
                    break;
                case 2:
                    //doneeUI.listAllProducts(getAllProducts());
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
