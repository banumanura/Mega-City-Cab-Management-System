// src/main/java/com/megacitycab/service/VehicleService.java
package com.megacitycab.service;

import com.megacitycab.dao.VehicleDAO;
import com.megacitycab.dao.SystemLogDAO;
import com.megacitycab.model.Vehicle;
import com.megacitycab.model.SystemLog;

import java.sql.SQLException;
import java.util.List;

public class VehicleService {
    private VehicleDAO vehicleDAO;
    private SystemLogDAO systemLogDAO;
    
    public VehicleService() {
        this.vehicleDAO = new VehicleDAO();
        this.systemLogDAO = new SystemLogDAO();
    }
    
    public Vehicle getVehicleById(int id) throws SQLException {
        return vehicleDAO.getVehicleById(id);
    }
    
    public List<Vehicle> getAllVehicles() throws SQLException {
        return vehicleDAO.getAllVehicles();
    }
    
    public List<Vehicle> getAvailableVehicles() throws SQLException {
        return vehicleDAO.getAvailableVehicles();
    }
    
    public int addVehicle(Vehicle vehicle, String ipAddress, Integer loggedInEmployeeId) throws SQLException {
        int vehicleId = vehicleDAO.addVehicle(vehicle);
        
        // Log the action
        if (vehicleId > 0) {
            SystemLog log = new SystemLog();
            log.setEmployeeId(loggedInEmployeeId);
            log.setAction("ADD_VEHICLE");
            log.setDetails("Added new vehicle: " + vehicle.getMake() + " " + vehicle.getModel() + " with registration: " + vehicle.getRegistrationNumber());
            log.setIpAddress(ipAddress);
            systemLogDAO.addLog(log);
        }
        
        return vehicleId;
    }
    
    public boolean updateVehicle(Vehicle vehicle, String ipAddress, Integer loggedInEmployeeId) throws SQLException {
        boolean success = vehicleDAO.updateVehicle(vehicle);
        
        // Log the action
        if (success) {
            SystemLog log = new SystemLog();
            log.setEmployeeId(loggedInEmployeeId);
            log.setAction("UPDATE_VEHICLE");
            log.setDetails("Updated vehicle: " + vehicle.getMake() + " " + vehicle.getModel() + " with ID: " + vehicle.getVehicleId());
            log.setIpAddress(ipAddress);
            systemLogDAO.addLog(log);
        }
        
        return success;
    }
    
    public boolean deleteVehicle(int id, String ipAddress, Integer loggedInEmployeeId) throws SQLException {
        Vehicle vehicle = vehicleDAO.getVehicleById(id);
        boolean success = vehicleDAO.deleteVehicle(id);
        
        // Log the action
        if (success && vehicle != null) {
            SystemLog log = new SystemLog();
            log.setEmployeeId(loggedInEmployeeId);
            log.setAction("DELETE_VEHICLE");
            log.setDetails("Deleted vehicle: " + vehicle.getMake() + " " + vehicle.getModel() + " with ID: " + id);
            log.setIpAddress(ipAddress);
            systemLogDAO.addLog(log);
        }
        
        return success;
    }
}
