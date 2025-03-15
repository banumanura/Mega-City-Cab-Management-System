// src/main/java/com/megacitycab/model/Booking.java
package com.megacitycab.model;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.math.BigDecimal;

public class Booking {
    private int bookingId;
    private String bookingNumber;
    private int customerId;
    private String customerName; // ✅ Added field for customer name
    private int vehicleId;
    private int driverId;
    private String pickupLocation;
    private String destination;
    private Date bookingDate;
    private Time bookingTime;
    private BigDecimal estimatedDistance;
    private String specialRequests;
    private String status;
    private int createdBy;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Constructors
    public Booking() {}

    public Booking(int bookingId, String bookingNumber, int customerId, String customerName, int vehicleId, int driverId,
                   String pickupLocation, String destination, Date bookingDate, Time bookingTime,
                   BigDecimal estimatedDistance, String specialRequests, String status, int createdBy,
                   Timestamp createdAt, Timestamp updatedAt) {
        this.bookingId = bookingId;
        this.bookingNumber = bookingNumber;
        this.customerId = customerId;
        this.customerName = customerName; // ✅ Initialize customerName
        this.vehicleId = vehicleId;
        this.driverId = driverId;
        this.pickupLocation = pickupLocation;
        this.destination = destination;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.estimatedDistance = estimatedDistance;
        this.specialRequests = specialRequests;
        this.status = status;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    public String getBookingNumber() { return bookingNumber; }
    public void setBookingNumber(String bookingNumber) { this.bookingNumber = bookingNumber; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public String getCustomerName() { return customerName; } // ✅ Getter for customerName
    public void setCustomerName(String customerName) { this.customerName = customerName; } // ✅ Setter for customerName

    public int getVehicleId() { return vehicleId; }
    public void setVehicleId(int vehicleId) { this.vehicleId = vehicleId; }

    public int getDriverId() { return driverId; }
    public void setDriverId(int driverId) { this.driverId = driverId; }

    public String getPickupLocation() { return pickupLocation; }
    public void setPickupLocation(String pickupLocation) { this.pickupLocation = pickupLocation; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public Date getBookingDate() { return bookingDate; }
    public void setBookingDate(Date bookingDate) { this.bookingDate = bookingDate; }

    public Time getBookingTime() { return bookingTime; }
    public void setBookingTime(Time bookingTime) { this.bookingTime = bookingTime; }

    public BigDecimal getEstimatedDistance() { return estimatedDistance; }
    public void setEstimatedDistance(BigDecimal estimatedDistance) { this.estimatedDistance = estimatedDistance; }

    public String getSpecialRequests() { return specialRequests; }
    public void setSpecialRequests(String specialRequests) { this.specialRequests = specialRequests; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getCreatedBy() { return createdBy; }
    public void setCreatedBy(int createdBy) { this.createdBy = createdBy; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
}
