// src/main/java/com/megacitycab/dao/BillDAO.java
package com.megacitycab.dao;

import com.megacitycab.model.Bill;
import com.megacitycab.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {
    
    // Get bill by ID
    public Bill getBillById(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Bill bill = null;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM bill WHERE bill_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                bill = mapResultSetToBill(rs);
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DBConnection.closeConnection(conn);
        }
        
        return bill;
    }
    
    // Get bill by bill number
    public Bill getBillByBillNumber(String billNumber) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Bill bill = null;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM bill WHERE bill_number = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, billNumber);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                bill = mapResultSetToBill(rs);
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DBConnection.closeConnection(conn);
        }
        
        return bill;
    }
    
    // Get bill by booking ID
    public Bill getBillByBookingId(int bookingId) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Bill bill = null;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM bill WHERE booking_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bookingId);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                bill = mapResultSetToBill(rs);
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DBConnection.closeConnection(conn);
        }
        
        return bill;
    }
    
    // Get all bills
    public List<Bill> getAllBills() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Bill> bills = new ArrayList<>();
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM bill ORDER BY generated_at DESC";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Bill bill = mapResultSetToBill(rs);
                bills.add(bill);
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DBConnection.closeConnection(conn);
        }
        
        return bills;
    }
    
    // Add a new bill
    public int addBill(Bill bill) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int generatedId = -1;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO bill (bill_number, booking_id, base_amount, distance_charge, " +
                         "waiting_charge, night_surcharge, tax_amount, discount_amount, discount_reason, " +
                         "total_amount, payment_method, payment_status, generated_by) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, bill.getBillNumber());
            stmt.setInt(2, bill.getBookingId());
            stmt.setBigDecimal(3, bill.getBaseAmount());
            stmt.setBigDecimal(4, bill.getDistanceCharge());
            stmt.setBigDecimal(5, bill.getWaitingCharge());
            stmt.setBigDecimal(6, bill.getNightSurcharge());
            stmt.setBigDecimal(7, bill.getTaxAmount());
            stmt.setBigDecimal(8, bill.getDiscountAmount());
            stmt.setString(9, bill.getDiscountReason());
            stmt.setBigDecimal(10, bill.getTotalAmount());
            stmt.setString(11, bill.getPaymentMethod());
            stmt.setString(12, bill.getPaymentStatus());
            stmt.setInt(13, bill.getGeneratedBy());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating bill failed, no rows affected.");
            }
            
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            } else {
                throw new SQLException("Creating bill failed, no ID obtained.");
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DBConnection.closeConnection(conn);
        }
        
        return generatedId;
    }
    
    // Update an existing bill
    public boolean updateBill(Bill bill) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE bill SET base_amount = ?, distance_charge = ?, waiting_charge = ?, " +
                         "night_surcharge = ?, tax_amount = ?, discount_amount = ?, discount_reason = ?, " +
                         "total_amount = ?, payment_method = ?, payment_status = ? " +
                         "WHERE bill_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setBigDecimal(1, bill.getBaseAmount());
            stmt.setBigDecimal(2, bill.getDistanceCharge());
            stmt.setBigDecimal(3, bill.getWaitingCharge());
            stmt.setBigDecimal(4, bill.getNightSurcharge());
            stmt.setBigDecimal(5, bill.getTaxAmount());
            stmt.setBigDecimal(6, bill.getDiscountAmount());
            stmt.setString(7, bill.getDiscountReason());
            stmt.setBigDecimal(8, bill.getTotalAmount());
            stmt.setString(9, bill.getPaymentMethod());
            stmt.setString(10, bill.getPaymentStatus());
            stmt.setInt(11, bill.getBillId());
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } finally {
            if (stmt != null) stmt.close();
            DBConnection.closeConnection(conn);
        }
    }
    
    // Delete a bill
    public boolean deleteBill(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "DELETE FROM bill WHERE bill_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } finally {
            if (stmt != null) stmt.close();
            DBConnection.closeConnection(conn);
        }
    }
    
    // Helper method to map ResultSet to Bill object
    private Bill mapResultSetToBill(ResultSet rs) throws SQLException {
        Bill bill = new Bill();
        bill.setBillId(rs.getInt("bill_id"));
        bill.setBillNumber(rs.getString("bill_number"));
        bill.setBookingId(rs.getInt("booking_id"));
        bill.setBaseAmount(rs.getBigDecimal("base_amount"));
        bill.setDistanceCharge(rs.getBigDecimal("distance_charge"));
        bill.setWaitingCharge(rs.getBigDecimal("waiting_charge"));
        bill.setNightSurcharge(rs.getBigDecimal("night_surcharge"));
        bill.setTaxAmount(rs.getBigDecimal("tax_amount"));
        bill.setDiscountAmount(rs.getBigDecimal("discount_amount"));
        bill.setDiscountReason(rs.getString("discount_reason"));
        bill.setTotalAmount(rs.getBigDecimal("total_amount"));
        bill.setPaymentMethod(rs.getString("payment_method"));
        bill.setPaymentStatus(rs.getString("payment_status"));
        bill.setGeneratedBy(rs.getInt("generated_by"));
        bill.setGeneratedAt(rs.getTimestamp("generated_at"));
        bill.setUpdatedAt(rs.getTimestamp("updated_at"));
        return bill;
    }
}
