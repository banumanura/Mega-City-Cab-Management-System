// src/main/java/com/megacitycab/controller/LoginServlet.java
package com.megacitycab.controller;

import com.megacitycab.model.Employee;
import com.megacitycab.service.EmployeeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private EmployeeService employeeService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        employeeService = new EmployeeService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Check if user is already logged in
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("employee") != null) {
            // Redirect to dashboard if already logged in
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }
        
        // Forward to login page
        request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String ipAddress = request.getRemoteAddr();
        
        try {
            Employee employee = employeeService.authenticateEmployee(username, password, ipAddress);
            
            if (employee != null) {
                // Authentication successful
                HttpSession session = request.getSession();
                session.setAttribute("employee", employee);
                session.setAttribute("employeeId", employee.getEmployeeId());
                session.setAttribute("employeeName", employee.getFullName());
                session.setAttribute("employeeRole", employee.getRole());
                
                // Redirect based on role
                String role = employee.getRole();
                if ("ADMIN".equals(role)) {
                    response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                } else if ("MANAGER".equals(role)) {
                    response.sendRedirect(request.getContextPath() + "/manager/dashboard");
                } else {
                    response.sendRedirect(request.getContextPath() + "/receptionist/dashboard");
                }
            } else {
                // Authentication failed
                request.setAttribute("errorMessage", "Invalid username or password");
                request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
        }
    }
}
