// src/main/java/com/megacitycab/dao/CustomerDAO.java
package com.megacitycab.dao;

import com.megacitycab.model.Customer;
import com.megacitycab.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    // Get customer by ID
    public Customer getCustomerById(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Customer customer = null;

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM customer WHERE customer_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                customer = mapResultSetToCustomer(rs);
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DBConnection.closeConnection(conn);
        }

        return customer;
    }

    // Get customer by registration number
    public Customer getCustomerByRegistrationNumber(String registrationNumber) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Customer customer = null;

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM customer WHERE registration_number = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, registrationNumber);
            rs = stmt.executeQuery();

            if (rs.next()) {
                customer = mapResultSetToCustomer(rs);
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DBConnection.closeConnection(conn);
        }

        return customer;
    }

    // Get all customers
    public List<Customer> getAllCustomers() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Customer> customers = new ArrayList<>();

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM customer";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Customer customer = mapResultSetToCustomer(rs);
                customers.add(customer);
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DBConnection.closeConnection(conn);
        }

        return customers;
    }

    // Add a new customer
    public int addCustomer(Customer customer) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int generatedId = -1;

        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO customer (registration_number, first_name, last_name, address, city, telephone, email, nic, status, registered_by) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, customer.getRegistrationNumber());
            stmt.setString(2, customer.getFirstName());
            stmt.setString(3, customer.getLastName());
            stmt.setString(4, customer.getAddress());
            stmt.setString(5, customer.getCity());
            stmt.setString(6, customer.getTelephone());
            stmt.setString(7, customer.getEmail());
            stmt.setString(8, customer.getNic());
            stmt.setString(9, customer.getStatus());
            stmt.setInt(10, customer.getRegisteredBy());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating customer failed, no rows affected.");
            }

            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            } else {
                throw new SQLException("Creating customer failed, no ID obtained.");
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DBConnection.closeConnection(conn);
        }

        return generatedId;
    }

    // Update an existing customer
    public boolean updateCustomer(Customer customer) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE customer SET first_name = ?, last_name = ?, address = ?, city = ?, " +
                         "telephone = ?, email = ?, nic = ?, status = ? WHERE customer_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getAddress());
            stmt.setString(4, customer.getCity());
            stmt.setString(5, customer.getTelephone());
            stmt.setString(6, customer.getEmail());
            stmt.setString(7, customer.getNic());
            stmt.setString(8, customer.getStatus());
            stmt.setInt(9, customer.getCustomerId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } finally {
            if (stmt != null) stmt.close();
            DBConnection.closeConnection(conn);
        }
    }

    // Delete a customer
    public boolean deleteCustomer(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBConnection.getConnection();
            String sql = "DELETE FROM customer WHERE customer_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } finally {
            if (stmt != null) stmt.close();
            DBConnection.closeConnection(conn);
        }
    }

    // Helper method to map ResultSet to Customer object
    private Customer mapResultSetToCustomer(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setCustomerId(rs.getInt("customer_id"));
        customer.setRegistrationNumber(rs.getString("registration_number"));
        customer.setFirstName(rs.getString("first_name"));
        customer.setLastName(rs.getString("last_name"));
        customer.setAddress(rs.getString("address"));
        customer.setCity(rs.getString("city"));
        customer.setTelephone(rs.getString("telephone"));
        customer.setEmail(rs.getString("email"));
        customer.setNic(rs.getString("nic"));
        customer.setStatus(rs.getString("status"));
        customer.setRegisteredBy(rs.getInt("registered_by"));
        customer.setCreatedAt(rs.getTimestamp("created_at"));
        customer.setUpdatedAt(rs.getTimestamp("updated_at"));
        return customer;
    }
}