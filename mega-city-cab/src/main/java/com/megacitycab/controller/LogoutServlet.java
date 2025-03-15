// src/main/java/com/megacitycab/controller/LogoutServlet.java
package com.megacitycab.controller;

import com.megacitycab.model.Employee;
import com.megacitycab.service.SystemLogService;
import com.megacitycab.model.SystemLog;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
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
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            // Log the logout action
            Employee employee = (Employee) session.getAttribute("employee");
            if (employee != null) {
                try {
                    SystemLog log = new SystemLog();
                    log.setEmployeeId(employee.getEmployeeId());
                    log.setAction("LOGOUT");
                    log.setDetails("User logged out: " + employee.getUsername());
                    log.setIpAddress(request.getRemoteAddr());
                    systemLogService.addLog(log);
                } catch (SQLException e) {
                    // Just log the error, don't prevent logout
                    getServletContext().log("Error logging logout: " + e.getMessage());
                }
            }
            
            // Invalidate the session
            session.invalidate();
        }
        
        // Redirect to login page
        response.sendRedirect(request.getContextPath() + "/login");
    }
}
