// src/main/java/com/megacitycab/service/BookingService.java
package com.megacitycab.service;

import com.megacitycab.dao.BookingDAO;
import com.megacitycab.dao.SystemLogDAO;
import com.megacitycab.dao.VehicleDAO;
import com.megacitycab.dao.DriverDAO;
import com.megacitycab.model.Booking;
import com.megacitycab.model.SystemLog;
import com.megacitycab.model.Vehicle;
import com.megacitycab.model.Driver;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class BookingService {
    private BookingDAO bookingDAO;
    private VehicleDAO vehicleDAO;
    private DriverDAO driverDAO;
    private SystemLogDAO systemLogDAO;
    
    public BookingService() {
        this.bookingDAO = new BookingDAO();
        this.vehicleDAO = new VehicleDAO();
        this.driverDAO = new DriverDAO();
        this.systemLogDAO = new SystemLogDAO();
    }
    
    public Booking getBookingById(int id) throws SQLException {
        return bookingDAO.getBookingById(id);
    }
    
    public Booking getBookingByBookingNumber(String bookingNumber) throws SQLException {
        return bookingDAO.getBookingByBookingNumber(bookingNumber);
    }
    
    public List<Booking> getAllBookings() throws SQLException {
        return bookingDAO.getAllBookings();
    }
    
    public List<Booking> getBookingsByCustomerId(int customerId) throws SQLException {
        return bookingDAO.getBookingsByCustomerId(customerId);
    }
    
    public int addBooking(Booking booking, String ipAddress, Integer loggedInEmployeeId) throws SQLException {
        // Generate a unique booking number
        if (booking.getBookingNumber() == null || booking.getBookingNumber().isEmpty()) {
            booking.setBookingNumber("BK" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }
        
        int bookingId = bookingDAO.addBooking(booking);
        
        // Update vehicle and driver status if booking is confirmed
        if (bookingId > 0 && "CONFIRMED".equals(booking.getStatus())) {
            Vehicle vehicle = vehicleDAO.getVehicleById(booking.getVehicleId());
            if (vehicle != null) {
                vehicle.setStatus("ASSIGNED");
                vehicleDAO.updateVehicle(vehicle);
            }
            
            Driver driver = driverDAO.getDriverById(booking.getDriverId());
            if (driver != null) {
                driver.setStatus("ON_TRIP");
                driverDAO.updateDriver(driver);
            }
        }
        
        // Log the action
        if (bookingId > 0) {
            SystemLog log = new SystemLog();
            log.setEmployeeId(loggedInEmployeeId);
            log.setAction("ADD_BOOKING");
            log.setDetails("Added new booking: " + booking.getBookingNumber() + " for customer ID: " + booking.getCustomerId());
            log.setIpAddress(ipAddress);
            systemLogDAO.addLog(log);
        }
        
        return bookingId;
    }
    
    public boolean updateBooking(Booking booking, String ipAddress, Integer loggedInEmployeeId) throws SQLException {
        Booking oldBooking = bookingDAO.getBookingById(booking.getBookingId());
        boolean success = bookingDAO.updateBooking(booking);
        
        // Update vehicle and driver status based on booking status change
        if (success && oldBooking != null && !oldBooking.getStatus().equals(booking.getStatus())) {
            updateVehicleAndDriverStatus(oldBooking, booking);
        }
        
        // Log the action
        if (success) {
            SystemLog log = new SystemLog();
            log.setEmployeeId(loggedInEmployeeId);
            log.setAction("UPDATE_BOOKING");
            log.setDetails("Updated booking: " + booking.getBookingNumber() + " with ID: " + booking.getBookingId() + ", Status: " + booking.getStatus());
            log.setIpAddress(ipAddress);
            systemLogDAO.addLog(log);
        }
        
        return success;
    }
    
    public boolean deleteBooking(int id, String ipAddress, Integer loggedInEmployeeId) throws SQLException {
        Booking booking = bookingDAO.getBookingById(id);
        boolean success = bookingDAO.deleteBooking(id);
        
        // Log the action
        if (success && booking != null) {
            SystemLog log = new SystemLog();
            log.setEmployeeId(loggedInEmployeeId);
            log.setAction("DELETE_BOOKING");
            log.setDetails("Deleted booking: " + booking.getBookingNumber() + " with ID: " + id);
            log.setIpAddress(ipAddress);
            systemLogDAO.addLog(log);
        }
        
        return success;
    }
    
    private void updateVehicleAndDriverStatus(Booking oldBooking, Booking newBooking) throws SQLException {
        // Handle vehicle status
        Vehicle vehicle = vehicleDAO.getVehicleById(newBooking.getVehicleId());
        if (vehicle != null) {
            switch (newBooking.getStatus()) {
                case "CONFIRMED":
                case "IN_PROGRESS":
                    vehicle.setStatus("ASSIGNED");
                    break;
                case "COMPLETED":
                case "CANCELLED":
                    vehicle.setStatus("AVAILABLE");
                    break;
                default:
                    break;
            }
            vehicleDAO.updateVehicle(vehicle);
        }
        
        // Handle driver status
        Driver driver = driverDAO.getDriverById(newBooking.getDriverId());
        if (driver != null) {
            switch (newBooking.getStatus()) {
                case "CONFIRMED":
                case "IN_PROGRESS":
                    driver.setStatus("ON_TRIP");
                    break;
                case "COMPLETED":
                    driver.setStatus("AVAILABLE");
                    break;
                case "CANCELLED":
                    driver.setStatus("AVAILABLE");
                    break;
                default:
                    break;
            }
            driverDAO.updateDriver(driver);
        }
    }
}
