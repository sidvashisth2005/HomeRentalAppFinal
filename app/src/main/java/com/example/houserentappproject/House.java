package com.example.houserentappproject;

import java.util.ArrayList;
import java.util.List;

public class House {
    private String id;
    private String name;
    private String number;
    private String type;
    private String rent;
    private String city;
    private String landmark;
    private boolean isAvailable;
    private List<HistoricalRate> historicalRates;
    private String ownerId;
    private String ownerName;
    private String ownerAccountNumber;
    private String amenities;
    private boolean furnished;
    private int numberOfRooms;
    private int numberOfBathrooms;

    // Default constructor required for Firebase
    public House() {
        this.historicalRates = new ArrayList<>();
    }

    public House(String id, String name, String number, String type, String rent, 
                String city, String landmark, boolean isAvailable, String ownerId, 
                String ownerName, String ownerAccountNumber) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.type = type;
        this.rent = rent;
        this.city = city;
        this.landmark = landmark;
        this.isAvailable = isAvailable;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.ownerAccountNumber = ownerAccountNumber;
        this.historicalRates = new ArrayList<>();
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getRent() { return rent; }
    public void setRent(String rent) { this.rent = rent; }
    
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    
    public String getLandmark() { return landmark; }
    public void setLandmark(String landmark) { this.landmark = landmark; }
    
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
    
    public List<HistoricalRate> getHistoricalRates() { return historicalRates; }
    public void setHistoricalRates(List<HistoricalRate> historicalRates) { 
        this.historicalRates = historicalRates; 
    }
    
    public String getOwnerId() { return ownerId; }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }
    
    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
    
    public String getOwnerAccountNumber() { return ownerAccountNumber; }
    public void setOwnerAccountNumber(String ownerAccountNumber) { 
        this.ownerAccountNumber = ownerAccountNumber; 
    }

    public String getAmenities() { return amenities; }
    public void setAmenities(String amenities) { this.amenities = amenities; }

    public boolean isFurnished() { return furnished; }
    public void setFurnished(boolean furnished) { this.furnished = furnished; }

    public int getNumberOfRooms() { return numberOfRooms; }
    public void setNumberOfRooms(int numberOfRooms) { this.numberOfRooms = numberOfRooms; }

    public int getNumberOfBathrooms() { return numberOfBathrooms; }
    public void setNumberOfBathrooms(int numberOfBathrooms) { 
        this.numberOfBathrooms = numberOfBathrooms; 
    }

    // Helper methods for rate calculations
    public double getAverageRate() {
        if (historicalRates.isEmpty()) return 0;
        return historicalRates.stream()
                .mapToDouble(HistoricalRate::getRate)
                .average()
                .orElse(0);
    }

    public double getLowestRate() {
        if (historicalRates.isEmpty()) return 0;
        return historicalRates.stream()
                .mapToDouble(HistoricalRate::getRate)
                .min()
                .orElse(0);
    }
}
