// src/main/java/com/megacitycab/service/BillService.java
package com.megacitycab.service;

import com.megacitycab.dao.BillDAO;
import com.megacitycab.dao.BookingDAO;
import com.megacitycab.dao.SystemLogDAO;
import com.megacitycab.model.Bill;
import com.megacitycab.model.Booking;
import com.megacitycab.model.SystemLog;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class BillService {
    private BillDAO billDAO;
    private BookingDAO bookingDAO;
    private SystemLogDAO systemLogDAO;
    
    // Constants for billing calculations
    private static final BigDecimal BASE_FARE = new BigDecimal("500.00");
    private static final BigDecimal RATE_PER_KM = new BigDecimal("50.00");
    private static final BigDecimal WAITING_CHARGE_PER_HOUR = new BigDecimal("200.00");
    private static final BigDecimal NIGHT_SURCHARGE_PERCENTAGE = new BigDecimal("0.20"); // 20%
    private static final BigDecimal TAX_PERCENTAGE = new BigDecimal("0.15"); // 15%
    
    public BillService() {
        this.billDAO = new BillDAO();
        this.bookingDAO = new BookingDAO();
        this.systemLogDAO = new SystemLogDAO();
    }
    
    public Bill getBillById(int id) throws SQLException {
        return billDAO.getBillById(id);
    }
    
    public Bill getBillByBillNumber(String billNumber) throws SQLException {
        return billDAO.getBillByBillNumber(billNumber);
    }
    
    public Bill getBillByBookingId(int bookingId) throws SQLException {
        return billDAO.getBillByBookingId(bookingId);
    }
    
    public List<Bill> getAllBills() throws SQLException {
        return billDAO.getAllBills();
    }
    
    public int generateBill(int bookingId, BigDecimal waitingHours, boolean isNightTrip, BigDecimal discountAmount, 
                          String discountReason, String paymentMethod, int generatedBy, String ipAddress) throws SQLException {
        
        Booking booking = bookingDAO.getBookingById(bookingId);
        if (booking == null) {
            throw new SQLException("Booking not found with ID: " + bookingId);
        }
        
        // Check if bill already exists for this booking
        Bill existingBill = billDAO.getBillByBookingId(bookingId);
        if (existingBill != null) {
            throw new SQLException("Bill already exists for booking ID: " + bookingId);
        }
        
        // Calculate bill components
        BigDecimal baseAmount = BASE_FARE;
        BigDecimal distanceCharge = booking.getEstimatedDistance().multiply(RATE_PER_KM).setScale(2, RoundingMode.HALF_UP);
        BigDecimal waitingCharge = waitingHours.multiply(WAITING_CHARGE_PER_HOUR).setScale(2, RoundingMode.HALF_UP);
        
        BigDecimal nightSurcharge = BigDecimal.ZERO;
        if (isNightTrip) {
            nightSurcharge = baseAmount.add(distanceCharge).multiply(NIGHT_SURCHARGE_PERCENTAGE).setScale(2, RoundingMode.HALF_UP);
        }
        
        BigDecimal subtotal = baseAmount.add(distanceCharge).add(waitingCharge).add(nightSurcharge);
        BigDecimal taxAmount = subtotal.multiply(TAX_PERCENTAGE).setScale(2, RoundingMode.HALF_UP);
        
        // Apply discount if provided
        if (discountAmount == null) {
            discountAmount = BigDecimal.ZERO;
        }
        
        BigDecimal totalAmount = subtotal.add(taxAmount).subtract(discountAmount).setScale(2, RoundingMode.HALF_UP);
        
        // Create bill object
        Bill bill = new Bill();
        bill.setBillNumber("BL" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        bill.setBookingId(bookingId);
        bill.setBaseAmount(baseAmount);
        bill.setDistanceCharge(distanceCharge);
        bill.setWaitingCharge(waitingCharge);
        bill.setNightSurcharge(nightSurcharge);
        bill.setTaxAmount(taxAmount);
        bill.setDiscountAmount(discountAmount);
        bill.setDiscountReason(discountReason);
        bill.setTotalAmount(totalAmount);
        bill.setPaymentMethod(paymentMethod);
        bill.setPaymentStatus("PENDING");
        bill.setGeneratedBy(generatedBy);
        
        int billId = billDAO.addBill(bill);
        
        // Update booking status to COMPLETED if it's not already
        if (!"COMPLETED".equals(booking.getStatus())) {
            booking.setStatus("COMPLETED");
            bookingDAO.updateBooking(booking);
        }
        
        // Log the action
        if (billId > 0) {
            SystemLog log = new SystemLog();
            log.setEmployeeId(generatedBy);
            log.setAction("GENERATE_BILL");
            log.setDetails("Generated bill: " + bill.getBillNumber() + " for booking: " + booking.getBookingNumber() + ", Amount: " + totalAmount);
            log.setIpAddress(ipAddress);
            systemLogDAO.addLog(log);
        }
        
        return billId;
    }
    
    public boolean updateBill(Bill bill, String ipAddress, Integer loggedInEmployeeId) throws SQLException {
        boolean success = billDAO.updateBill(bill);
        
        // Log the action
        if (success) {
            SystemLog log = new SystemLog();
            log.setEmployeeId(loggedInEmployeeId);
            log.setAction("UPDATE_BILL");
            log.setDetails("Updated bill: " + bill.getBillNumber() + " with ID: " + bill.getBillId() + ", Payment Status: " + bill.getPaymentStatus());
            log.setIpAddress(ipAddress);
            systemLogDAO.addLog(log);
        }
        
        return success;
    }
    
    public boolean deleteBill(int id, String ipAddress, Integer loggedInEmployeeId) throws SQLException {
        Bill bill = billDAO.getBillById(id);
        boolean success = billDAO.deleteBill(id);
        
        // Log the action
        if (success && bill != null) {
            SystemLog log = new SystemLog();
            log.setEmployeeId(loggedInEmployeeId);
            log.setAction("DELETE_BILL");
            log.setDetails("Deleted bill: " + bill.getBillNumber() + " with ID: " + id);
            log.setIpAddress(ipAddress);
            systemLogDAO.addLog(log);
        }
        
        return success;
    }
}
