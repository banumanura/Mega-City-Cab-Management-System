// src/main/java/com/megacitycab/controller/EmployeeServlet.java
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
import java.util.List;

@WebServlet("/admin/employee/*")
public class EmployeeServlet extends HttpServlet {
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
        String pathInfo = request.getPathInfo();
        
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // List all employees
                listEmployees(request, response);
            } else if (pathInfo.equals("/add")) {
                // Show add employee form
                showAddForm(request, response);
            } else if (pathInfo.equals("/edit")) {
                // Show edit employee form
                showEditForm(request, response);
            } else if (pathInfo.equals("/view")) {
                // View employee details
                viewEmployee(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // Default action
                response.sendRedirect(request.getContextPath() + "/admin/employee");
            } else if (pathInfo.equals("/add")) {
                // Add a new employee
                addEmployee(request, response);
            } else if (pathInfo.equals("/edit")) {
                // Update an existing employee
                updateEmployee(request, response);
            } else if (pathInfo.equals("/delete")) {
                // Delete an employee
                deleteEmployee(request, response);
            } else if (pathInfo.equals("/change-password")) {
                // Change employee password
                changePassword(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }
    
    private void listEmployees(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        List<Employee> employees = employeeService.getAllEmployees();
        request.setAttribute("employees", employees);
        request.getRequestDispatcher("/jsp/admin/employee/list.jsp").forward(request, response);
    }
    
    private void showAddForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/admin/employee/add.jsp").forward(request, response);
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Employee employee = employeeService.getEmployeeById(id);
        request.setAttribute("employee", employee);
        request.getRequestDispatcher("/jsp/admin/employee/edit.jsp").forward(request, response);
    }
    
    private void viewEmployee(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Employee employee = employeeService.getEmployeeById(id);
        request.setAttribute("employee", employee);
        request.getRequestDispatcher("/jsp/admin/employee/view.jsp").forward(request, response);
    }
    
    private void addEmployee(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        // Get the logged-in employee
        HttpSession session = request.getSession();
        Employee loggedInEmployee = (Employee) session.getAttribute("employee");
        
        // Create a new employee
        Employee employee = new Employee();
        employee.setUsername(request.getParameter("username"));
        employee.setPassword(request.getParameter("password")); // Will be hashed in the service
        employee.setFullName(request.getParameter("fullName"));
        employee.setRole(request.getParameter("role"));
        employee.setEmail(request.getParameter("email"));
        employee.setPhone(request.getParameter("phone"));
        employee.setStatus("ACTIVE");
        
        employeeService.addEmployee(employee, request.getRemoteAddr(), loggedInEmployee.getEmployeeId());
        response.sendRedirect(request.getContextPath() + "/admin/employee");
    }
    
    private void updateEmployee(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        // Get the logged-in employee
        HttpSession session = request.getSession();
        Employee loggedInEmployee = (Employee) session.getAttribute("employee");
        
        // Update the employee
        int id = Integer.parseInt(request.getParameter("id"));
        Employee employee = employeeService.getEmployeeById(id);
        
        employee.setFullName(request.getParameter("fullName"));
        employee.setRole(request.getParameter("role"));
        employee.setEmail(request.getParameter("email"));
        employee.setPhone(request.getParameter("phone"));
        employee.setStatus(request.getParameter("status"));
        
        employeeService.updateEmployee(employee, request.getRemoteAddr(), loggedInEmployee.getEmployeeId());
        response.sendRedirect(request.getContextPath() + "/admin/employee");
    }
    
    private void deleteEmployee(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        // Get the logged-in employee
        HttpSession session = request.getSession();
        Employee loggedInEmployee = (Employee) session.getAttribute("employee");
        
        int id = Integer.parseInt(request.getParameter("id"));
        employeeService.deleteEmployee(id, request.getRemoteAddr(), loggedInEmployee.getEmployeeId());
        response.sendRedirect(request.getContextPath() + "/admin/employee");
    }
    
    private void changePassword(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        // Get the logged-in employee
        HttpSession session = request.getSession();
        Employee loggedInEmployee = (Employee) session.getAttribute("employee");
        
        int id = Integer.parseInt(request.getParameter("id"));
        String newPassword = request.getParameter("newPassword");
        
        employeeService.updateEmployeePassword(id, newPassword, request.getRemoteAddr(), loggedInEmployee.getEmployeeId());
        response.sendRedirect(request.getContextPath() + "/admin/employee");
    }
}
