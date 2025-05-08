package com.example.houserentappproject;

import java.util.Date;

public class Booking {
    private String id;
    private String houseId;
    private String tenantId;
    private String tenantName;
    private Date startDate;
    private Date endDate;
    private double amount;
    private String status; // PENDING, CONFIRMED, CANCELLED
    private String paymentId;
    private Date bookingDate;

    public Booking() {}

    public Booking(String id, String houseId, String tenantId, String tenantName, 
                  Date startDate, Date endDate, double amount) {
        this.id = id;
        this.houseId = houseId;
        this.tenantId = tenantId;
        this.tenantName = tenantName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.status = "PENDING";
        this.bookingDate = new Date();
    }

    //
} 