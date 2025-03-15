// src/main/java/com/megacitycab/service/DriverService.java
package com.megacitycab.service;

import com.megacitycab.dao.DriverDAO;
import com.megacitycab.dao.SystemLogDAO;
import com.megacitycab.model.Driver;
import com.megacitycab.model.SystemLog;

import java.sql.SQLException;
import java.util.List;

public class DriverService {
    private DriverDAO driverDAO;
    private SystemLogDAO systemLogDAO;
    
    public DriverService() {
        this.driverDAO = new DriverDAO();
        this.systemLogDAO = new SystemLogDAO();
    }
    
    public Driver getDriverById(int id) throws SQLException {
        return driverDAO.getDriverById(id);
    }
    
    public List<Driver> getAllDrivers() throws SQLException {
        return driverDAO.getAllDrivers();
    }
    
    public List<Driver> getAvailableDrivers() throws SQLException {
        return driverDAO.getAvailableDrivers();
    }
    
    public int addDriver(Driver driver, String ipAddress, Integer loggedInEmployeeId) throws SQLException {
        int driverId = driverDAO.addDriver(driver);
        
        // Log the action
        if (driverId > 0) {
            SystemLog log = new SystemLog();
            log.setEmployeeId(loggedInEmployeeId);
            log.setAction("ADD_DRIVER");
            log.setDetails("Added new driver: " + driver.getFirstName() + " " + driver.getLastName() + " with license: " + driver.getLicenseNumber());
            log.setIpAddress(ipAddress);
            systemLogDAO.addLog(log);
        }
        
        return driverId;
    }
    
    public boolean updateDriver(Driver driver, String ipAddress, Integer loggedInEmployeeId) throws SQLException {
        boolean success = driverDAO.updateDriver(driver);
        
        // Log the action
        if (success) {
            SystemLog log = new SystemLog();
            log.setEmployeeId(loggedInEmployeeId);
            log.setAction("UPDATE_DRIVER");
            log.setDetails("Updated driver: " + driver.getFirstName() + " " + driver.getLastName() + " with ID: " + driver.getDriverId());
            log.setIpAddress(ipAddress);
            systemLogDAO.addLog(log);
        }
        
        return success;
    }
    
    public boolean deleteDriver(int id, String ipAddress, Integer loggedInEmployeeId) throws SQLException {
        Driver driver = driverDAO.getDriverById(id);
        boolean success = driverDAO.deleteDriver(id);
        
        // Log the action
        if (success && driver != null) {
            SystemLog log = new SystemLog();
            log.setEmployeeId(loggedInEmployeeId);
            log.setAction("DELETE_DRIVER");
            log.setDetails("Deleted driver: " + driver.getFirstName() + " " + driver.getLastName() + " with ID: " + id);
            log.setIpAddress(ipAddress);
            systemLogDAO.addLog(log);
        }
        
        return success;
    }
}
