package control;

import ADT.HashedDictionary;
import ADT.DictionaryInterface;
import ADT.TreeMapInterface;
import ADT.TreeMapImplementation;
import ADT.ListInterface;
import ADT.LinkedList;
import DAO.FileDao;
import entity.Donation;
import entity.Donor;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DonationMaintenance {
    private DictionaryInterface<String, Donation> donationHashMap;
    private TreeMapInterface<Date, ListInterface<Donation>> donationTreeMap;
    private ListInterface<Donation> donationLinkedList;
    private FileDao fileDao;
    private static final String DONATION_CSV_PATH = "Donation.csv";

    public DonationMaintenance() {
        donationHashMap = new HashedDictionary<>();
        donationTreeMap = new TreeMapImplementation<>();
        donationLinkedList = new LinkedList<Donation>();
        fileDao = new FileDao();
        createDonationCSV(); // Check for CSV file creation
    }

    // Method to create Donation CSV file with headers if it doesn't exist
    private void createDonationCSV() {
        File file = new File(DONATION_CSV_PATH);
        if (!file.exists()) {
            try {
                FileWriter writer = new FileWriter(file);
                writer.append("Donation ID,Donor ID,Amount,Date,Event ID,Payment Method,Receipt Number,Donation Type,Notes\n");
                writer.flush();
                writer.close();
                System.out.println("Created Donation.csv with headers.");
            } catch (IOException e) {
                System.out.println("Error creating Donation.csv: " + e.getMessage());
            }
        }
    }

    // Method to add a donation
    public void addDonation(Donation donation) {
        // Add donation to the HashMap using the donation ID
        donationHashMap.add(donation.getDonationId(), donation);

        // Retrieve the date of the donation
        Date date = donation.getDate();

        // If the date is not already in the TreeMap, create a new LinkedList<Donation>
        if (!donationTreeMap.containsKey(date)) {
            donationTreeMap.put(date, new LinkedList<Donation>());
        }

        // Add the donation to the list associated with the date in the TreeMap
        donationTreeMap.get(date).add(donation);

        // Add the donation to the LinkedList (keeps track of all donations in order)
        donationLinkedList.add(donation);

        // Write the donation to the CSV file
        writeDonationToCSV(donation);
    }

    // Method to write a donation to the CSV file
    private void writeDonationToCSV(Donation donation) {
        try (FileWriter writer = new FileWriter(DONATION_CSV_PATH, true)) {
            writer.append(donation.getDonationId()).append(",")
                  .append(donation.getDonorId()).append(",")
                  .append(String.valueOf(donation.getAmount())).append(",")
                  .append(new SimpleDateFormat("yyyy-MM-dd").format(donation.getDate())).append(",")
                  .append(donation.getEventId()).append(",")
                  .append(donation.getPaymentMethod()).append(",")
                  .append(donation.getReceiptNumber()).append(",")
                  .append(donation.getDonationType().name()).append(",")
                  .append(donation.getNotes()).append("\n");
            writer.flush();
        } catch (IOException e) {
            System.out.println("Error writing to Donation.csv: " + e.getMessage());
        }
    }

    // Other methods remain unchanged...

    // Method to retrieve donation by ID
    public Donation getDonationById(String donationId) {
        return donationHashMap.getValue(donationId); // Use getValue instead of get
    }

    // Method to find donations by Date Range
    public ListInterface<Donation> getDonationsByDateRange(Date startDate, Date endDate) {
        ListInterface<Donation> result = new LinkedList<Donation>();
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
        Donation donation = donationHashMap.getValue(donationId); // Use getValue instead of get
        if (donation != null) {
            donationHashMap.remove(donationId);

            Date date = donation.getDate();
            ListInterface<Donation> donations = donationTreeMap.get(date); // Use ListInterface<Donation>
            if (donations != null) {
                donations.remove(donation);
                if (donations.isEmpty()) {
                    donationTreeMap.remove(date);
                }
            }

            donationLinkedList.remove(donation);
            // Optionally, you can also write code to remove from CSV if needed
        }
    }

    // Method to display donations (for testing)
    public void displayDonations() {
        System.out.println("Donations in HashMap:");
        for (String id : donationHashMap.getKeys()) {
            System.out.println(donationHashMap.getValue(id)); // Use getValue instead of get
        }

        System.out.println("Donations in TreeMap (by Date):");
        for (Map.Entry<Date, ListInterface<Donation>> entry : donationTreeMap.entries()) { // Adjusted type here
            System.out.println("Date: " + entry.getKey());
            for (Donation donation : entry.getValue()) { // This will work because entry.getValue() is of type ListInterface<Donation>
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
        // Initialize the FileDao for Donor
        FileDao<Donor> fileDao = new FileDao<>();

        // Load the donors using the mapping function
        ListInterface<Donor> donors = fileDao.loadDataFromCSV(filePath, this::mapRowToDonor);

        // Display the donor IDs
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

        return new Donor(donorId, name, contactNumber, email, address, donorType, donationPreference, donorTimes, totalAmount);
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

    public static void main(String[] args) {
        DonationMaintenance dm = new DonationMaintenance();
        dm.displayDonations();
    }
}
