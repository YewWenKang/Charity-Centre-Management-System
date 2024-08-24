package control;

import ADT.LinkedList;
import ADT.ListInterface;
import boundary.DoneeMaintenanceUI;
import entity.Donee;
import utility.MessageUI;
import utility.SaveFile;

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
            String email = parts[4];
            String doneeType = parts[5];
            String organizationName = parts.length > 6 ? parts[6] : "";

            return new Donee(id, name, address, phoneNumber, email, doneeType, organizationName);
        });
    }

    public void saveDoneeData() {
        SaveFile.saveToFile(FILE_NAME, doneeList, headers);
    }

    public Donee inputDoneeDetails() {
        String doneeId = generateDoneeId();
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

        return new Donee(doneeId, doneeName, doneeAddress, doneePhoneNumber, doneeEmail, doneeType, doneeOrganizationName);
    }

    private String generateDoneeId() {
        int size = doneeList.getNumberOfEntries();
        // ID prefix
        String prefix = "DE";
        // Numeric part, zero-padded to 3 digits
        String numericPart = String.format("%03d", size + 1);
        return prefix + numericPart;
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
        // Display all donees
        System.out.println("List of Donees:");
        for (int i = 1; i <= doneeList.getNumberOfEntries(); i++) {
            Donee donee = doneeList.getEntry(i);
            System.out.println(donee.getId() + ": " + donee.getName());
        }


        String idToUpdate = doneeUI.inputDoneeIdUpdate();

        // Search for the donee with the specified ID
        for (int i = 1; i <= doneeList.getNumberOfEntries(); i++) {
            Donee donee = doneeList.getEntry(i);
            if (donee.getId().equalsIgnoreCase(idToUpdate)) {
                boolean keepUpdating = true;
                while (keepUpdating) {
                    int choice = doneeUI.getUpdateDoneeChoice();

                    switch (choice) {
                        case 1:
                            String updatedName = doneeUI.inputDoneeName();
                            donee.setName(updatedName);
                            System.out.println("Name updated successfully.");
                            break;
                        case 2:
                            String updatedAddress = doneeUI.inputDoneeAddress();
                            donee.setAddress(updatedAddress);
                            System.out.println("Address updated successfully.");
                            break;
                        case 3:
                            String updatedPhoneNumber = doneeUI.inputDoneePhoneNumber();
                            donee.setPhoneNumber(updatedPhoneNumber);
                            System.out.println("Phone number updated successfully.");
                            break;
                        case 4:
                            String updatedEmail = doneeUI.inputDoneeEmail();
                            donee.setEmail(updatedEmail);
                            System.out.println("Email updated successfully.");
                            break;
                        case 5:
                            String updatedDoneeType = doneeUI.inputDoneeType();
                            if (updatedDoneeType.equalsIgnoreCase("Y")) {
                                donee.setDoneeType("Organization");
                                System.out.println("Donee type updated to Organization.");
                            } else {
                                donee.setDoneeType("Individual");
                                donee.setOrganizationName(""); // Clear organization name if changing to Individual
                                System.out.println("Donee type updated to Individual.");
                            }
                            break;
                        case 6:
                            if (donee.getDoneeType().equalsIgnoreCase("Organization")) {
                                String updatedOrganizationName = doneeUI.inputDoneeOrganizationName();
                                donee.setOrganizationName(updatedOrganizationName);
                                System.out.println("Organization name updated successfully.");
                            } else {
                                System.out.println("This donee is not an organization. Cannot update Organization Name.");
                            }
                            break;
                        case 0:
                            keepUpdating = false;
                            System.out.println("Finished updating donee.");
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }
                }
                doneeList.replace(i, donee);
                return;
            }
        }

        System.out.println("Donee with ID " + idToUpdate + " not found.");
    }

    //choice 4
    public void searchDoneeDetails() {
        String idToSearch = doneeUI.inputDoneeIdToSearch();

        for (int i = 1; i <= doneeList.getNumberOfEntries(); i++) {
            Donee donee = doneeList.getEntry(i);
            if (donee.getId().equalsIgnoreCase(idToSearch)) {
                doneeUI.printDoneeDetails(donee);
                return;
            }
        }

        System.out.println("Donee with ID " + idToSearch + " not found.");
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
        String type = doneeUI.inputDoneeTypeFilter();
        String location = doneeUI.inputDoneeAddressFilter();

        for (int i = 1; i <= doneeList.getNumberOfEntries(); i++) {
            Donee donee = doneeList.getEntry(i);
            boolean matchesType = type.isEmpty() || donee.getDoneeType().equalsIgnoreCase(type);
            boolean matchesLocation = location.isEmpty() || donee.getAddress().toLowerCase().contains(location.toLowerCase());

            if (matchesType && matchesLocation) {
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
                    updateDoneeDetails();
                    System.out.println(getAllDonees());
                    break;
                case 4:
                    searchDoneeDetails();
                    break;
                case 5:
                    break;
                case 6:
                    filterDonees();
                    break;
                case 7:
                    generateSummaryReport();
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
