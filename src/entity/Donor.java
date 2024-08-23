package entity;

public class Donor {
    private String donorId;
    private String name;
    private String contactNumber;
    private String email;
    private String address;
    private String donorType; // e.g., government, private, public
    private String donationPreference; 
    private String donorTimes; 
    // Constructor
    public Donor(String donorId, String name, String contactNumber, String email, String address,
                 String donorType, String donationPreference, String donorTimes) {
        this.donorId = donorId;
        this.name = name;
        this.contactNumber = contactNumber;
        this.email = email;
        this.address = address;
        this.donorType = donorType;
        this.donationPreference = donationPreference;
        this.donorTimes = donorTimes;
    }

    // Getters and Setters

    public String getDonorId() {
        return donorId;
    }

    public void setDonorId(String donorId) {
        this.donorId = donorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDonorType() {
        return donorType;
    }

    public void setDonorType(String donorType) {
        this.donorType = donorType;
    }

    public String getDonationPreference() {
        return donationPreference;
    }

    public void setDonationPreference(String donationPreference) {
        this.donationPreference = donationPreference;
    }

    public String getDonorTimes() {
        return donorTimes;
    }

    public void setDonorTimes(String donorTimes) {
        this.donorTimes = donorTimes;
    }

    // Additional methods for managing the donor entity can be added here

    @Override
    public String toString() {
        return "Donor{" +
                "donorId='" + donorId + '\'' +
                ", name='" + name + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", donorType='" + donorType + '\'' +
                ", donationPreference='" + donationPreference + '\'' +
                ", donorTimes='" + donorTimes + '\'' +
                '}';
    }
}
