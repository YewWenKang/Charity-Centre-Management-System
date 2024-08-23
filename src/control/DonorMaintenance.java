package control;

import ADT.LinkedList;
import ADT.ListInterface;
import DAO.FileDao;
import boundary.DonorMaintenanceUI;
import entity.Donor;
import java.util.List;

public class DonorMaintenance {
    private ListInterface<Donor> donorList = new LinkedList<>();
    private static final String FILE_NAME = "donorData.csv";
    private final List<String> headers;
    private final FileDao<Donor> fileDao;

    public DonorMaintenance() {
        headers = List.of("ID", "Name", "Contact Number", "Email", "Address", "Donor Type", "Donation Preference",
                "Donation Times", "Total Amount");
        fileDao = new FileDao<>();
        donorList = fileDao.loadDataFromCSV(FILE_NAME, this::mapRowToDonor);
    }

    private Donor mapRowToDonor(String[] row) {
        if (row.length == 9) {
            return new Donor(row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7], row[8]);
        } else {
            System.out.println("Warning: Incomplete or malformed row detected, skipping: " + String.join(",", row));
        }
        return null;
    }

    public void addDonor(String donorId, String name, String contactNumber, String email, String address, String donorType, String donationPreference, String donorTimes, String totalAmount) {
        Donor donor = new Donor(donorId, name, contactNumber, email, address, donorType, donationPreference, donorTimes, totalAmount);
        donorList.add(donor);
        saveDonorsToCSV();
        System.out.println("Donor added: " + donor);
    }
    
    public boolean updateDonor(String donorId, String name, String contactNumber, String email, String address,
            String donorType, String donationPreference, String donorTimes, String totalAmount) {
        Donor donor = findDonorById(donorId);
        if (donor != null) {
            donor.setName(name);
            donor.setContactNumber(contactNumber);
            donor.setEmail(email);
            donor.setAddress(address);
            donor.setDonorType(donorType);
            donor.setDonationPreference(donationPreference);
            donor.setDonorTimes(donorTimes);
            donor.setTotalAmount(totalAmount);
            saveDonorsToCSV();
            return true;
        } else {
            System.out.println("Donor not found.");
            return false;
        }
    }

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

    public ListInterface<Donor> getAllDonors() {
        return donorList;
    }

    public Donor findDonorById(String donorId) {
        for (int i = 0; i < donorList.size(); i++) {
            Donor donor = donorList.get(i);
            if (donor != null && donor.getDonorId().equals(donorId)) {
                return donor;
            }
        }
        return null;
    }

    private void saveDonorsToCSV() {
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
    
        } catch (Exception e) {
            e.printStackTrace();  // Log the error stack trace for debugging
            System.out.println("Error saving donors to CSV: " + e.getMessage());
        }
    }
    
    private List<String> mapDonorToRow(Donor donor) {
        return List.of(
                donor.getDonorId(),
                donor.getName(),
                donor.getContactNumber(),
                donor.getEmail(),
                donor.getAddress(),
                donor.getDonorType(),
                donor.getDonationPreference(),
                donor.getDonorTimes(),
                donor.getTotalAmount());
    }


    public static void main(String[] args) {
        DonorMaintenanceUI ui = new DonorMaintenanceUI();
        ui.start();
    }
}
