// src/main/java/com/megacitycab/dao/SystemLogDAO.java
package com.megacitycab.dao;

import com.megacitycab.model.SystemLog;
import com.megacitycab.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SystemLogDAO {
    
    // Add a new log entry
    public int addLog(SystemLog log) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int generatedId = -1;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO system_log (employee_id, action, details, ip_address) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            if (log.getEmployeeId() != null) {
                stmt.setInt(1, log.getEmployeeId());
            } else {
                stmt.setNull(1, Types.INTEGER);
            }
            
            stmt.setString(2, log.getAction());
            stmt.setString(3, log.getDetails());
            stmt.setString(4, log.getIpAddress());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating log entry failed, no rows affected.");
            }
            
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            } else {
                throw new SQLException("Creating log entry failed, no ID obtained.");
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DBConnection.closeConnection(conn);
        }
        
        return generatedId;
    }
    
    // Get all logs
    public List<SystemLog> getAllLogs() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<SystemLog> logs = new ArrayList<>();
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM system_log ORDER BY timestamp DESC";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                SystemLog log = mapResultSetToSystemLog(rs);
                logs.add(log);
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DBConnection.closeConnection(conn);
        }
        
        return logs;
    }
    
    // Get logs by employee ID
    public List<SystemLog> getLogsByEmployeeId(int employeeId) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<SystemLog> logs = new ArrayList<>();
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM system_log WHERE employee_id = ? ORDER BY timestamp DESC";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, employeeId);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                SystemLog log = mapResultSetToSystemLog(rs);
                logs.add(log);
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DBConnection.closeConnection(conn);
        }
        
        return logs;
    }
    
    // Helper method to map ResultSet to SystemLog object
    private SystemLog mapResultSetToSystemLog(ResultSet rs) throws SQLException {
        SystemLog log = new SystemLog();
        log.setLogId(rs.getInt("log_id"));
        
        int employeeId = rs.getInt("employee_id");
        if (!rs.wasNull()) {
            log.setEmployeeId(employeeId);
        }
        
        log.setAction(rs.getString("action"));
        log.setDetails(rs.getString("details"));
        log.setIpAddress(rs.getString("ip_address"));
        log.setTimestamp(rs.getTimestamp("timestamp"));
        return log;
    }
}
