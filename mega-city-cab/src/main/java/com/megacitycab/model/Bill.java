// src/main/java/com/megacitycab/model/Bill.java
package com.megacitycab.model;

import java.sql.Timestamp;
import java.math.BigDecimal;

public class Bill {
    private int billId;
    private String billNumber;
    private int bookingId;
    private BigDecimal baseAmount;
    private BigDecimal distanceCharge;
    private BigDecimal waitingCharge;
    private BigDecimal nightSurcharge;
    private BigDecimal taxAmount;
    private BigDecimal discountAmount;
    private String discountReason;
    private BigDecimal totalAmount;
    private String paymentMethod;
    private String paymentStatus;
    private int generatedBy;
    private Timestamp generatedAt;
    private Timestamp updatedAt;
    
    // Constructors
    public Bill() {}
    
    public Bill(int billId, String billNumber, int bookingId, BigDecimal baseAmount, 
               BigDecimal distanceCharge, BigDecimal waitingCharge, BigDecimal nightSurcharge,
               BigDecimal taxAmount, BigDecimal discountAmount, String discountReason,
               BigDecimal totalAmount, String paymentMethod, String paymentStatus, int generatedBy,
               Timestamp generatedAt, Timestamp updatedAt) {
        this.billId = billId;
        this.billNumber = billNumber;
        this.bookingId = bookingId;
        this.baseAmount = baseAmount;
        this.distanceCharge = distanceCharge;
        this.waitingCharge = waitingCharge;
        this.nightSurcharge = nightSurcharge;
        this.taxAmount = taxAmount;
        this.discountAmount = discountAmount;
        this.discountReason = discountReason;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.generatedBy = generatedBy;
        this.generatedAt = generatedAt;
        this.updatedAt = updatedAt;
    }
    
    // Getters and Setters
    public int getBillId() {
        return billId;
    }
    
    public void setBillId(int billId) {
        this.billId = billId;
    }
    
    public String getBillNumber() {
        return billNumber;
    }
    
    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }
    
    public int getBookingId() {
        return bookingId;
    }
    
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }
    
    public BigDecimal getBaseAmount() {
        return baseAmount;
    }
    
    public void setBaseAmount(BigDecimal baseAmount) {
        this.baseAmount = baseAmount;
    }
    
    public BigDecimal getDistanceCharge() {
        return distanceCharge;
    }
    
    public void setDistanceCharge(BigDecimal distanceCharge) {
        this.distanceCharge = distanceCharge;
    }
    
    public BigDecimal getWaitingCharge() {
        return waitingCharge;
    }
    
    public void setWaitingCharge(BigDecimal waitingCharge) {
        this.waitingCharge = waitingCharge;
    }
    
    public BigDecimal getNightSurcharge() {
        return nightSurcharge;
    }
    
    public void setNightSurcharge(BigDecimal nightSurcharge) {
        this.nightSurcharge = nightSurcharge;
    }
    
    public BigDecimal getTaxAmount() {
        return taxAmount;
    }
    
    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }
    
    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }
    
    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }
    
    public String getDiscountReason() {
        return discountReason;
    }
    
    public void setDiscountReason(String discountReason) {
        this.discountReason = discountReason;
    }
    
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public String getPaymentStatus() {
        return paymentStatus;
    }
    
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    
    public int getGeneratedBy() {
        return generatedBy;
    }
    
    public void setGeneratedBy(int generatedBy) {
        this.generatedBy = generatedBy;
    }
    
    public Timestamp getGeneratedAt() {
        return generatedAt;
    }
    
    public void setGeneratedAt(Timestamp generatedAt) {
        this.generatedAt = generatedAt;
    }
    
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    @Override
    public String toString() {
        return "Bill{" +
                "billId=" + billId +
                ", billNumber='" + billNumber + '\'' +
                ", bookingId=" + bookingId +
                ", baseAmount=" + baseAmount +
                ", distanceCharge=" + distanceCharge +
                ", waitingCharge=" + waitingCharge +
                ", nightSurcharge=" + nightSurcharge +
                ", taxAmount=" + taxAmount +
                ", discountAmount=" + discountAmount +
                ", totalAmount=" + totalAmount +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                '}';
    }
}
