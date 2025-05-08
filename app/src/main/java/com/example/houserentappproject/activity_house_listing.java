package com.example.houserentappproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class activity_house_listing extends AppCompatActivity {
    private static final String TAG = "HouseListing";

    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private ArrayList<House> list;
    private String selectedCity;
    private String selectedLandmark;
    private ProgressBar loadingIndicator;
    private TextView emptyView;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_listing);

        try {
            sessionManager = new SessionManager(this);

            // Get the selected city and landmark from intent or session
            selectedCity = getIntent().getStringExtra("selected_city");
            selectedLandmark = getIntent().getStringExtra("selected_landmark");

            // If not in intent, try to get from session
            if (selectedCity == null) {
                selectedCity = sessionManager.getSelectedCity();
            }
            if (selectedLandmark == null) {
                selectedLandmark = sessionManager.getSelectedLandmark();
            }

            Log.d(TAG, "Selected City: " + selectedCity);
            Log.d(TAG, "Selected Landmark: " + selectedLandmark);

            if (selectedCity == null || selectedCity.isEmpty()) {
                Toast.makeText(this, "No city selected", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            // Initialize views
            recyclerView = findViewById(R.id.housesRecyclerView);
            loadingIndicator = findViewById(R.id.loadingIndicator);
            emptyView = findViewById(R.id.emptyView);

            // Setup RecyclerView
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            list = new ArrayList<>();
            myAdapter = new MyAdapter(this, list);
            recyclerView.setAdapter(myAdapter);

            // Show loading indicator
            loadingIndicator.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.GONE);

            // Load test data and then apply filters
            addTestData();
            loadHouses();
        } catch (Exception e) {
            Log.e(TAG, "Error in onCreate: " + e.getMessage(), e);
            Toast.makeText(this, "Error initializing: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void loadHouses() {
        Log.d(TAG, "Starting to load houses");
        Log.d(TAG, "Selected city: " + selectedCity);
        Log.d(TAG, "Selected landmark: " + selectedLandmark);

        loadingIndicator.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);

        // Get filter results if any
        String propertyType = getIntent().getStringExtra("propertyType");
        int minRent = getIntent().getIntExtra("minRent", 0);
        int maxRent = getIntent().getIntExtra("maxRent", Integer.MAX_VALUE);
        ArrayList<String> amenities = getIntent().getStringArrayListExtra("amenities");
        boolean isFurnished = getIntent().getBooleanExtra("isFurnished", false);

        Log.d(TAG, "Filters - Property Type: " + propertyType);
        Log.d(TAG, "Filters - Rent Range: " + minRent + " - " + maxRent);
        Log.d(TAG, "Filters - Amenities: " + amenities);
        Log.d(TAG, "Filters - Furnished: " + isFurnished);

        List<House> filteredHouses = new ArrayList<>();
        for (House house : list) {
            boolean matchesCity = selectedCity == null || selectedCity.isEmpty() || 
                                house.getCity().equalsIgnoreCase(selectedCity);
            boolean matchesLandmark = selectedLandmark == null || selectedLandmark.isEmpty() || 
                                    house.getLandmark().toLowerCase().contains(selectedLandmark.toLowerCase());
            boolean matchesPropertyType = propertyType == null || propertyType.equals("Any") || 
                                        house.getType().equals(propertyType);
            boolean matchesRent = Integer.parseInt(house.getRent()) >= minRent && 
                                Integer.parseInt(house.getRent()) <= maxRent;
            boolean matchesFurnished = !isFurnished || house.isFurnished();
            boolean matchesAmenities = amenities == null || amenities.isEmpty() || 
                                     (house.getAmenities() != null && 
                                      house.getAmenities().toLowerCase().contains(amenities.get(0).toLowerCase()));

            if (matchesCity && matchesLandmark && matchesPropertyType && 
                matchesRent && matchesFurnished && matchesAmenities) {
                filteredHouses.add(house);
                Log.d(TAG, "House matches filters: " + house.getName());
            } else {
                Log.d(TAG, "House does not match filters: " + house.getName());
            }
        }

        Log.d(TAG, "Total houses found: " + filteredHouses.size());

        if (filteredHouses.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            Log.d(TAG, "No houses found matching the criteria");
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            list.clear();
            list.addAll(filteredHouses);
            myAdapter.notifyDataSetChanged();
            Log.d(TAG, "Updated RecyclerView with filtered houses");
        }

        loadingIndicator.setVisibility(View.GONE);
    }

    private void addTestData() {
        // Mumbai houses
        House house1 = new House(
            "m1",
            "Sunset Apartments",
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
        list.add(house1);

        House house2 = new House(
            "m2",
            "Green Valley Residency",
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
        list.add(house2);

        House house3 = new House(
            "m3",
            "Marine Drive Heights",
            "9876543218",
            "3BHK Apartment",
            "45000",
            "Mumbai",
            "Marine Drive",
            true,
            "owner9",
            "Rajesh Kumar",
            "1234567898"
        );
        house3.setAmenities("Parking, Gym, Swimming Pool, Garden");
        house3.setFurnished(true);
        house3.setNumberOfRooms(3);
        house3.setNumberOfBathrooms(3);
        list.add(house3);

        House house4 = new House(
            "m4",
            "Powai Lake View",
            "9876543219",
            "2BHK Apartment",
            "35000",
            "Mumbai",
            "Powai",
            true,
            "owner10",
            "Priya Sharma",
            "1234567899"
        );
        house4.setAmenities("Parking, Garden, Security");
        house4.setFurnished(true);
        house4.setNumberOfRooms(2);
        house4.setNumberOfBathrooms(2);
        list.add(house4);

        House house5 = new House(
            "m5",
            "Juhu Beach Villa",
            "9876543227",
            "4BHK Villa",
            "65000",
            "Mumbai",
            "Juhu",
            true,
            "owner18",
            "Amit Shah",
            "1234567907"
        );
        house5.setAmenities("Parking, Garden, Swimming Pool, Security, Beach Access");
        house5.setFurnished(true);
        house5.setNumberOfRooms(4);
        house5.setNumberOfBathrooms(4);
        list.add(house5);

        House house6 = new House(
            "m6",
            "Worli Sea Face",
            "9876543228",
            "3BHK Apartment",
            "55000",
            "Mumbai",
            "Worli",
            true,
            "owner19",
            "Deepika Singh",
            "1234567908"
        );
        house6.setAmenities("Parking, Gym, Swimming Pool, Sea View");
        house6.setFurnished(true);
        house6.setNumberOfRooms(3);
        house6.setNumberOfBathrooms(3);
        list.add(house6);

        // Hyderabad houses
        House house7 = new House(
            "h1",
            "Tech Park Residences",
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
        house7.setAmenities("Parking, Garden, Security");
        house7.setFurnished(true);
        house7.setNumberOfRooms(3);
        house7.setNumberOfBathrooms(3);
        list.add(house7);

        House house8 = new House(
            "h2",
            "Hitech City Apartments",
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
        house8.setAmenities("Parking, Security");
        house8.setFurnished(false);
        house8.setNumberOfRooms(2);
        house8.setNumberOfBathrooms(2);
        list.add(house8);

        House house9 = new House(
            "h3",
            "Madhapur Heights",
            "9876543220",
            "1BHK Apartment",
            "12000",
            "Hyderabad",
            "Madhapur",
            true,
            "owner11",
            "Arun Kumar",
            "1234567900"
        );
        house9.setAmenities("Parking, Gym");
        house9.setFurnished(true);
        house9.setNumberOfRooms(1);
        house9.setNumberOfBathrooms(1);
        list.add(house9);

        House house10 = new House(
            "h4",
            "Jubilee Hills Villa",
            "9876543221",
            "4BHK Villa",
            "55000",
            "Hyderabad",
            "Jubilee Hills",
            true,
            "owner12",
            "Rahul Singh",
            "1234567901"
        );
        house10.setAmenities("Parking, Garden, Swimming Pool, Security");
        house10.setFurnished(true);
        house10.setNumberOfRooms(4);
        house10.setNumberOfBathrooms(4);
        list.add(house10);

        House house11 = new House(
            "h5",
            "Banjara Hills Residency",
            "9876543229",
            "3BHK Apartment",
            "45000",
            "Hyderabad",
            "Banjara Hills",
            true,
            "owner20",
            "Kiran Reddy",
            "1234567909"
        );
        house11.setAmenities("Parking, Gym, Swimming Pool, Garden");
        house11.setFurnished(true);
        house11.setNumberOfRooms(3);
        house11.setNumberOfBathrooms(3);
        list.add(house11);

        House house12 = new House(
            "h6",
            "Secunderabad Heights",
            "9876543230",
            "2BHK Apartment",
            "18000",
            "Hyderabad",
            "Secunderabad",
            true,
            "owner21",
            "Vijay Kumar",
            "1234567910"
        );
        house12.setAmenities("Parking, Security");
        house12.setFurnished(false);
        house12.setNumberOfRooms(2);
        house12.setNumberOfBathrooms(2);
        list.add(house12);

        // Bangalore houses
        House house13 = new House(
            "b1",
            "Koramangala Heights",
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
        house13.setAmenities("Parking, Gym, Swimming Pool");
        house13.setFurnished(true);
        house13.setNumberOfRooms(3);
        house13.setNumberOfBathrooms(3);
        list.add(house13);

        House house14 = new House(
            "b2",
            "Indiranagar Residency",
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
        house14.setAmenities("Parking, Security");
        house14.setFurnished(false);
        house14.setNumberOfRooms(2);
        house14.setNumberOfBathrooms(2);
        list.add(house14);

        House house15 = new House(
            "b3",
            "HSR Layout Apartments",
            "9876543222",
            "1BHK Apartment",
            "15000",
            "Bangalore",
            "HSR Layout",
            true,
            "owner13",
            "Neha Gupta",
            "1234567902"
        );
        house15.setAmenities("Parking");
        house15.setFurnished(true);
        house15.setNumberOfRooms(1);
        house15.setNumberOfBathrooms(1);
        list.add(house15);

        House house16 = new House(
            "b4",
            "Whitefield Villa",
            "9876543223",
            "4BHK Villa",
            "50000",
            "Bangalore",
            "Whitefield",
            true,
            "owner14",
            "Vikram Patel",
            "1234567903"
        );
        house16.setAmenities("Parking, Garden, Swimming Pool, Security");
        house16.setFurnished(true);
        house16.setNumberOfRooms(4);
        house16.setNumberOfBathrooms(4);
        list.add(house16);

        House house17 = new House(
            "b5",
            "Electronic City Residency",
            "9876543231",
            "2BHK Apartment",
            "20000",
            "Bangalore",
            "Electronic City",
            true,
            "owner22",
            "Rajesh Iyer",
            "1234567911"
        );
        house17.setAmenities("Parking, Security");
        house17.setFurnished(true);
        house17.setNumberOfRooms(2);
        house17.setNumberOfBathrooms(2);
        list.add(house17);

        House house18 = new House(
            "b6",
            "Marathahalli Heights",
            "9876543232",
            "3BHK Apartment",
            "35000",
            "Bangalore",
            "Marathahalli",
            true,
            "owner23",
            "Priya Menon",
            "1234567912"
        );
        house18.setAmenities("Parking, Gym, Swimming Pool");
        house18.setFurnished(true);
        house18.setNumberOfRooms(3);
        house18.setNumberOfBathrooms(3);
        list.add(house18);

        // Chennai houses
        House house19 = new House(
            "c1",
            "T Nagar Residency",
            "9876543224",
            "2BHK Apartment",
            "18000",
            "Chennai",
            "T Nagar",
            true,
            "owner15",
            "Suresh Kumar",
            "1234567904"
        );
        house19.setAmenities("Parking, Security");
        house19.setFurnished(true);
        house19.setNumberOfRooms(2);
        house19.setNumberOfBathrooms(2);
        list.add(house19);

        House house20 = new House(
            "c2",
            "Anna Nagar Heights",
            "9876543225",
            "3BHK Apartment",
            "28000",
            "Chennai",
            "Anna Nagar",
            true,
            "owner16",
            "Lakshmi Devi",
            "1234567905"
        );
        house20.setAmenities("Parking, Gym, Swimming Pool");
        house20.setFurnished(true);
        house20.setNumberOfRooms(3);
        house20.setNumberOfBathrooms(3);
        list.add(house20);

        House house21 = new House(
            "c3",
            "Adyar Villa",
            "9876543226",
            "4BHK Villa",
            "45000",
            "Chennai",
            "Adyar",
            true,
            "owner17",
            "Ravi Shankar",
            "1234567906"
        );
        house21.setAmenities("Parking, Garden, Swimming Pool, Security");
        house21.setFurnished(true);
        house21.setNumberOfRooms(4);
        house21.setNumberOfBathrooms(4);
        list.add(house21);

        House house22 = new House(
            "c4",
            "Velachery Residency",
            "9876543233",
            "2BHK Apartment",
            "20000",
            "Chennai",
            "Velachery",
            true,
            "owner24",
            "Karthik Raman",
            "1234567913"
        );
        house22.setAmenities("Parking, Security");
        house22.setFurnished(false);
        house22.setNumberOfRooms(2);
        house22.setNumberOfBathrooms(2);
        list.add(house22);

        House house23 = new House(
            "c5",
            "OMR Tech Park",
            "9876543234",
            "3BHK Apartment",
            "32000",
            "Chennai",
            "OMR",
            true,
            "owner25",
            "Anand Kumar",
            "1234567914"
        );
        house23.setAmenities("Parking, Gym, Swimming Pool");
        house23.setFurnished(true);
        house23.setNumberOfRooms(3);
        house23.setNumberOfBathrooms(3);
        list.add(house23);
    }
}