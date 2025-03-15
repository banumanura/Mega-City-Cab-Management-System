// src/main/java/com/megacitycab/service/EmployeeService.java
package com.megacitycab.service;

import com.megacitycab.dao.EmployeeDAO;
import com.megacitycab.dao.SystemLogDAO;
import com.megacitycab.model.Employee;
import com.megacitycab.model.SystemLog;

import java.sql.SQLException;
import java.util.List;

public class EmployeeService {
    private EmployeeDAO employeeDAO;
    private SystemLogDAO systemLogDAO;
    
    public EmployeeService() {
        this.employeeDAO = new EmployeeDAO();
        this.systemLogDAO = new SystemLogDAO();
    }
    
    public Employee getEmployeeById(int id) throws SQLException {
        return employeeDAO.getEmployeeById(id);
    }
    
    public Employee getEmployeeByUsername(String username) throws SQLException {
        return employeeDAO.getEmployeeByUsername(username);
    }
    
    public List<Employee> getAllEmployees() throws SQLException {
        return employeeDAO.getAllEmployees();
    }
    
    public int addEmployee(Employee employee, String ipAddress, Integer loggedInEmployeeId) throws SQLException {
        int employeeId = employeeDAO.addEmployee(employee);
        
        // Log the action
        if (employeeId > 0) {
            SystemLog log = new SystemLog();
            log.setEmployeeId(loggedInEmployeeId);
            log.setAction("ADD_EMPLOYEE");
            log.setDetails("Added new employee: " + employee.getUsername() + " with role: " + employee.getRole());
            log.setIpAddress(ipAddress);
            systemLogDAO.addLog(log);
        }
        
        return employeeId;
    }
    
    public boolean updateEmployee(Employee employee, String ipAddress, Integer loggedInEmployeeId) throws SQLException {
        boolean success = employeeDAO.updateEmployee(employee);
        
        // Log the action
        if (success) {
            SystemLog log = new SystemLog();
            log.setEmployeeId(loggedInEmployeeId);
            log.setAction("UPDATE_EMPLOYEE");
            log.setDetails("Updated employee: " + employee.getUsername() + " with ID: " + employee.getEmployeeId());
            log.setIpAddress(ipAddress);
            systemLogDAO.addLog(log);
        }
        
        return success;
    }
    
    public boolean updateEmployeePassword(int employeeId, String newPassword, String ipAddress, Integer loggedInEmployeeId) throws SQLException {
        boolean success = employeeDAO.updateEmployeePassword(employeeId, newPassword);
        
        // Log the action
        if (success) {
            SystemLog log = new SystemLog();
            log.setEmployeeId(loggedInEmployeeId);
            log.setAction("UPDATE_EMPLOYEE_PASSWORD");
            log.setDetails("Updated password for employee with ID: " + employeeId);
            log.setIpAddress(ipAddress);
            systemLogDAO.addLog(log);
        }
        
        return success;
    }
    
    public boolean deleteEmployee(int id, String ipAddress, Integer loggedInEmployeeId) throws SQLException {
        Employee employee = employeeDAO.getEmployeeById(id);
        boolean success = employeeDAO.deleteEmployee(id);
        
        // Log the action
        if (success && employee != null) {
            SystemLog log = new SystemLog();
            log.setEmployeeId(loggedInEmployeeId);
            log.setAction("DELETE_EMPLOYEE");
            log.setDetails("Deleted employee: " + employee.getUsername() + " with ID: " + id);
            log.setIpAddress(ipAddress);
            systemLogDAO.addLog(log);
        }
        
        return success;
    }
    
    public Employee authenticateEmployee(String username, String password, String ipAddress) throws SQLException {
        Employee employee = employeeDAO.authenticateEmployee(username, password);
        
        // Log the action
        if (employee != null) {
            SystemLog log = new SystemLog();
            log.setEmployeeId(employee.getEmployeeId());
            log.setAction("LOGIN");
            log.setDetails("User logged in: " + username);
            log.setIpAddress(ipAddress);
            systemLogDAO.addLog(log);
        } else {
            SystemLog log = new SystemLog();
            log.setEmployeeId(null);
            log.setAction("LOGIN_FAILED");
            log.setDetails("Failed login attempt for username: " + username);
            log.setIpAddress(ipAddress);
            systemLogDAO.addLog(log);
        }
        
        return employee;
    }
}
