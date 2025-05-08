package com.example.houserentappproject;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class DataGenerator {
    private static final String[] CITIES = {"Mumbai", "Hyderabad", "Chennai", "Bangalore"};
    private static final String[] LANDMARKS = {
        "Near Metro Station", "Near Shopping Mall", "Near Hospital", 
        "Near University", "Near Park", "Near Market"
    };
    private static final String[] HOUSE_TYPES = {"1BHK", "2BHK", "3BHK", "PG", "Studio"};
    private static final String[] OWNER_NAMES = {
        "John Doe", "Jane Smith", "Mike Johnson", "Sarah Williams", "David Brown"
    };
    private static final String[] TENANT_NAMES = {
        "Alex Turner", "Emma Watson", "Chris Evans", "Lisa Ray", "Tom Hardy"
    };

    public static void generateTestData() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("houses");

        // Mumbai houses
        House house1 = new House(
            "m1",
            "John Doe",
            "9876543210",
            "2BHK Apartment",
            "25000",
            "Mumbai",
            "Andheri West",
            true,
            "owner1",
            "John Doe",
            "1234567890"
        );
        house1.setAmenities("Parking, Gym, Swimming Pool");
        house1.setFurnished(true);
        house1.setNumberOfRooms(2);
        house1.setNumberOfBathrooms(2);

        House house2 = new House(
            "m2",
            "Jane Smith",
            "9876543211",
            "1BHK Apartment",
            "15000",
            "Mumbai",
            "Bandra East",
            true,
            "owner2",
            "Jane Smith",
            "1234567891"
        );
        house2.setAmenities("Parking, Security");
        house2.setFurnished(false);
        house2.setNumberOfRooms(1);
        house2.setNumberOfBathrooms(1);

        House house3 = new House(
            "m3",
            "Rajesh Kumar",
            "9876543218",
            "3BHK Apartment",
            "45000",
            "Mumbai",
            "Powai",
            true,
            "owner9",
            "Rajesh Kumar",
            "1234567898"
        );
        house3.setAmenities("Parking, Gym, Swimming Pool, Garden");
        house3.setFurnished(true);
        house3.setNumberOfRooms(3);
        house3.setNumberOfBathrooms(3);

        // Hyderabad houses
        House house4 = new House(
            "h1",
            "Mike Johnson",
            "9876543212",
            "3BHK Villa",
            "35000",
            "Hyderabad",
            "Gachibowli",
            true,
            "owner3",
            "Mike Johnson",
            "1234567892"
        );
        house4.setAmenities("Parking, Garden, Security");
        house4.setFurnished(true);
        house4.setNumberOfRooms(3);
        house4.setNumberOfBathrooms(3);

        House house5 = new House(
            "h2",
            "Sarah Wilson",
            "9876543213",
            "2BHK Apartment",
            "20000",
            "Hyderabad",
            "Hitech City",
            true,
            "owner4",
            "Sarah Wilson",
            "1234567893"
        );
        house5.setAmenities("Parking, Security");
        house5.setFurnished(false);
        house5.setNumberOfRooms(2);
        house5.setNumberOfBathrooms(2);

        House house6 = new House(
            "h3",
            "Priya Sharma",
            "9876543219",
            "1BHK Apartment",
            "12000",
            "Hyderabad",
            "Madhapur",
            true,
            "owner10",
            "Priya Sharma",
            "1234567899"
        );
        house6.setAmenities("Parking");
        house6.setFurnished(true);
        house6.setNumberOfRooms(1);
        house6.setNumberOfBathrooms(1);

        // Chennai houses
        House house7 = new House(
            "c1",
            "David Brown",
            "9876543214",
            "1BHK Apartment",
            "12000",
            "Chennai",
            "T Nagar",
            true,
            "owner5",
            "David Brown",
            "1234567894"
        );
        house7.setAmenities("Parking");
        house7.setFurnished(false);
        house7.setNumberOfRooms(1);
        house7.setNumberOfBathrooms(1);

        House house8 = new House(
            "c2",
            "Lisa Anderson",
            "9876543215",
            "2BHK Apartment",
            "18000",
            "Chennai",
            "Anna Nagar",
            true,
            "owner6",
            "Lisa Anderson",
            "1234567895"
        );
        house8.setAmenities("Parking, Security");
        house8.setFurnished(true);
        house8.setNumberOfRooms(2);
        house8.setNumberOfBathrooms(2);

        House house9 = new House(
            "c3",
            "Arun Kumar",
            "9876543220",
            "3BHK Apartment",
            "30000",
            "Chennai",
            "Adyar",
            true,
            "owner11",
            "Arun Kumar",
            "1234567900"
        );
        house9.setAmenities("Parking, Gym, Swimming Pool");
        house9.setFurnished(true);
        house9.setNumberOfRooms(3);
        house9.setNumberOfBathrooms(3);

        // Bangalore houses
        House house10 = new House(
            "b1",
            "Tom Wilson",
            "9876543216",
            "3BHK Apartment",
            "30000",
            "Bangalore",
            "Koramangala",
            true,
            "owner7",
            "Tom Wilson",
            "1234567896"
        );
        house10.setAmenities("Parking, Gym, Swimming Pool");
        house10.setFurnished(true);
        house10.setNumberOfRooms(3);
        house10.setNumberOfBathrooms(3);

        House house11 = new House(
            "b2",
            "Emma Davis",
            "9876543217",
            "2BHK Apartment",
            "22000",
            "Bangalore",
            "Indiranagar",
            true,
            "owner8",
            "Emma Davis",
            "1234567897"
        );
        house11.setAmenities("Parking, Security");
        house11.setFurnished(false);
        house11.setNumberOfRooms(2);
        house11.setNumberOfBathrooms(2);

        House house12 = new House(
            "b3",
            "Rahul Singh",
            "9876543221",
            "1BHK Apartment",
            "15000",
            "Bangalore",
            "HSR Layout",
            true,
            "owner12",
            "Rahul Singh",
            "1234567901"
        );
        house12.setAmenities("Parking");
        house12.setFurnished(true);
        house12.setNumberOfRooms(1);
        house12.setNumberOfBathrooms(1);

        // Add houses to database
        database.child(house1.getId()).setValue(house1);
        database.child(house2.getId()).setValue(house2);
        database.child(house3.getId()).setValue(house3);
        database.child(house4.getId()).setValue(house4);
        database.child(house5.getId()).setValue(house5);
        database.child(house6.getId()).setValue(house6);
        database.child(house7.getId()).setValue(house7);
        database.child(house8.getId()).setValue(house8);
        database.child(house9.getId()).setValue(house9);
        database.child(house10.getId()).setValue(house10);
        database.child(house11.getId()).setValue(house11);
        database.child(house12.getId()).setValue(house12);
    }
} 