// src/main/java/com/megacitycab/dao/EmployeeDAO.java
package com.megacitycab.dao;

import com.megacitycab.model.Employee;
import com.megacitycab.util.DBConnection;
import com.megacitycab.util.PasswordUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    
    // Get employee by ID
    public Employee getEmployeeById(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Employee employee = null;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM employee WHERE employee_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                employee = mapResultSetToEmployee(rs);
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DBConnection.closeConnection(conn);
        }
        
        return employee;
    }
    
    // Get employee by username
    public Employee getEmployeeByUsername(String username) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Employee employee = null;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM employee WHERE username = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                employee = mapResultSetToEmployee(rs);
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DBConnection.closeConnection(conn);
        }
        
        return employee;
    }
    
    // Get all employees
    public List<Employee> getAllEmployees() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Employee> employees = new ArrayList<>();
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM employee";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Employee employee = mapResultSetToEmployee(rs);
                employees.add(employee);
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DBConnection.closeConnection(conn);
        }
        
        return employees;
    }
    
    // Add a new employee
    public int addEmployee(Employee employee) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int generatedId = -1;
        
        try {
            conn = DBConnection.getConnection();
            
            // Generate salt and hash password
            String salt = PasswordUtil.generateSalt();
            String hashedPassword = PasswordUtil.hashPassword(employee.getPassword(), salt);
            
            String sql = "INSERT INTO employee (username, password, password_salt, full_name, role, email, phone, status) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, employee.getUsername());
            stmt.setString(2, hashedPassword);
            stmt.setString(3, salt);
            stmt.setString(4, employee.getFullName());
            stmt.setString(5, employee.getRole());
            stmt.setString(6, employee.getEmail());
            stmt.setString(7, employee.getPhone());
            stmt.setString(8, employee.getStatus());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating employee failed, no rows affected.");
            }
            
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            } else {
                throw new SQLException("Creating employee failed, no ID obtained.");
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DBConnection.closeConnection(conn);
        }
        
        return generatedId;
    }
    
    // Update an existing employee
    public boolean updateEmployee(Employee employee) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE employee SET full_name = ?, role = ?, email = ?, phone = ?, status = ? " +
                         "WHERE employee_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, employee.getFullName());
            stmt.setString(2, employee.getRole());
            stmt.setString(3, employee.getEmail());
            stmt.setString(4, employee.getPhone());
            stmt.setString(5, employee.getStatus());
            stmt.setInt(6, employee.getEmployeeId());
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } finally {
            if (stmt != null) stmt.close();
            DBConnection.closeConnection(conn);
        }
    }
    
    // Update employee password
    public boolean updateEmployeePassword(int employeeId, String newPassword) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBConnection.getConnection();
            
            // Generate new salt and hash password
            String salt = PasswordUtil.generateSalt();
            String hashedPassword = PasswordUtil.hashPassword(newPassword, salt);
            
            String sql = "UPDATE employee SET password = ?, password_salt = ? WHERE employee_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, hashedPassword);
            stmt.setString(2, salt);
            stmt.setInt(3, employeeId);
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } finally {
            if (stmt != null) stmt.close();
            DBConnection.closeConnection(conn);
        }
    }
    
    // Delete an employee
    public boolean deleteEmployee(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "DELETE FROM employee WHERE employee_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } finally {
            if (stmt != null) stmt.close();
            DBConnection.closeConnection(conn);
        }
    }
    
    // Authenticate employee
    public Employee authenticateEmployee(String username, String password) throws SQLException {
        Employee employee = getEmployeeByUsername(username);
        
        if (employee != null) {
            // Get the stored salt and hash
            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;
            
            try {
                conn = DBConnection.getConnection();
                String sql = "SELECT password, password_salt FROM employee WHERE username = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, username);
                rs = stmt.executeQuery();
                
                if (rs.next()) {
                    String storedHash = rs.getString("password");
                    String storedSalt = rs.getString("password_salt");
                    
                    // Verify the password
                    if (PasswordUtil.verifyPassword(password, storedHash, storedSalt)) {
                        // Update last login time
                        updateLastLogin(employee.getEmployeeId());
                        return employee;
                    }
                }
            } finally {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                DBConnection.closeConnection(conn);
            }
        }
        
        return null; // Authentication failed
    }
    
    // Update last login time
    private void updateLastLogin(int employeeId) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE employee SET last_login = CURRENT_TIMESTAMP WHERE employee_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, employeeId);
            stmt.executeUpdate();
        } finally {
            if (stmt != null) stmt.close();
            DBConnection.closeConnection(conn);
        }
    }
    
    // Helper method to map ResultSet to Employee object
    private Employee mapResultSetToEmployee(ResultSet rs) throws SQLException {
        Employee employee = new Employee();
        employee.setEmployeeId(rs.getInt("employee_id"));
        employee.setUsername(rs.getString("username"));
        employee.setPassword(rs.getString("password")); // This is the hashed password
        employee.setFullName(rs.getString("full_name"));
        employee.setRole(rs.getString("role"));
        employee.setEmail(rs.getString("email"));
        employee.setPhone(rs.getString("phone"));
        employee.setStatus(rs.getString("status"));
        employee.setLastLogin(rs.getTimestamp("last_login"));
        employee.setCreatedAt(rs.getTimestamp("created_at"));
        employee.setUpdatedAt(rs.getTimestamp("updated_at"));
        return employee;
    }
}
