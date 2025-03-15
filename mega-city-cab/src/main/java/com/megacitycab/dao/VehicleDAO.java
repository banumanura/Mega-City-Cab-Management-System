// src/main/java/com/megacitycab/dao/VehicleDAO.java
package com.megacitycab.dao;

import com.megacitycab.model.Vehicle;
import com.megacitycab.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO {
    
    // Get vehicle by ID
    public Vehicle getVehicleById(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Vehicle vehicle = null;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM vehicle WHERE vehicle_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                vehicle = mapResultSetToVehicle(rs);
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DBConnection.closeConnection(conn);
        }
        
        return vehicle;
    }
    
    // Get all vehicles
    public List<Vehicle> getAllVehicles() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Vehicle> vehicles = new ArrayList<>();
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM vehicle";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Vehicle vehicle = mapResultSetToVehicle(rs);
                vehicles.add(vehicle);
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DBConnection.closeConnection(conn);
        }
        
        return vehicles;
    }
    
    // Get available vehicles
    public List<Vehicle> getAvailableVehicles() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Vehicle> vehicles = new ArrayList<>();
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM vehicle WHERE status = 'AVAILABLE'";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Vehicle vehicle = mapResultSetToVehicle(rs);
                vehicles.add(vehicle);
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DBConnection.closeConnection(conn);
        }
        
        return vehicles;
    }
    
    // Add a new vehicle
    public int addVehicle(Vehicle vehicle) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int generatedId = -1;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO vehicle (registration_number, model, make, year, color, seat_capacity, ac_available, vehicle_type, status) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, vehicle.getRegistrationNumber());
            stmt.setString(2, vehicle.getModel());
            stmt.setString(3, vehicle.getMake());
            stmt.setInt(4, vehicle.getYear());
            stmt.setString(5, vehicle.getColor());
            stmt.setInt(6, vehicle.getSeatCapacity());
            stmt.setBoolean(7, vehicle.isAcAvailable());
            stmt.setString(8, vehicle.getVehicleType());
            stmt.setString(9, vehicle.getStatus());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating vehicle failed, no rows affected.");
            }
            
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            } else {
                throw new SQLException("Creating vehicle failed, no ID obtained.");
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DBConnection.closeConnection(conn);
        }
        
        return generatedId;
    }
    
    // Update an existing vehicle
    public boolean updateVehicle(Vehicle vehicle) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE vehicle SET model = ?, make = ?, year = ?, color = ?, " +
                         "seat_capacity = ?, ac_available = ?, vehicle_type = ?, status = ? " +
                         "WHERE vehicle_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, vehicle.getModel());
            stmt.setString(2, vehicle.getMake());
            stmt.setInt(3, vehicle.getYear());
            stmt.setString(4, vehicle.getColor());
            stmt.setInt(5, vehicle.getSeatCapacity());
            stmt.setBoolean(6, vehicle.isAcAvailable());
            stmt.setString(7, vehicle.getVehicleType());
            stmt.setString(8, vehicle.getStatus());
            stmt.setInt(9, vehicle.getVehicleId());
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } finally {
            if (stmt != null) stmt.close();
            DBConnection.closeConnection(conn);
        }
    }
    
    // Delete a vehicle
    public boolean deleteVehicle(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "DELETE FROM vehicle WHERE vehicle_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } finally {
            if (stmt != null) stmt.close();
            DBConnection.closeConnection(conn);
        }
    }
    
    // Helper method to map ResultSet to Vehicle object
    private Vehicle mapResultSetToVehicle(ResultSet rs) throws SQLException {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleId(rs.getInt("vehicle_id"));
        vehicle.setRegistrationNumber(rs.getString("registration_number"));
        vehicle.setModel(rs.getString("model"));
        vehicle.setMake(rs.getString("make"));
        vehicle.setYear(rs.getInt("year"));
        vehicle.setColor(rs.getString("color"));
        vehicle.setSeatCapacity(rs.getInt("seat_capacity"));
        vehicle.setAcAvailable(rs.getBoolean("ac_available"));
        vehicle.setVehicleType(rs.getString("vehicle_type"));
        vehicle.setStatus(rs.getString("status"));
        vehicle.setCreatedAt(rs.getTimestamp("created_at"));
        vehicle.setUpdatedAt(rs.getTimestamp("updated_at"));
        return vehicle;
    }
}
