// src/main/java/com/megacitycab/model/Vehicle.java
package com.megacitycab.model;

import java.sql.Timestamp;

public class Vehicle {
    private int vehicleId;
    private String registrationNumber;
    private String model;
    private String make;
    private int year;
    private String color;
    private int seatCapacity;
    private boolean acAvailable;
    private String vehicleType;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    // Constructors
    public Vehicle() {}
    
    public Vehicle(int vehicleId, String registrationNumber, String model, String make, int year,
                  String color, int seatCapacity, boolean acAvailable, String vehicleType,
                  String status, Timestamp createdAt, Timestamp updatedAt) {
        this.vehicleId = vehicleId;
        this.registrationNumber = registrationNumber;
        this.model = model;
        this.make = make;
        this.year = year;
        this.color = color;
        this.seatCapacity = seatCapacity;
        this.acAvailable = acAvailable;
        this.vehicleType = vehicleType;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Getters and Setters
    public int getVehicleId() {
        return vehicleId;
    }
    
    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }
    
    public String getRegistrationNumber() {
        return registrationNumber;
    }
    
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
    
    public String getModel() {
        return model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    
    public String getMake() {
        return make;
    }
    
    public void setMake(String make) {
        this.make = make;
    }
    
    public int getYear() {
        return year;
    }
    
    public void setYear(int year) {
        this.year = year;
    }
    
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public int getSeatCapacity() {
        return seatCapacity;
    }
    
    public void setSeatCapacity(int seatCapacity) {
        this.seatCapacity = seatCapacity;
    }
    
    public boolean isAcAvailable() {
        return acAvailable;
    }
    
    public void setAcAvailable(boolean acAvailable) {
        this.acAvailable = acAvailable;
    }
    
    public String getVehicleType() {
        return vehicleType;
    }
    
    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
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
        return "Vehicle{" +
                "vehicleId=" + vehicleId +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", model='" + model + '\'' +
                ", make='" + make + '\'' +
                ", year=" + year +
                ", color='" + color + '\'' +
                ", seatCapacity=" + seatCapacity +
                ", acAvailable=" + acAvailable +
                ", vehicleType='" + vehicleType + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
