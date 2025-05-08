package com.example.houserentappproject;

import java.util.Date;

public class Payment {
    private String id;
    private String bookingId;
    private String houseId;
    private String tenantId;
    private String ownerId;
    private double amount;
    private Date paymentDate;
    private String status; // PENDING, COMPLETED, FAILED
    private String transactionId;

    public Payment() {}

    public Payment(String id, String bookingId, String houseId, String tenantId, 
                  String ownerId, double amount) {
        this.id = id;
        this.bookingId = bookingId;
        this.houseId = houseId;
        this.tenantId = tenantId;
        this.ownerId = ownerId;
        this.amount = amount;
        this.status = "PENDING";
        this.paymentDate = new Date();
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }
    
    public String getHouseId() { return houseId; }
    public void setHouseId(String houseId) { this.houseId = houseId; }
    
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    
    public String getOwnerId() { return ownerId; }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }
    
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    
    public Date getPaymentDate() { return paymentDate; }
    public void setPaymentDate(Date paymentDate) { this.paymentDate = paymentDate; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
} 