// src/main/java/com/megacitycab/controller/SystemLogServlet.java
package com.megacitycab.controller;

import com.megacitycab.model.SystemLog;
import com.megacitycab.service.SystemLogService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/system-log")
public class SystemLogServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private SystemLogService systemLogService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        systemLogService = new SystemLogService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // Get employee ID filter if provided
            String employeeIdParam = request.getParameter("employeeId");
            List<SystemLog> logs;
            
            if (employeeIdParam != null && !employeeIdParam.isEmpty()) {
                int employeeId = Integer.parseInt(employeeIdParam);
                logs = systemLogService.getLogsByEmployeeId(employeeId);
                request.setAttribute("filteredEmployeeId", employeeId);
            } else {
                logs = systemLogService.getAllLogs();
            }
            
            request.setAttribute("logs", logs);
            request.getRequestDispatcher("/jsp/admin/system-log.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }
}
