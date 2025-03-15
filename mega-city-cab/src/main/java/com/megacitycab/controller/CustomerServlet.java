// src/main/java/com/megacitycab/controller/CustomerServlet.java
package com.megacitycab.controller;

import com.megacitycab.model.Customer;
import com.megacitycab.model.Employee;
import com.megacitycab.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@WebServlet("/customer/*")
public class CustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CustomerService customerService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        customerService = new CustomerService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // List all customers
                listCustomers(request, response);
            } else if (pathInfo.equals("/add")) {
                // Show add customer form
                showAddForm(request, response);
            } else if (pathInfo.equals("/edit")) {
                // Show edit customer form
                showEditForm(request, response);
            } else if (pathInfo.equals("/view")) {
                // View customer details
                viewCustomer(request, response);
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
                response.sendRedirect(request.getContextPath() + "/customer");
            } else if (pathInfo.equals("/add")) {
                // Add a new customer
                addCustomer(request, response);
            } else if (pathInfo.equals("/edit")) {
                // Update an existing customer
                updateCustomer(request, response);
            } else if (pathInfo.equals("/delete")) {
                // Delete a customer
                deleteCustomer(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }
    
    private void listCustomers(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        List<Customer> customers = customerService.getAllCustomers();
        request.setAttribute("customers", customers);
        request.getRequestDispatcher("/jsp/customer/list.jsp").forward(request, response);
    }
    
    private void showAddForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/customer/add.jsp").forward(request, response);
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Customer customer = customerService.getCustomerById(id);
        request.setAttribute("customer", customer);
        request.getRequestDispatcher("/jsp/customer/edit.jsp").forward(request, response);
    }
    
    private void viewCustomer(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Customer customer = customerService.getCustomerById(id);
        request.setAttribute("customer", customer);
        request.getRequestDispatcher("/jsp/customer/view.jsp").forward(request, response);
    }
    
    private void addCustomer(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        // Get the logged-in employee
        HttpSession session = request.getSession();
        Employee employee = (Employee) session.getAttribute("employee");
        
        // Create a new customer
        Customer customer = new Customer();
        customer.setRegistrationNumber("CUS" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        customer.setFirstName(request.getParameter("firstName"));
        customer.setLastName(request.getParameter("lastName"));
        customer.setAddress(request.getParameter("address"));
        customer.setCity(request.getParameter("city"));
        customer.setTelephone(request.getParameter("telephone"));
        customer.setEmail(request.getParameter("email"));
        customer.setNic(request.getParameter("nic"));
        customer.setStatus("ACTIVE");
        customer.setRegisteredBy(employee.getEmployeeId());
        
        customerService.addCustomer(customer, request.getRemoteAddr(), employee.getEmployeeId());
        response.sendRedirect(request.getContextPath() + "/customer");
    }
    
    private void updateCustomer(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        // Get the logged-in employee
        HttpSession session = request.getSession();
        Employee employee = (Employee) session.getAttribute("employee");
        
        // Update the customer
        int id = Integer.parseInt(request.getParameter("id"));
        Customer customer = customerService.getCustomerById(id);
        
        customer.setFirstName(request.getParameter("firstName"));
        customer.setLastName(request.getParameter("lastName"));
        customer.setAddress(request.getParameter("address"));
        customer.setCity(request.getParameter("city"));
        customer.setTelephone(request.getParameter("telephone"));
        customer.setEmail(request.getParameter("email"));
        customer.setNic(request.getParameter("nic"));
        customer.setStatus(request.getParameter("status"));
        
        customerService.updateCustomer(customer, request.getRemoteAddr(), employee.getEmployeeId());
        response.sendRedirect(request.getContextPath() + "/customer");
    }
    
    private void deleteCustomer(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        // Get the logged-in employee
        HttpSession session = request.getSession();
        Employee employee = (Employee) session.getAttribute("employee");
        
        int id = Integer.parseInt(request.getParameter("id"));
        customerService.deleteCustomer(id, request.getRemoteAddr(), employee.getEmployeeId());
        response.sendRedirect(request.getContextPath() + "/customer");
    }
}
