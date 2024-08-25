package control;

import ADT.LinkedList;
import ADT.ListInterface;
import ADT.TreeMapImplementation;
import ADT.TreeMapInterface;
import DAO.FileDao;
import boundary.DonorMaintenanceUI;
import entity.Donor;
import java.util.Comparator;
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

    private Donor mapRowToDonor(String[] row) {
        if (row.length == 9) {
            return new Donor(row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7], row[8]);
        } else {
            System.out.println("Warning: Incomplete or malformed row detected, skipping: " + String.join(",", row));
        }
        return null;
    }

    public void addDonor(String donorId, String name, String contactNumber, String email, String address,
                         String donorType, String donationPreference, String donorTimes, String totalAmount) {
        Donor donor = new Donor(donorId, name, contactNumber, email, address, donorType, donationPreference, donorTimes, totalAmount);
        donorList.add(donor);
        saveDonorsToCSV();
        System.out.println("Donor added: " + donor);
    }

    public boolean updateDonor(String donorId, String name, String contactNumber, String email,
                               String address, String donorType, String donationPreference,
                               String donorTimes, String totalAmount) {
        Donor donor = findDonorById(donorId);
        if (donor == null) {
            return false; // Donor not found
        }

        donor.setName(name);
        donor.setContactNumber(contactNumber);
        donor.setEmail(email);
        donor.setAddress(address);
        donor.setDonorType(donorType);
        donor.setDonationPreference(donationPreference);
        donor.setDonorTimes(donorTimes);
        donor.setTotalAmount(totalAmount);

        return saveDonorsToCSV();
    }

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

    public void viewAllDonors() {
        ListInterface<Donor> donorList = getAllDonors();
        if (donorList.size() == 0) {
            System.out.println("No donors found.");
        } else {
            System.out.println("\n--- Donor List ---");
            System.out.printf("%-10s %-20s %-15s %-25s %-20s %-20s %-15s %-15s%n",
                    "Donor ID", "Name", "Contact No.", "Email", "Address",
                    "Donor Type", "Donor Times", "Total Amount (RM)");
            System.out.println(
                    "---------------------------------------------------------------------------------------------"
                            + "-------------------------------------------------------------");

            for (int i = 0; i < donorList.size(); i++) {
                Donor donor = donorList.get(i);
                System.out.printf("%-10s %-20s %-15s %-25s %-20s %-20s %-15s %-15s%n",
                        donor.getDonorId(),
                        donor.getName(),
                        donor.getContactNumber(),
                        donor.getEmail(),
                        donor.getAddress(),
                        donor.getDonorType(),
                        donor.getDonorTimes(),
                        donor.getTotalAmount());
            }
        }
    }

    private ListInterface<Donor> mergeSort(ListInterface<Donor> list, Comparator<Donor> comparator) {
        if (list.size() <= 1) {
            return list;
        }

        int middle = list.size() / 2;
        ListInterface<Donor> left = new LinkedList<>();
        ListInterface<Donor> right = new LinkedList<>();

        for (int i = 0; i < middle; i++) {
            left.add(list.get(i));
        }
        for (int i = middle; i < list.size(); i++) {
            right.add(list.get(i));
        }

        left = mergeSort(left, comparator);
        right = mergeSort(right, comparator);

        return merge(left, right, comparator);
    }

    private ListInterface<Donor> merge(ListInterface<Donor> left, ListInterface<Donor> right,
                                       Comparator<Donor> comparator) {
        ListInterface<Donor> result = new LinkedList<>();

        while (!left.isEmpty() && !right.isEmpty()) {
            if (comparator.compare(left.get(0), right.get(0)) <= 0) {
                result.add(left.remove(0));
            } else {
                result.add(right.remove(0));
            }
        }

        while (!left.isEmpty()) {
            result.add(left.remove(0));
        }

        while (!right.isEmpty()) {
            result.add(right.remove(0));
        }

        return result;
    }

    public void sortByIdAscending() {
        ListInterface<Donor> sortedList = mergeSort(getAllDonors(), Comparator.comparing(Donor::getDonorId));
        updateDonorList(sortedList);
    }

    public void sortByIdDescending() {
        ListInterface<Donor> sortedList = mergeSort(getAllDonors(), Comparator.comparing(Donor::getDonorId).reversed());
        updateDonorList(sortedList);
    }

    public void sortByNameAscending() {
        ListInterface<Donor> sortedList = mergeSort(getAllDonors(), Comparator.comparing(Donor::getName));
        updateDonorList(sortedList);
    }

    public void sortByNameDescending() {
        ListInterface<Donor> sortedList = mergeSort(getAllDonors(), Comparator.comparing(Donor::getName).reversed());
        updateDonorList(sortedList);
    }

    public void sortByAmountAscending() {
        ListInterface<Donor> sortedList = mergeSort(getAllDonors(), Comparator.comparing(Donor::getTotalAmount));
        updateDonorList(sortedList);
    }

    public void sortByAmountDescending() {
        ListInterface<Donor> sortedList = mergeSort(getAllDonors(), Comparator.comparing(Donor::getTotalAmount).reversed());
        updateDonorList(sortedList);
    }

    private void updateDonorList(ListInterface<Donor> sortedList) {
        donorList.clear();
        for (int i = 0; i < sortedList.size(); i++) {
            donorList.add(sortedList.get(i));
        }
    }

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

    public Donor findDonorByEmail(String email) {
        return donorList.stream()
                .filter(donor -> donor != null && donor.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    public Donor findDonorByContactNumber(String contactNumber) {
        return donorList.stream()
                .filter(donor -> donor != null && donor.getContactNumber().equals(contactNumber))
                .findFirst()
                .orElse(null);
    }

    public TreeMapInterface<String, Donor> filterByDonorType(String donorType) {
        TreeMapInterface<String, Donor> result = new TreeMapImplementation<>();
        for (Donor donor : donorList) {
            if (donor.getDonorType().equalsIgnoreCase(donorType)) {
                result.put(donor.getDonorId(), donor);
            }
        }
        return result;
    }

    public TreeMapInterface<String, Donor> filterByDonationPreference(String donationPreference) {
        TreeMapInterface<String, Donor> result = new TreeMapImplementation<>();
        for (Donor donor : donorList) {
            if (donor.getDonationPreference().equalsIgnoreCase(donationPreference)) {
                result.put(donor.getDonorId(), donor);
            }
        }
        return result;
    }

    public TreeMapInterface<String, Donor> filterByDonationTimes(String donationTimes) {
        TreeMapInterface<String, Donor> result = new TreeMapImplementation<>();
        for (Donor donor : donorList) {
            if (donor.getDonorTimes().equalsIgnoreCase(donationTimes)) {
                result.put(donor.getDonorId(), donor);
            }
        }
        return result;
    }

    public TreeMapInterface<String, Donor> filterByTotalAmount(double minAmount, double maxAmount) {
        TreeMapInterface<String, Donor> result = new TreeMapImplementation<>();
        for (Donor donor : donorList) {
            double totalAmount;
            try {
                totalAmount = Double.parseDouble(donor.getTotalAmount());
            } catch (NumberFormatException e) {
                System.out.println("Invalid total amount for donor " + donor.getDonorId());
                continue;
            }
            if (totalAmount >= minAmount && totalAmount <= maxAmount) {
                result.put(donor.getDonorId(), donor);
            }
        }
        return result;
    }

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
            e.printStackTrace();
            System.out.println("Error saving donors to CSV: " + e.getMessage());
            return false;
        }
    }

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

    public static void main(String[] args) {
        DonorMaintenanceUI ui = new DonorMaintenanceUI();
        ui.start();
    }
}
