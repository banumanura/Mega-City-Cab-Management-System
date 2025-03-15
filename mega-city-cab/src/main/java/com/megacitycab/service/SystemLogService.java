// src/main/java/com/megacitycab/service/SystemLogService.java
package com.megacitycab.service;

import com.megacitycab.dao.SystemLogDAO;
import com.megacitycab.model.SystemLog;

import java.sql.SQLException;
import java.util.List;

public class SystemLogService {
    private SystemLogDAO systemLogDAO;
    
    public SystemLogService() {
        this.systemLogDAO = new SystemLogDAO();
    }
    
    public int addLog(SystemLog log) throws SQLException {
        return systemLogDAO.addLog(log);
    }
    
    public List<SystemLog> getAllLogs() throws SQLException {
        return systemLogDAO.getAllLogs();
    }
    
    public List<SystemLog> getLogsByEmployeeId(int employeeId) throws SQLException {
        return systemLogDAO.getLogsByEmployeeId(employeeId);
    }
}
