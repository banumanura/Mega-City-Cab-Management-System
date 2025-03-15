package com.megacitycab.controller;

import com.megacitycab.model.Bill;
import com.megacitycab.model.Booking;
import com.megacitycab.model.Customer;
import com.megacitycab.model.Employee;
import com.megacitycab.service.BillService;
import com.megacitycab.service.BookingService;
import com.megacitycab.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/bill/*")
public class BillServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BillService billService;
    private BookingService bookingService;
    private CustomerService customerService;

    @Override
    public void init() throws ServletException {
        super.init();
        billService = new BillService();
        bookingService = new BookingService();
        customerService = new CustomerService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                listBills(request, response);
            } else if (pathInfo.equals("/generate")) {
                showGenerateForm(request, response);
            } else if (pathInfo.equals("/edit")) {
                showEditForm(request, response);
            } else if (pathInfo.equals("/view")) {
                viewBill(request, response);
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
                response.sendRedirect(request.getContextPath() + "/bill");
            } else if (pathInfo.equals("/generate")) {
                generateBill(request, response);
            } else if (pathInfo.equals("/edit")) {
                updateBill(request, response);
            } else if (pathInfo.equals("/delete")) {
                deleteBill(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }

    private void listBills(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        List<Bill> bills = billService.getAllBills();
        request.setAttribute("bills", bills);
        request.getRequestDispatcher("/jsp/bill/list.jsp").forward(request, response);
    }

    private void showGenerateForm(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        String bookingIdParam = request.getParameter("bookingId");

        if (bookingIdParam == null || bookingIdParam.isEmpty()) {
            request.setAttribute("error", "Missing or invalid booking ID.");
            request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
            return;
        }

        int bookingId;
        try {
            bookingId = Integer.parseInt(bookingIdParam);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid booking ID format.");
            request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
            return;
        }

        Booking booking = bookingService.getBookingById(bookingId);
        if (booking == null) {
            request.setAttribute("error", "Booking not found.");
            request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
            return;
        }

        Customer customer = customerService.getCustomerById(booking.getCustomerId());

        request.setAttribute("booking", booking);
        request.setAttribute("customer", customer);
        request.getRequestDispatcher("/jsp/bill/generate.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Bill bill = billService.getBillById(id);
        Booking booking = bookingService.getBookingById(bill.getBookingId());

        request.setAttribute("bill", bill);
        request.setAttribute("booking", booking);
        request.getRequestDispatcher("/jsp/bill/edit.jsp").forward(request, response);
    }

    private void viewBill(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Bill bill = billService.getBillById(id);
        Booking booking = bookingService.getBookingById(bill.getBookingId());
        Customer customer = customerService.getCustomerById(booking.getCustomerId());

        request.setAttribute("bill", bill);
        request.setAttribute("booking", booking);
        request.setAttribute("customer", customer);
        request.getRequestDispatcher("/jsp/bill/view.jsp").forward(request, response);
    }

    private void generateBill(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        HttpSession session = request.getSession();
        Employee employee = (Employee) session.getAttribute("employee");

        String bookingIdParam = request.getParameter("bookingId");
        if (bookingIdParam == null || bookingIdParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/bill?error=Missing booking ID");
            return;
        }

        int bookingId;
        try {
            bookingId = Integer.parseInt(bookingIdParam);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/bill?error=Invalid booking ID");
            return;
        }

        BigDecimal waitingHours = new BigDecimal(request.getParameter("waitingHours"));
        boolean isNightTrip = "on".equals(request.getParameter("isNightTrip"));

        BigDecimal discountAmount = BigDecimal.ZERO;
        String discountStr = request.getParameter("discountAmount");
        if (discountStr != null && !discountStr.isEmpty()) {
            discountAmount = new BigDecimal(discountStr);
        }

        String discountReason = request.getParameter("discountReason");
        String paymentMethod = request.getParameter("paymentMethod");

        int billId = billService.generateBill(
            bookingId, 
            waitingHours, 
            isNightTrip, 
            discountAmount, 
            discountReason, 
            paymentMethod, 
            employee.getEmployeeId(), 
            request.getRemoteAddr()
        );

        response.sendRedirect(request.getContextPath() + "/bill/view?id=" + billId);
    }

    private void updateBill(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        HttpSession session = request.getSession();
        Employee employee = (Employee) session.getAttribute("employee");

        int id = Integer.parseInt(request.getParameter("id"));
        Bill bill = billService.getBillById(id);

        String paymentMethod = request.getParameter("paymentMethod");
        String paymentStatus = request.getParameter("paymentStatus");

        bill.setPaymentMethod(paymentMethod);
        bill.setPaymentStatus(paymentStatus);

        billService.updateBill(bill, request.getRemoteAddr(), employee.getEmployeeId());
        response.sendRedirect(request.getContextPath() + "/bill");
    }

    private void deleteBill(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        HttpSession session = request.getSession();
        Employee employee = (Employee) session.getAttribute("employee");

        int id = Integer.parseInt(request.getParameter("id"));
        billService.deleteBill(id, request.getRemoteAddr(), employee.getEmployeeId());
        response.sendRedirect(request.getContextPath() + "/bill");
    }
}
