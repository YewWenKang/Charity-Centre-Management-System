package control;

import DAO.FileDao;
import boundary.DonorMaintenanceUI;
import entity.Donor;
import java.util.ArrayList;
import java.util.List;

public class DonorMaintenance {
    private List<Donor> donorList;
    private static final String FILE_NAME = "donorData.csv";
    private final List<String> headers;
    private final FileDao<Donor> fileDao;

    public DonorMaintenance() {
        donorList = new ArrayList<>();
        headers = List.of("ID", "Name", "Contact Number", "Email", "Address", "Donor Type", "Donation Preference", "Donotion Times");
        fileDao = new FileDao<>();
        donorList = fileDao.loadDataFromCSV(FILE_NAME, this::mapRowToDonor);
    }

    private Donor mapRowToDonor(String[] row) {
        if (row.length == 8) {
            return new Donor(row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7]);
        }
        return null;
    }

    public void addDonor(String donorId, String name, String contactNumber, String email, String address, String donorType, String donationPreference, String donorTimes) {
        Donor donor = new Donor(donorId, name, contactNumber, email, address, donorType, donationPreference, donorTimes);
        donorList.add(donor);
        System.out.println("Donor added successfully.");
        saveDonorsToCSV();
    }

    public boolean updateDonor(String donorId, String name, String contactNumber, String email, String address,
            String donorType, String donationPreference, String donorTimes) {
        Donor donor = findDonorById(donorId);
        if (donor != null) {
            donor.setName(name);
            donor.setContactNumber(contactNumber);
            donor.setEmail(email);
            donor.setAddress(address);
            donor.setDonorType(donorType);
            donor.setDonationPreference(donationPreference);
            donor.setDonorTimes(donorTimes);
            System.out.println("Donor updated successfully.");
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
            System.out.println("Donor deleted successfully.");
            saveDonorsToCSV();
            return true;
        } else {
            System.out.println("Donor not found.");
            return false;
        }
    }

    public List<Donor> getAllDonors() {
        return new ArrayList<>(donorList);
    }

    public Donor findDonorById(String donorId) {
        for (Donor donor : donorList) {
            if (donor.getDonorId().equals(donorId)) {
                return donor;
            }
        }
        return null;
    }

    private void saveDonorsToCSV() {
        try {
            fileDao.writeDataToCSV(FILE_NAME, headers, donorList, this::mapDonorToRow);
        } catch (Exception e) {
            e.printStackTrace();
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
                donor.getDonorTimes()
        );
    }

    public static void main(String[] args) {
        DonorMaintenanceUI ui = new DonorMaintenanceUI();
        ui.start();
    }
}
