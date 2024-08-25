package control;

import ADT.DictionaryInterface;
import ADT.HashedDictionary;
import ADT.LinkedList;
import ADT.ListInterface;
import ADT.TreeMapImplementation;
import ADT.TreeMapInterface;
import DAO.FileDao;
import entity.Donation;
import entity.Donor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

public class DonationMaintenance {
    private DictionaryInterface<String, ListInterface<Donation>> donationHashMap;
    private TreeMapInterface<Date, ListInterface<Donation>> donationTreeMap;
    private ListInterface<Donation> donationLinkedList;
    private FileDao fileDao;
    private static final String DONATION_CSV_PATH = "Donation.csv";
    private static int donationCounter = 0; // Counter for generating sequential donation IDs
    private DonorMaintenance donorMaintenance = new DonorMaintenance();

    // Constructor
    public DonationMaintenance() {
        donationHashMap = new HashedDictionary<>();
        donationTreeMap = new TreeMapImplementation<>();
        donationLinkedList = new LinkedList<>();
        createDonationCSV(); // Check for CSV file creation
    }

    // Method to create Donation CSV file with headers if it doesn't exist
    private void createDonationCSV() {
        File file = new File(DONATION_CSV_PATH);
        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.append("Donation ID,Donor ID,Amount,Date,Payment Method,Receipt Number,Donation Type,Notes\n");
                writer.flush();
                System.out.println("Created Donation.csv with headers.");
            } catch (IOException e) {
                System.out.println("Error creating Donation.csv: " + e.getMessage());
            }
        }
    }

    // Method to generate sequential donation ID (DOxxx)
    public String generateDonationId() {
        donationCounter++;
        return String.format("DO%03d", donationCounter);
    }

    // Method to generate random receipt number (RNxxx)
    public String generateReceiptNumber() {
        Random random = new Random();
        int number = random.nextInt(1000); // Generate a number between 0 and 999
        return String.format("RN%03d", number);
    }

    // Method to add a donation
    public void addDonation(Donation donation) {
        // Add donation to the HashMap
        ListInterface<Donation> donations = donationHashMap.getValue(donation.getDonationId());
        if (donations == null) {
            donations = new LinkedList<>();
            donationHashMap.add(donation.getDonationId(), donations);
        }
        donations.add(donation);
    
        // Add donation to the TreeMap
        Date date = donation.getDate();
        if (!donationTreeMap.containsKey(date)) {
            donationTreeMap.put(date, new LinkedList<>());
        }
        donationTreeMap.get(date).add(donation);
    
        // Add donation to the LinkedList
        donationLinkedList.add(donation);
    
        // Write the donation to the CSV file
        ListInterface<String> headers = new LinkedList<>();
        headers.add("Donation ID");
        headers.add("Donor ID");
        headers.add("Amount");
        headers.add("Date");
        headers.add("Payment Method");
        headers.add("Receipt Number");
        headers.add("Donation Type");
        headers.add("Notes");
    
        ListInterface<Donation> donationList = new LinkedList<>();
        donationList.add(donation);
    
        try {
            writeDataToCSV(DONATION_CSV_PATH, headers, donationList, this::mapDonationToRow);
        } catch (IOException e) {
            System.out.println("Error writing to CSV file: " + e.getMessage());
        }
    
        // Update donor's total amount
        updateDonorTotalAmount(donation.getDonorId(), donation.getAmount());
    }
    

    // Method to update donor's total amount
    private void updateDonorTotalAmount(String donorId, double donationAmount) {
        // Load the existing donors from CSV
        FileDao<Donor> fileDao = new FileDao<>();
        ListInterface<Donor> donors = fileDao.loadDataFromCSV("donorData.csv", this::mapRowToDonor);
    
        // Find the donor with the given donorId
        Donor donor = donors.stream()
                .filter(d -> d.getDonorId().equals(donorId))
                .findFirst()
                .orElse(null);
    
        if (donor != null) {
            // Update the total amount
            double currentTotalAmount = Double.parseDouble(donor.getTotalAmount());
            double newTotalAmount = currentTotalAmount + donationAmount;
            donor.setTotalAmount(String.format("%.2f", newTotalAmount));
    
            ListInterface<String> headers = new LinkedList<>();
            headers.add("ID");
            headers.add("Name");
            headers.add("Contact Number");
            headers.add("Email");
            headers.add("Address");
            headers.add("Donor Type");
            headers.add("Donation Preference");
            headers.add("Donation Times");
            headers.add("Total Amount(RM)");
   
            fileDao.writeDataToCSV("donorData.csv", headers, donors, this::mapDonorToRow);
        } else {
            System.out.println("Donor with ID " + donorId + " not found.");
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
    
    

    // Method to map a Donation object to a CSV row
    private ListInterface<String> mapDonationToRow(Donation donation) {
        ListInterface<String> row = new LinkedList<>();
        row.add(donation.getDonationId());
        row.add(donation.getDonorId());
        row.add(String.format("%.2f", donation.getAmount()));
        row.add(new SimpleDateFormat("yyyy-MM-dd").format(donation.getDate()));
        row.add(donation.getPaymentMethod());
        row.add(donation.getReceiptNumber());
        row.add(donation.getDonationType() != null ? donation.getDonationType().toString() : "");
        row.add(donation.getNotes());
        return row;
    }
    
    

    // Method to write data to a CSV file
    public <T> void writeDataToCSV(String fileName, ListInterface<String> headers, ListInterface<T> data, Function<T, ListInterface<String>> mapper) throws IOException {
        File file = new File(fileName);
        boolean append = file.exists(); // Append if file already exists
    
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, append))) {
            if (!append) {
                // Write headers if creating a new file
                writer.write(String.join(",", headers));
                writer.newLine();
            }
    
            // Write data rows
            for (T item : data) {
                ListInterface<String> row = mapper.apply(item);
                writer.write(String.join(",", row));
                writer.newLine();
            }
        }
    }
    
    

    // Method to retrieve donation by ID
    public ListInterface<Donation> getDonationsById(String donationId) {
        // Retrieve the list of donations for the given ID
        return donationHashMap.getValue(donationId);
    }
    

    // Method to find donations by Date Range
    public ListInterface<Donation> getDonationsByDateRange(Date startDate, Date endDate) {
        ListInterface<Donation> result = new LinkedList<>();
        for (Map.Entry<Date, ListInterface<Donation>> entry : donationTreeMap.entries()) {
            Date date = entry.getKey();
            if ((date.after(startDate) || date.equals(startDate)) &&
                    (date.before(endDate) || date.equals(endDate))) {
                result.addAll(entry.getValue());
            }
        }
        return result;
    }

    // Method to delete a donation
    public void deleteDonation(String donationId) {
        ListInterface<Donation> donations = donationHashMap.getValue(donationId);
        if (donations != null) {
            for (Donation donation : donations) {
                Date date = donation.getDate();
                ListInterface<Donation> dateDonations = donationTreeMap.get(date);
                if (dateDonations != null) {
                    dateDonations.remove(donation);
                    if (dateDonations.isEmpty()) {
                        donationTreeMap.remove(date);
                    }
                }
                donationLinkedList.remove(donation);
            }
            donationHashMap.remove(donationId);
        }
    }

    public void clearAllDonations() {
        donationHashMap.clear(); // Clear all entries in HashMap
        donationTreeMap.clear(); // Clear all entries in TreeMap
        donationLinkedList.clear(); // Clear all entries in LinkedList
    
        // Optionally, you can also clear the CSV file or keep it unchanged
        clearDonationCSV(); // This method will clear the CSV file, if needed
    }
    
    private void clearDonationCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DONATION_CSV_PATH, false))) {
            // Write headers
            ListInterface<String> headers = new LinkedList<>();
            headers.add("Donation ID");
            headers.add("Donor ID");
            headers.add("Amount");
            headers.add("Date");
            headers.add("Payment Method");
            headers.add("Receipt Number");
            headers.add("Donation Type");
            headers.add("Notes");
            writer.write(String.join(",", headers));
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error clearing Donation.csv: " + e.getMessage());
        }
    }
    

    
    // Method to display donations (for testing)
    public void displayDonations() {
        System.out.println("Donations in HashMap:");
        for (String id : donationHashMap.getKeys()) {
            System.out.println(donationHashMap.getValue(id));
        }

        System.out.println("Donations in TreeMap (by Date):");
        for (Map.Entry<Date, ListInterface<Donation>> entry : donationTreeMap.entries()) {
            System.out.println("Date: " + entry.getKey());
            for (Donation donation : entry.getValue()) {
                System.out.println(donation);
            }
        }

        System.out.println("Donations in LinkedList (by Insertion Order):");
        for (Donation donation : donationLinkedList) {
            System.out.println(donation);
        }
    }

    // Method to load donor IDs from a CSV file
    public void loadDonorIds(String filePath) {
        FileDao<Donor> fileDao = new FileDao<>();
        ListInterface<Donor> donors = fileDao.loadDataFromCSV(filePath, this::mapRowToDonor);

        for (Donor donor : donors) {
            System.out.println("Donor ID: " + donor.getDonorId());
        }
    }

    // Mapping function to convert a CSV row to a Donor object
    private Donor mapRowToDonor(String[] values) {
        if (values.length < 9) {
            throw new IllegalArgumentException("CSV row does not contain enough values to map to Donor.");
        }

        String donorId = values[0];
        String name = values[1];
        String contactNumber = values[2];
        String email = values[3];
        String address = values[4];
        String donorType = values[5];
        String donationPreference = values[6];
        String donorTimes = values[7];
        String totalAmount = values[8];

        return new Donor(donorId, name, contactNumber, email, address, donorType, donationPreference, donorTimes,
                totalAmount);
    }

    // Helper method to parse date
    private Date parseDate(String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(dateString);
        } catch (Exception e) {
            System.out.println("Invalid date format. Using current date.");
            return new Date();
        }
    }

    // Method to check if donor ID exists in CSV
    public static boolean donorIdExistsInCSV(String donorId, String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length > 0 && fields[0].trim().equals(donorId)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the CSV file: " + e.getMessage());
        }
        return false;
    }
}
