// src/main/java/com/megacitycab/controller/BookingServlet.java
package com.megacitycab.controller;

import com.megacitycab.model.Booking;
import com.megacitycab.model.Customer;
import com.megacitycab.model.Vehicle;
import com.megacitycab.model.Driver;
import com.megacitycab.model.Employee;
import com.megacitycab.service.BookingService;
import com.megacitycab.service.CustomerService;
import com.megacitycab.service.VehicleService;
import com.megacitycab.service.DriverService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;

@WebServlet("/booking/*")
public class BookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BookingService bookingService;
    private CustomerService customerService;
    private VehicleService vehicleService;
    private DriverService driverService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        bookingService = new BookingService();
        customerService = new CustomerService();
        vehicleService = new VehicleService();
        driverService = new DriverService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                listBookings(request, response);
            } else if (pathInfo.equals("/add")) {
                showAddForm(request, response);
            } else if (pathInfo.equals("/edit")) {
                showEditForm(request, response);
            } else if (pathInfo.equals("/view")) {
                viewBooking(request, response);
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
                response.sendRedirect(request.getContextPath() + "/booking");
            } else if (pathInfo.equals("/add")) {
                addBooking(request, response);
            } else if (pathInfo.equals("/update")) {
                updateBooking(request, response);
            } else if (pathInfo.equals("/delete")) {
                deleteBooking(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException | ParseException e) {
            throw new ServletException("Error processing request", e);
        }
    }
    
    private void listBookings(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        List<Booking> bookings = bookingService.getAllBookings();
        request.setAttribute("bookings", bookings);
        request.getRequestDispatcher("/jsp/booking/list.jsp").forward(request, response);
    }
    
    private void showAddForm(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        List<Customer> customers = customerService.getAllCustomers();
        List<Vehicle> vehicles = vehicleService.getAvailableVehicles();
        List<Driver> drivers = driverService.getAvailableDrivers();
        
        request.setAttribute("customers", customers);
        request.setAttribute("vehicles", vehicles);
        request.setAttribute("drivers", drivers);
        request.getRequestDispatcher("/jsp/booking/add.jsp").forward(request, response);
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Booking booking = bookingService.getBookingById(id);
        
        List<Customer> customers = customerService.getAllCustomers();
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        List<Driver> drivers = driverService.getAllDrivers();
        
        request.setAttribute("booking", booking);
        request.setAttribute("customers", customers);
        request.setAttribute("vehicles", vehicles);
        request.setAttribute("drivers", drivers);
        request.getRequestDispatcher("/jsp/booking/edit.jsp").forward(request, response);
    }
    
    private void viewBooking(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Booking booking = bookingService.getBookingById(id);
        
        Customer customer = customerService.getCustomerById(booking.getCustomerId());
        Vehicle vehicle = vehicleService.getVehicleById(booking.getVehicleId());
        Driver driver = driverService.getDriverById(booking.getDriverId());
        
        request.setAttribute("booking", booking);
        request.setAttribute("customer", customer);
        request.setAttribute("vehicle", vehicle);
        request.setAttribute("driver", driver);
        request.getRequestDispatcher("/jsp/booking/view.jsp").forward(request, response);
    }

    private void addBooking(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ParseException, IOException, ServletException {
        // Get employee from session for created_by field
        HttpSession session = request.getSession();
        Employee employee = (Employee) session.getAttribute("employee");
        
        // Check if employee exists in session
        if (employee == null || employee.getEmployeeId() <= 0) {
            throw new ServletException("Employee not logged in or invalid employee session");
        }

        Booking booking = new Booking();
        
        // Generate a unique booking number
        String bookingNumber = "B" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        booking.setBookingNumber(bookingNumber);
        
        booking.setCustomerId(Integer.parseInt(request.getParameter("customerId")));
        booking.setVehicleId(Integer.parseInt(request.getParameter("vehicleId")));
        booking.setDriverId(Integer.parseInt(request.getParameter("driverId")));
        booking.setPickupLocation(request.getParameter("pickupLocation"));
        booking.setDestination(request.getParameter("destination"));
        booking.setBookingDate(Date.valueOf(request.getParameter("bookingDate")));
        
        // Ensure time is valid
        String bookingTimeStr = request.getParameter("bookingTime");
        if (bookingTimeStr != null && !bookingTimeStr.isEmpty()) {
            try {
                booking.setBookingTime(Time.valueOf(bookingTimeStr + ":00"));
            } catch (IllegalArgumentException e) {
                throw new ServletException("Invalid booking time format. Use HH:mm (e.g., 14:30)", e);
            }
        } else {
            throw new ServletException("Booking time cannot be empty.");
        }

        booking.setEstimatedDistance(new BigDecimal(request.getParameter("estimatedDistance")));
        booking.setSpecialRequests(request.getParameter("specialRequests"));
        booking.setStatus("PENDING"); // Default status for new bookings
        booking.setCreatedBy(employee.getEmployeeId()); // Set the created_by field

        bookingService.addBooking(booking, request.getRemoteAddr(), employee.getEmployeeId());
        response.sendRedirect(request.getContextPath() + "/booking");
    }

    private void updateBooking(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException, ServletException {
        HttpSession session = request.getSession();
        Employee employee = (Employee) session.getAttribute("employee");
        
        // Check if employee exists in session
        if (employee == null || employee.getEmployeeId() <= 0) {
            throw new ServletException("Employee not logged in or invalid employee session");
        }

        int bookingId = Integer.parseInt(request.getParameter("bookingId"));
        Booking booking = bookingService.getBookingById(bookingId);

        if (booking == null) {
            throw new ServletException("Booking not found for ID: " + bookingId);
        }

        booking.setCustomerId(Integer.parseInt(request.getParameter("customerId")));
        booking.setVehicleId(Integer.parseInt(request.getParameter("vehicleId")));
        booking.setDriverId(Integer.parseInt(request.getParameter("driverId")));
        booking.setPickupLocation(request.getParameter("pickupLocation"));
        booking.setDestination(request.getParameter("destination"));
        booking.setBookingDate(Date.valueOf(request.getParameter("bookingDate")));
        
        // Ensure time is valid
        String bookingTimeStr = request.getParameter("bookingTime");
        if (bookingTimeStr != null && !bookingTimeStr.isEmpty()) {
            try {
                booking.setBookingTime(Time.valueOf(bookingTimeStr + ":00"));
            } catch (IllegalArgumentException e) {
                throw new ServletException("Invalid booking time format. Use HH:mm (e.g., 14:30)", e);
            }
        } else {
            throw new ServletException("Booking time cannot be empty.");
        }

        booking.setEstimatedDistance(new BigDecimal(request.getParameter("estimatedDistance")));
        booking.setSpecialRequests(request.getParameter("specialRequests"));
        booking.setStatus(request.getParameter("status"));

        bookingService.updateBooking(booking, request.getRemoteAddr(), employee.getEmployeeId());
        response.sendRedirect(request.getContextPath() + "/booking");
    }

    private void deleteBooking(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException, ServletException {  // Added ServletException here
        HttpSession session = request.getSession();
        Employee employee = (Employee) session.getAttribute("employee");
        
        // Check if employee exists in session
        if (employee == null || employee.getEmployeeId() <= 0) {
            throw new ServletException("Employee not logged in or invalid employee session");
        }

        int bookingId = Integer.parseInt(request.getParameter("id"));
        bookingService.deleteBooking(bookingId, request.getRemoteAddr(), employee.getEmployeeId());

        response.sendRedirect(request.getContextPath() + "/booking");
    }
}