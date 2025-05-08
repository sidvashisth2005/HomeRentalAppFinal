package com.example.houserentappproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.slider.Slider;
import java.util.ArrayList;
import java.util.List;

public class FiltersActivity extends AppCompatActivity {
    private Spinner propertyTypeSpinner;
    private Slider minRentSlider;
    private Slider maxRentSlider;
    private CheckBox parkingCheckBox;
    private CheckBox gymCheckBox;
    private CheckBox swimmingPoolCheckBox;
    private CheckBox gardenCheckBox;
    private CheckBox securityCheckBox;
    private CheckBox furnishedCheckBox;
    private Button applyFiltersButton;
    private Button resetButton;
    private String selectedCity;
    private String selectedLandmark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        // Get city and landmark from intent
        selectedCity = getIntent().getStringExtra("selected_city");
        selectedLandmark = getIntent().getStringExtra("selected_landmark");

        // Initialize views
        propertyTypeSpinner = findViewById(R.id.propertyTypeSpinner);
        minRentSlider = findViewById(R.id.minRentSlider);
        maxRentSlider = findViewById(R.id.maxRentSlider);
        parkingCheckBox = findViewById(R.id.parkingCheckBox);
        gymCheckBox = findViewById(R.id.gymCheckBox);
        swimmingPoolCheckBox = findViewById(R.id.swimmingPoolCheckBox);
        gardenCheckBox = findViewById(R.id.gardenCheckBox);
        securityCheckBox = findViewById(R.id.securityCheckBox);
        furnishedCheckBox = findViewById(R.id.furnishedCheckBox);
        applyFiltersButton = findViewById(R.id.applyFiltersButton);
        resetButton = findViewById(R.id.resetButton);

        // Set up slider listeners to ensure min doesn't exceed max
        minRentSlider.addOnChangeListener((slider, value, fromUser) -> {
            if (value > maxRentSlider.getValue()) {
                maxRentSlider.setValue(value);
            }
        });

        maxRentSlider.addOnChangeListener((slider, value, fromUser) -> {
            if (value < minRentSlider.getValue()) {
                minRentSlider.setValue(value);
            }
        });

        // Apply filters button click listener
        applyFiltersButton.setOnClickListener(v -> {
            // Get selected property type
            String propertyType = propertyTypeSpinner.getSelectedItem().toString();

            // Get rent range
            int minRent = (int) minRentSlider.getValue();
            int maxRent = (int) maxRentSlider.getValue();

            // Get selected amenities
            List<String> amenities = new ArrayList<>();
            if (parkingCheckBox.isChecked()) amenities.add("Parking");
            if (gymCheckBox.isChecked()) amenities.add("Gym");
            if (swimmingPoolCheckBox.isChecked()) amenities.add("Swimming Pool");
            if (gardenCheckBox.isChecked()) amenities.add("Garden");
            if (securityCheckBox.isChecked()) amenities.add("Security");

            // Get furnished status
            boolean isFurnished = furnishedCheckBox.isChecked();

            // Launch house listing activity with filters
            Intent intent = new Intent(this, activity_house_listing.class);
            intent.putExtra("selected_city", selectedCity);
            intent.putExtra("selected_landmark", selectedLandmark);
            intent.putExtra("propertyType", propertyType);
            intent.putExtra("minRent", minRent);
            intent.putExtra("maxRent", maxRent);
            intent.putStringArrayListExtra("amenities", new ArrayList<>(amenities));
            intent.putExtra("isFurnished", isFurnished);
            startActivity(intent);
            finish();
        });

        // Reset button click listener
        resetButton.setOnClickListener(v -> {
            propertyTypeSpinner.setSelection(0);
            minRentSlider.setValue(10000);
            maxRentSlider.setValue(70000);
            parkingCheckBox.setChecked(false);
            gymCheckBox.setChecked(false);
            swimmingPoolCheckBox.setChecked(false);
            gardenCheckBox.setChecked(false);
            securityCheckBox.setChecked(false);
            furnishedCheckBox.setChecked(false);
        });
    }
} 