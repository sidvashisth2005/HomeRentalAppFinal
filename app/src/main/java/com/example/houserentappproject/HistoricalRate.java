package com.example.houserentappproject;

import java.util.Date;

public class HistoricalRate {
    private double rate;
    private Date date;
    private String tenantId;
    private String tenantName;

    // Default constructor required for Firebase
    public HistoricalRate() {}

    public HistoricalRate(double rate, Date date, String tenantId, String tenantName) {
        this.rate = rate;
        this.date = date;
        this.tenantId = tenantId;
        this.tenantName = tenantName;
    }

    public double getRate() { return rate; }
    public void setRate(double rate) { this.rate = rate; }
    
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    
    public String getTenantName() { return tenantName; }
    public void setTenantName(String tenantName) { this.tenantName = tenantName; }
} 