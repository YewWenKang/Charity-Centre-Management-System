package control;

import ADT.LinkedList;
import ADT.ListInterface;
import DAO.FileDao;
import boundary.DonorMaintenanceUI;
import entity.Donor;
import java.util.Scanner;
import java.util.function.Predicate;
import utility.ValidationUI;

public class DonorMaintenance {
    private ListInterface<Donor> donorList = new LinkedList<>();
    private static final String FILE_NAME = "donorData.csv";
    private final ListInterface<String> headers;
    private final FileDao<Donor> fileDao;
    private final Scanner scanner = new Scanner(System.in);

    public DonorMaintenance() {
        headers = new LinkedList<>();
        headers.add("ID");
        headers.add("Name");
        headers.add("Contact Number");
        headers.add("Email");
        headers.add("Address");
        headers.add("Donor Type");
        headers.add("Donation Preference");
        headers.add("Donation Times");
        headers.add("Total Amount(RM)");

        fileDao = new FileDao<>();
        donorList = fileDao.loadDataFromCSV(FILE_NAME, this::mapRowToDonor);
    }

    // Map CSV row to Donor entity
    private Donor mapRowToDonor(String[] row) {
        if (row.length == 9) {
            return new Donor(row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7], row[8]);
        } else {
            System.out.println("Warning: Incomplete or malformed row detected, skipping: " + String.join(",", row));
        }
        return null;
    }

    // Add a new donor and save to CSV
    public void addDonor(String donorId, String name, String contactNumber, String email, String address,
            String donorType, String donationPreference, String donorTimes, String totalAmount) {
        Donor donor = new Donor(donorId, name, contactNumber, email, address, donorType, donationPreference, donorTimes,
                totalAmount);
        donorList.add(donor);
        saveDonorsToCSV();
        System.out.println("Donor added: " + donor);
    }

    // update donor
    public boolean updateDonor(String donorId, String name, String contactNumber, String email,
            String address, String donorType, String donationPreference,
            String donorTimes, String totalAmount) {
        Donor donor = findDonorById(donorId);
        if (donor == null) {
            return false; // Donor not found
        }

        // Update donor details
        donor.setName(name);
        donor.setContactNumber(contactNumber);
        donor.setEmail(email);
        donor.setAddress(address);
        donor.setDonorType(donorType);
        donor.setDonationPreference(donationPreference);
        donor.setDonorTimes(donorTimes);
        donor.setTotalAmount(totalAmount);

        // Save changes
        return saveDonorsToCSV();
    }

    // Helper method for validated input
    private String getValidatedInput(String prompt, Predicate<String> validator, String errorMessage) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            if (ValidationUI.isNotEmpty(input) && validator.test(input)) {
                return input;
            }

            System.out.println(errorMessage);
            if (!ValidationUI.retryOrExit()) {
                return null; // Indicate cancellation
            }
        }
    }

    // Delete a donor by ID and save changes to CSV
    public boolean deleteDonor(String donorId) {
        Donor donor = findDonorById(donorId);
        if (donor != null) {
            donorList.remove(donor);
            saveDonorsToCSV();
            return true;
        } else {
            System.out.println("Donor not found.");
            return false;
        }
    }

    // Get all donors
    public ListInterface<Donor> getAllDonors() {
        return donorList;
    }

    // Find a donor by ID
    public Donor findDonorById(String donorId) {
        return donorList.stream()
                .filter(donor -> donor != null && donor.getDonorId().equals(donorId))
                .findFirst()
                .orElse(null);
    }

    public Donor findDonorByName(String name) {
        return donorList.stream()
                .filter(donor -> donor != null && donor.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    // Save all donors to CSV
    private boolean saveDonorsToCSV() {
        try {
            ListInterface<Donor> validDonors = new LinkedList<>();
            for (int i = 0; i < donorList.size(); i++) {
                Donor donor = donorList.get(i);
                if (donor != null) {
                    validDonors.add(donor);
                } else {
                    System.out.println("Warning: Null donor found at index " + i);
                }
            }
            fileDao.writeDataToCSV(FILE_NAME, headers, validDonors, this::mapDonorToRow);
            return true;
        } catch (Exception e) {
            e.printStackTrace(); // Log the error stack trace for debugging
            System.out.println("Error saving donors to CSV: " + e.getMessage());
            return false;
        }
    }

    // Map Donor entity to CSV row
    private ListInterface<String> mapDonorToRow(Donor donor) {
        ListInterface<String> row = new LinkedList<>();
        row.add(donor.getDonorId());
        row.add(donor.getName());
        row.add(donor.getContactNumber());
        row.add(donor.getEmail());
        row.add(donor.getAddress());
        row.add(donor.getDonorType());
        row.add(donor.getDonationPreference());
        row.add(donor.getDonorTimes());
        row.add(donor.getTotalAmount());
        return row;
    }

    // Main method to start the UI
    public static void main(String[] args) {
        DonorMaintenanceUI ui = new DonorMaintenanceUI();
        ui.start();
    }
}
