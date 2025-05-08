package com.example.houserentappproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Spinner citySpinner;
    private Spinner landmarkSpinner;
    private Button searchButton;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(this);

        // Initialize views
        citySpinner = findViewById(R.id.citySpinner);
        landmarkSpinner = findViewById(R.id.landmarkSpinner);
        searchButton = findViewById(R.id.searchButton);

        // Setup city spinner with custom layout
        ArrayAdapter<CharSequence> cityAdapter = ArrayAdapter.createFromResource(this,
                R.array.cities, R.layout.spinner_item);
        cityAdapter.setDropDownViewResource(R.layout.spinner_item);
        citySpinner.setAdapter(cityAdapter);

        // City selection listener
        citySpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                String selectedCity = parent.getItemAtPosition(position).toString();
                updateLandmarks(selectedCity);
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
                landmarkSpinner.setAdapter(null);
            }
        });

        // Search button click listener
        searchButton.setOnClickListener(v -> proceedToFilters());

        // Check if user is already logged in
        if (sessionManager.isLoggedIn()) {
            startActivity(new Intent(MainActivity.this, activity_home.class));
            finish();
        }
    }

    private void updateLandmarks(String city) {
        int landmarkArrayId;
        switch (city) {
            case "Mumbai":
                landmarkArrayId = R.array.mumbai_landmarks;
                break;
            case "Hyderabad":
                landmarkArrayId = R.array.hyderabad_landmarks;
                break;
            case "Bangalore":
                landmarkArrayId = R.array.bangalore_landmarks;
                break;
            case "Chennai":
                landmarkArrayId = R.array.chennai_landmarks;
                break;
            default:
                landmarkArrayId = 0;
                break;
        }

        if (landmarkArrayId != 0) {
            ArrayAdapter<CharSequence> landmarkAdapter = ArrayAdapter.createFromResource(this,
                    landmarkArrayId, R.layout.spinner_item);
            landmarkAdapter.setDropDownViewResource(R.layout.spinner_item);
            landmarkSpinner.setAdapter(landmarkAdapter);
        } else {
            landmarkSpinner.setAdapter(null);
        }
    }

    private void proceedToFilters() {
        String selectedCity = citySpinner.getSelectedItem().toString();
        String selectedLandmark = landmarkSpinner.getSelectedItem() != null ? 
                                landmarkSpinner.getSelectedItem().toString() : "";

        if (selectedCity.equals("Select City")) {
            Toast.makeText(this, "Please select a city", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save selections to session
        sessionManager.saveSelectedCity(selectedCity);
        sessionManager.saveSelectedLandmark(selectedLandmark);

        // Launch FiltersActivity
        Intent intent = new Intent(this, FiltersActivity.class);
        intent.putExtra("selected_city", selectedCity);
        intent.putExtra("selected_landmark", selectedLandmark);
        startActivity(intent);
    }
}