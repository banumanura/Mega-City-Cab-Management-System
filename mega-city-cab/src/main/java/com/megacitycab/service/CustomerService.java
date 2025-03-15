// src/main/java/com/megacitycab/service/CustomerService.java
package com.megacitycab.service;

import com.megacitycab.dao.CustomerDAO;
import com.megacitycab.dao.SystemLogDAO;
import com.megacitycab.model.Customer;
import com.megacitycab.model.SystemLog;

import java.sql.SQLException;
import java.util.List;

public class CustomerService {
    private CustomerDAO customerDAO;
    private SystemLogDAO systemLogDAO;

    public CustomerService() {
        this.customerDAO = new CustomerDAO();
        this.systemLogDAO = new SystemLogDAO();
    }

    public Customer getCustomerById(int id) throws SQLException {
        return customerDAO.getCustomerById(id);
    }

    public Customer getCustomerByRegistrationNumber(String registrationNumber) throws SQLException {
        return customerDAO.getCustomerByRegistrationNumber(registrationNumber);
    }

    public List<Customer> getAllCustomers() throws SQLException {
        return customerDAO.getAllCustomers();
    }

    public int addCustomer(Customer customer, String ipAddress, Integer loggedInEmployeeId) throws SQLException {
        int customerId = customerDAO.addCustomer(customer);

        // Log the action
        if (customerId > 0) {
            SystemLog log = new SystemLog();
            log.setEmployeeId(loggedInEmployeeId);
            log.setAction("ADD_CUSTOMER");
            log.setDetails("Added new customer: " + customer.getFirstName() + " " + customer.getLastName());
            log.setIpAddress(ipAddress);
            systemLogDAO.addLog(log);
        }

        return customerId;
    }

    public boolean updateCustomer(Customer customer, String ipAddress, Integer loggedInEmployeeId) throws SQLException {
        boolean success = customerDAO.updateCustomer(customer);

        // Log the action
        if (success) {
            SystemLog log = new SystemLog();
            log.setEmployeeId(loggedInEmployeeId);
            log.setAction("UPDATE_CUSTOMER");
            log.setDetails("Updated customer: " + customer.getFirstName() + " " + customer.getLastName() + " with ID: " + customer.getCustomerId());
            log.setIpAddress(ipAddress);
            systemLogDAO.addLog(log);
        }

        return success;
    }

    public boolean deleteCustomer(int id, String ipAddress, Integer loggedInEmployeeId) throws SQLException {
        Customer customer = customerDAO.getCustomerById(id);
        boolean success = customerDAO.deleteCustomer(id);

        // Log the action
        if (success && customer != null) {
            SystemLog log = new SystemLog();
            log.setEmployeeId(loggedInEmployeeId);
            log.setAction("DELETE_CUSTOMER");
            log.setDetails("Deleted customer: " + customer.getFirstName() + " " + customer.getLastName() + " with ID: " + id);
            log.setIpAddress(ipAddress);
            systemLogDAO.addLog(log);
        }

        return success;
    }
}