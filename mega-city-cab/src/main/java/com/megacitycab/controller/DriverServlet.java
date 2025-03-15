package com.megacitycab.controller;

import com.megacitycab.model.Driver;
import com.megacitycab.model.Employee;
import com.megacitycab.service.DriverService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;

@WebServlet("/driver/*")
public class DriverServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DriverService driverService;
    private SimpleDateFormat dateFormat;

    @Override
    public void init() throws ServletException {
        super.init();
        driverService = new DriverService();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // List all drivers
                listDrivers(request, response);
            } else if (pathInfo.equals("/add")) {
                // Show add driver form
                showAddForm(request, response);
            } else if (pathInfo.equals("/edit")) {
                // Show edit driver form
                showEditForm(request, response);
            } else if (pathInfo.equals("/view")) {
                // View driver details
                viewDriver(request, response);
            } else if (pathInfo.equals("/available")) {
                // List available drivers
                listAvailableDrivers(request, response);
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
                response.sendRedirect(request.getContextPath() + "/driver");
            } else if (pathInfo.equals("/add")) {
                // Add a new driver
                addDriver(request, response);
            } else if (pathInfo.equals("/edit")) {
                // Update an existing driver
                updateDriver(request, response);
            } else if (pathInfo.equals("/delete")) {
                // Delete a driver
                deleteDriver(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException | ParseException e) {
            throw new ServletException("Error processing request", e);
        }
    }

    private void listDrivers(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Driver> drivers = driverService.getAllDrivers();
        request.setAttribute("drivers", drivers);
        request.getRequestDispatcher("/jsp/driver/list.jsp").forward(request, response);
    }
    
    private void listAvailableDrivers(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Driver> drivers = driverService.getAvailableDrivers();
        request.setAttribute("drivers", drivers);
        request.getRequestDispatcher("/jsp/driver/available.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/driver/add.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Driver driver = driverService.getDriverById(id);
        request.setAttribute("driver", driver);
        request.getRequestDispatcher("/jsp/driver/edit.jsp").forward(request, response);
    }

    private void viewDriver(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Driver driver = driverService.getDriverById(id);
        request.setAttribute("driver", driver);
        request.getRequestDispatcher("/jsp/driver/view.jsp").forward(request, response);
    }

    private void addDriver(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException, ParseException {
        // Get the logged-in employee
        HttpSession session = request.getSession();
        Employee employee = (Employee) session.getAttribute("employee");
        
        // Create a new driver
        Driver driver = new Driver();
        driver.setFirstName(request.getParameter("firstName"));
        driver.setLastName(request.getParameter("lastName"));
        driver.setLicenseNumber(request.getParameter("licenseNumber"));
        driver.setNic(request.getParameter("nic"));
        driver.setAddress(request.getParameter("address"));
        driver.setTelephone(request.getParameter("telephone"));
        driver.setEmergencyContact(request.getParameter("emergencyContact"));
        
        // Parse dates
        Date dateOfBirth = Date.valueOf(request.getParameter("dateOfBirth"));
        Date joiningDate = Date.valueOf(request.getParameter("joiningDate"));
        
        driver.setDateOfBirth(dateOfBirth);
        driver.setJoiningDate(joiningDate);
        driver.setStatus(request.getParameter("status"));
        
        driverService.addDriver(driver, request.getRemoteAddr(), employee.getEmployeeId());
        response.sendRedirect(request.getContextPath() + "/driver");
    }

    private void updateDriver(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException, ParseException {
        // Get the logged-in employee
        HttpSession session = request.getSession();
        Employee employee = (Employee) session.getAttribute("employee");
        
        // Update the driver
        int id = Integer.parseInt(request.getParameter("id"));
        Driver driver = driverService.getDriverById(id);
        
        driver.setFirstName(request.getParameter("firstName"));
        driver.setLastName(request.getParameter("lastName"));
        driver.setLicenseNumber(request.getParameter("licenseNumber"));
        driver.setNic(request.getParameter("nic"));
        driver.setAddress(request.getParameter("address"));
        driver.setTelephone(request.getParameter("telephone"));
        driver.setEmergencyContact(request.getParameter("emergencyContact"));
        
        // Parse dates
        Date dateOfBirth = Date.valueOf(request.getParameter("dateOfBirth"));
        Date joiningDate = Date.valueOf(request.getParameter("joiningDate"));
        
        driver.setDateOfBirth(dateOfBirth);
        driver.setJoiningDate(joiningDate);
        driver.setStatus(request.getParameter("status"));
        
        driverService.updateDriver(driver, request.getRemoteAddr(), employee.getEmployeeId());
        response.sendRedirect(request.getContextPath() + "/driver");
    }

    private void deleteDriver(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        // Get the logged-in employee
        HttpSession session = request.getSession();
        Employee employee = (Employee) session.getAttribute("employee");
        
        int id = Integer.parseInt(request.getParameter("id"));
        driverService.deleteDriver(id, request.getRemoteAddr(), employee.getEmployeeId());
        response.sendRedirect(request.getContextPath() + "/driver");
    }
}
