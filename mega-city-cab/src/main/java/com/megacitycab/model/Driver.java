// src/main/java/com/megacitycab/model/Driver.java (continued)
package com.megacitycab.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Driver {
    private int driverId;
    private String firstName;
    private String lastName;
    private String licenseNumber;
    private String nic;
    private String address;
    private String telephone;
    private String emergencyContact;
    private Date dateOfBirth;
    private Date joiningDate;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    // Constructors
    public Driver() {}
    
    public Driver(int driverId, String firstName, String lastName, String licenseNumber, String nic,
                 String address, String telephone, String emergencyContact, Date dateOfBirth,
                 Date joiningDate, String status, Timestamp createdAt, Timestamp updatedAt) {
        this.driverId = driverId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.licenseNumber = licenseNumber;
        this.nic = nic;
        this.address = address;
        this.telephone = telephone;
        this.emergencyContact = emergencyContact;
        this.dateOfBirth = dateOfBirth;
        this.joiningDate = joiningDate;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Getters and Setters
    public int getDriverId() {
        return driverId;
    }
    
    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getLicenseNumber() {
        return licenseNumber;
    }
    
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
    
    public String getNic() {
        return nic;
    }
    
    public void setNic(String nic) {
        this.nic = nic;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getTelephone() {
        return telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    public String getEmergencyContact() {
        return emergencyContact;
    }
    
    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }
    
    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public Date getJoiningDate() {
        return joiningDate;
    }
    
    public void setJoiningDate(Date joiningDate) {
        this.joiningDate = joiningDate;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    @Override
    public String toString() {
        return "Driver{" +
                "driverId=" + driverId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", nic='" + nic + '\'' +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
