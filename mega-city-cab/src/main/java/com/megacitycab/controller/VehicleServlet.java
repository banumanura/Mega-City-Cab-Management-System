// src/main/java/com/megacitycab/controller/VehicleServlet.java

package com.megacitycab.controller;

import com.megacitycab.model.Vehicle;
import com.megacitycab.model.Employee;
import com.megacitycab.service.VehicleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/vehicle/*")
public class VehicleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        vehicleService = new VehicleService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // List all vehicles
                listVehicles(request, response);
            } else if (pathInfo.equals("/add")) {
                // Show add vehicle form
                showAddForm(request, response);
            } else if (pathInfo.equals("/edit")) {
                // Show edit vehicle form
                showEditForm(request, response);
            } else if (pathInfo.equals("/view")) {
                // View vehicle details
                viewVehicle(request, response);
            } else if (pathInfo.equals("/available")) {
                // List available vehicles
                listAvailableVehicles(request, response);
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
                response.sendRedirect(request.getContextPath() + "/vehicle");
            } else if (pathInfo.equals("/add")) {
                // Add a new vehicle
                addVehicle(request, response);
            } else if (pathInfo.equals("/edit")) {
                // Update an existing vehicle
                updateVehicle(request, response);
            } else if (pathInfo.equals("/delete")) {
                // Delete a vehicle
                deleteVehicle(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }

    private void listVehicles(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        request.setAttribute("vehicles", vehicles);
        request.getRequestDispatcher("/jsp/vehicle/list.jsp").forward(request, response);
    }
    
    private void listAvailableVehicles(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Vehicle> vehicles = vehicleService.getAvailableVehicles();
        request.setAttribute("vehicles", vehicles);
        request.getRequestDispatcher("/jsp/vehicle/available.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/vehicle/add.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Vehicle vehicle = vehicleService.getVehicleById(id);
        request.setAttribute("vehicle", vehicle);
        request.getRequestDispatcher("/jsp/vehicle/edit.jsp").forward(request, response);
    }

    private void viewVehicle(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Vehicle vehicle = vehicleService.getVehicleById(id);
        request.setAttribute("vehicle", vehicle);
        request.getRequestDispatcher("/jsp/vehicle/view.jsp").forward(request, response);
    }

    private void addVehicle(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        // Get the logged-in employee
        HttpSession session = request.getSession();
        Employee employee = (Employee) session.getAttribute("employee");
        
        // Create a new vehicle
        Vehicle vehicle = new Vehicle();
        vehicle.setRegistrationNumber(request.getParameter("registrationNumber"));
        vehicle.setModel(request.getParameter("model"));
        vehicle.setMake(request.getParameter("make"));
        vehicle.setYear(Integer.parseInt(request.getParameter("year")));
        vehicle.setColor(request.getParameter("color"));
        vehicle.setSeatCapacity(Integer.parseInt(request.getParameter("seatCapacity")));
        vehicle.setAcAvailable(Boolean.parseBoolean(request.getParameter("acAvailable")));
        vehicle.setVehicleType(request.getParameter("vehicleType"));
        vehicle.setStatus("AVAILABLE");
        
        vehicleService.addVehicle(vehicle, request.getRemoteAddr(), employee.getEmployeeId());
        response.sendRedirect(request.getContextPath() + "/vehicle");
    }

    private void updateVehicle(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        // Get the logged-in employee
        HttpSession session = request.getSession();
        Employee employee = (Employee) session.getAttribute("employee");
        
        // Update the vehicle
        int id = Integer.parseInt(request.getParameter("id"));
        Vehicle vehicle = vehicleService.getVehicleById(id);
        vehicle.setRegistrationNumber(request.getParameter("registrationNumber"));
        vehicle.setModel(request.getParameter("model"));
        vehicle.setMake(request.getParameter("make"));
        vehicle.setYear(Integer.parseInt(request.getParameter("year")));
        vehicle.setColor(request.getParameter("color"));
        vehicle.setSeatCapacity(Integer.parseInt(request.getParameter("seatCapacity")));
        vehicle.setAcAvailable(Boolean.parseBoolean(request.getParameter("acAvailable")));
        vehicle.setVehicleType(request.getParameter("vehicleType"));
        vehicle.setStatus(request.getParameter("status"));
        
        vehicleService.updateVehicle(vehicle, request.getRemoteAddr(), employee.getEmployeeId());
        response.sendRedirect(request.getContextPath() + "/vehicle");
    }

    private void deleteVehicle(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        // Get the logged-in employee
        HttpSession session = request.getSession();
        Employee employee = (Employee) session.getAttribute("employee");
        
        int id = Integer.parseInt(request.getParameter("id"));
        vehicleService.deleteVehicle(id, request.getRemoteAddr(), employee.getEmployeeId());
        response.sendRedirect(request.getContextPath() + "/vehicle");
    }
}
