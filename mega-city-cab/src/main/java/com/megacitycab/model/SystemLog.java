// src/main/java/com/megacitycab/model/SystemLog.java
package com.megacitycab.model;

import java.sql.Timestamp;

public class SystemLog {
    private int logId;
    private Integer employeeId;
    private String action;
    private String details;
    private String ipAddress;
    private Timestamp timestamp;
    
    // Constructors
    public SystemLog() {}
    
    public SystemLog(int logId, Integer employeeId, String action, String details, 
                    String ipAddress, Timestamp timestamp) {
        this.logId = logId;
        this.employeeId = employeeId;
        this.action = action;
        this.details = details;
        this.ipAddress = ipAddress;
        this.timestamp = timestamp;
    }
    
    // Getters and Setters
    public int getLogId() {
        return logId;
    }
    
    public void setLogId(int logId) {
        this.logId = logId;
    }
    
    public Integer getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }
    
    public String getAction() {
        return action;
    }
    
    public void setAction(String action) {
        this.action = action;
    }
    
    public String getDetails() {
        return details;
    }
    
    public void setDetails(String details) {
        this.details = details;
    }
    
    public String getIpAddress() {
        return ipAddress;
    }
    
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    
    public Timestamp getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
    
    @Override
    public String toString() {
        return "SystemLog{" +
                "logId=" + logId +
                ", employeeId=" + employeeId +
                ", action='" + action + '\'' +
                ", details='" + details + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
