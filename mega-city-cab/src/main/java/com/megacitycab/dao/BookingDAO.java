// src/main/java/com/megacitycab/dao/BookingDAO.java
package com.megacitycab.dao;

import com.megacitycab.model.Booking;
import com.megacitycab.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    // Add a new booking
    public int addBooking(Booking booking) throws SQLException {
        String sql = "INSERT INTO booking (booking_number, customer_id, vehicle_id, driver_id, pickup_location, " +
                     "destination, booking_date, booking_time, estimated_distance, special_requests, status, created_by) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, booking.getBookingNumber());
            stmt.setInt(2, booking.getCustomerId());
            stmt.setInt(3, booking.getVehicleId());
            stmt.setInt(4, booking.getDriverId());
            stmt.setString(5, booking.getPickupLocation());
            stmt.setString(6, booking.getDestination());
            stmt.setDate(7, booking.getBookingDate());
            stmt.setTime(8, booking.getBookingTime());
            stmt.setBigDecimal(9, booking.getEstimatedDistance());
            stmt.setString(10, booking.getSpecialRequests());
            stmt.setString(11, booking.getStatus());
            stmt.setInt(12, booking.getCreatedBy());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating booking failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating booking failed, no ID obtained.");
                }
            }
        }
    }

    // Get all bookings
    public List<Booking> getAllBookings() throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT b.*, c.first_name, c.last_name FROM booking b " +
                     "JOIN customer c ON b.customer_id = c.customer_id ORDER BY b.booking_date DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Booking booking = mapResultSetToBooking(rs);
                booking.setCustomerName(rs.getString("first_name") + " " + rs.getString("last_name")); // ✅ Set customerName
                bookings.add(booking);
            }
        }
        return bookings;
    }

    // Get all bookings by a customer
    public List<Booking> getBookingsByCustomerId(int customerId) throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT b.*, c.first_name, c.last_name FROM booking b " +
                     "JOIN customer c ON b.customer_id = c.customer_id WHERE b.customer_id = ? " +
                     "ORDER BY b.booking_date DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Booking booking = mapResultSetToBooking(rs);
                    booking.setCustomerName(rs.getString("first_name") + " " + rs.getString("last_name")); // ✅ Set customerName
                    bookings.add(booking);
                }
            }
        }
        return bookings;
    }

    // Get a booking by ID
    public Booking getBookingById(int id) throws SQLException {
        String sql = "SELECT * FROM booking WHERE booking_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToBooking(rs);
                }
            }
        }
        return null;
    }

    // Get a booking by booking number
    public Booking getBookingByBookingNumber(String bookingNumber) throws SQLException {
        String sql = "SELECT * FROM booking WHERE booking_number = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, bookingNumber);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToBooking(rs);
                }
            }
        }
        return null;
    }

    // Update an existing booking
    public boolean updateBooking(Booking booking) throws SQLException {
        String sql = "UPDATE booking SET customer_id = ?, vehicle_id = ?, driver_id = ?, pickup_location = ?, " +
                     "destination = ?, booking_date = ?, booking_time = ?, estimated_distance = ?, special_requests = ?, " +
                     "status = ?, created_by = ? WHERE booking_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, booking.getCustomerId());
            stmt.setInt(2, booking.getVehicleId());
            stmt.setInt(3, booking.getDriverId());
            stmt.setString(4, booking.getPickupLocation());
            stmt.setString(5, booking.getDestination());
            stmt.setDate(6, booking.getBookingDate());
            stmt.setTime(7, booking.getBookingTime());
            stmt.setBigDecimal(8, booking.getEstimatedDistance());
            stmt.setString(9, booking.getSpecialRequests());
            stmt.setString(10, booking.getStatus());
            stmt.setInt(11, booking.getCreatedBy());
            stmt.setInt(12, booking.getBookingId());

            return stmt.executeUpdate() > 0;
        }
    }

    // Delete a booking by ID
    public boolean deleteBooking(int id) throws SQLException {
        String sql = "DELETE FROM booking WHERE booking_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    // Helper method to map ResultSet to Booking object
    private Booking mapResultSetToBooking(ResultSet rs) throws SQLException {
        Booking booking = new Booking();
        booking.setBookingId(rs.getInt("booking_id"));
        booking.setBookingNumber(rs.getString("booking_number"));
        booking.setCustomerId(rs.getInt("customer_id"));
        booking.setVehicleId(rs.getInt("vehicle_id"));
        booking.setDriverId(rs.getInt("driver_id"));
        booking.setPickupLocation(rs.getString("pickup_location"));
        booking.setDestination(rs.getString("destination"));
        booking.setBookingDate(rs.getDate("booking_date"));
        booking.setBookingTime(rs.getTime("booking_time"));
        booking.setEstimatedDistance(rs.getBigDecimal("estimated_distance"));
        booking.setSpecialRequests(rs.getString("special_requests"));
        booking.setStatus(rs.getString("status"));
        booking.setCreatedBy(rs.getInt("created_by"));
        return booking;
    }
}
