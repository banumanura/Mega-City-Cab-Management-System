// src/main/java/com/megacitycab/dao/DriverDAO.java
package com.megacitycab.dao;

import com.megacitycab.model.Driver;
import com.megacitycab.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DriverDAO {
    
    // Get driver by ID
    public Driver getDriverById(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Driver driver = null;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM driver WHERE driver_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                driver = mapResultSetToDriver(rs);
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DBConnection.closeConnection(conn);
        }
        
        return driver;
    }
    
    // Get all drivers
    public List<Driver> getAllDrivers() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Driver> drivers = new ArrayList<>();
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM driver";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Driver driver = mapResultSetToDriver(rs);
                drivers.add(driver);
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DBConnection.closeConnection(conn);
        }
        
        return drivers;
    }
    
    // Get available drivers
    public List<Driver> getAvailableDrivers() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Driver> drivers = new ArrayList<>();
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM driver WHERE status = 'AVAILABLE'";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Driver driver = mapResultSetToDriver(rs);
                drivers.add(driver);
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DBConnection.closeConnection(conn);
        }
        
        return drivers;
    }
    
    // Add a new driver
    public int addDriver(Driver driver) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int generatedId = -1;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO driver (first_name, last_name, license_number, nic, address, " +
                         "telephone, emergency_contact, date_of_birth, joining_date, status) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, driver.getFirstName());
            stmt.setString(2, driver.getLastName());
            stmt.setString(3, driver.getLicenseNumber());
            stmt.setString(4, driver.getNic());
            stmt.setString(5, driver.getAddress());
            stmt.setString(6, driver.getTelephone());
            stmt.setString(7, driver.getEmergencyContact());
            stmt.setDate(8, driver.getDateOfBirth());
            stmt.setDate(9, driver.getJoiningDate());
            stmt.setString(10, driver.getStatus());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating driver failed, no rows affected.");
            }
            
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            } else {
                throw new SQLException("Creating driver failed, no ID obtained.");
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DBConnection.closeConnection(conn);
        }
        
        return generatedId;
    }
    
    // Update an existing driver
    public boolean updateDriver(Driver driver) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE driver SET first_name = ?, last_name = ?, license_number = ?, " +
                         "nic = ?, address = ?, telephone = ?, emergency_contact = ?, " +
                         "date_of_birth = ?, joining_date = ?, status = ? WHERE driver_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, driver.getFirstName());
            stmt.setString(2, driver.getLastName());
            stmt.setString(3, driver.getLicenseNumber());
            stmt.setString(4, driver.getNic());
            stmt.setString(5, driver.getAddress());
            stmt.setString(6, driver.getTelephone());
            stmt.setString(7, driver.getEmergencyContact());
            stmt.setDate(8, driver.getDateOfBirth());
            stmt.setDate(9, driver.getJoiningDate());
            stmt.setString(10, driver.getStatus());
            stmt.setInt(11, driver.getDriverId());
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } finally {
            if (stmt != null) stmt.close();
            DBConnection.closeConnection(conn);
        }
    }
    
    // Delete a driver
    public boolean deleteDriver(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "DELETE FROM driver WHERE driver_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } finally {
            if (stmt != null) stmt.close();
            DBConnection.closeConnection(conn);
        }
    }
    
    // Helper method to map ResultSet to Driver object
    private Driver mapResultSetToDriver(ResultSet rs) throws SQLException {
        Driver driver = new Driver();
        driver.setDriverId(rs.getInt("driver_id"));
        driver.setFirstName(rs.getString("first_name"));
        driver.setLastName(rs.getString("last_name"));
        driver.setLicenseNumber(rs.getString("license_number"));
        driver.setNic(rs.getString("nic"));
        driver.setAddress(rs.getString("address"));
        driver.setTelephone(rs.getString("telephone"));
        driver.setEmergencyContact(rs.getString("emergency_contact"));
        driver.setDateOfBirth(rs.getDate("date_of_birth"));
        driver.setJoiningDate(rs.getDate("joining_date"));
        driver.setStatus(rs.getString("status"));
        driver.setCreatedAt(rs.getTimestamp("created_at"));
        driver.setUpdatedAt(rs.getTimestamp("updated_at"));
        return driver;
    }
}
