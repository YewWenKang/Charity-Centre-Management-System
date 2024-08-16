package entity;

public class Donee {
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private String doneeType;  // Individual / Organization
    private String organizationName; // Optional , Only doneeType = Organization , will need to fill in

    public Donee(String name, String address, String phoneNumber, String email, String doneeType) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.doneeType = doneeType;
        this.organizationName = "";
    }
    public Donee(String name, String address, String phoneNumber, String email, String doneeType,String organizationName) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.doneeType = doneeType;
        this.organizationName = organizationName;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDoneeType() {
        return doneeType;
    }

    public void setDoneeType(String doneeType) {
        this.doneeType = doneeType;
    }
    
    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    @Override
    public String toString() {
        return "Donee{" +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", doneeType='" + doneeType + '\'' +
                ", Organization Name='" + organizationName + '\'' +
                '}';
    }
}
