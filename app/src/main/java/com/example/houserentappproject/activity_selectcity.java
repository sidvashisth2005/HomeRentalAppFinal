package com.example.houserentappproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class activity_selectcity extends AppCompatActivity {

    private CardView cv_mumbai, cv_hyderabad, cv_chennai, cv_bangalore;
    private EditText landmarkInput;
    private LinearLayout landmarkContainer;
    private View showHousesButton;
    private SessionManager sessionManager;
    private String selectedCity = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectcity);

        sessionManager = new SessionManager(this);

        // Initialize views
        cv_mumbai = findViewById(R.id.cv_img_mumbai);
        cv_hyderabad = findViewById(R.id.cv_img_hyderabad);
        cv_chennai = findViewById(R.id.cv_img_chennai);
        cv_bangalore = findViewById(R.id.cv_img_banglore);
        landmarkInput = findViewById(R.id.landmarkInput);
        landmarkContainer = findViewById(R.id.landmarkContainer);
        showHousesButton = findViewById(R.id.showHousesButton);

        // City selection click listener
        View.OnClickListener cityClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.cv_img_mumbai) {
                    selectedCity = "Mumbai";
                } else if (view.getId() == R.id.cv_img_hyderabad) {
                    selectedCity = "Hyderabad";
                } else if (view.getId() == R.id.cv_img_chennai) {
                    selectedCity = "Chennai";
                } else if (view.getId() == R.id.cv_img_banglore) {
                    selectedCity = "Bangalore";
                }

                // Show landmark input after city selection
                landmarkContainer.setVisibility(View.VISIBLE);
                // Scroll to landmark input
                landmarkContainer.requestFocus();
            }
        };

        // Show houses button click listener
        showHousesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String landmark = landmarkInput.getText().toString().trim();
                if (TextUtils.isEmpty(landmark)) {
                    Toast.makeText(activity_selectcity.this, 
                        "Please enter a landmark", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Save city and landmark to session
                sessionManager.saveSelectedCity(selectedCity);
                sessionManager.saveSelectedLandmark(landmark);

                // Proceed to house listing
                Intent intent = new Intent(getApplicationContext(), activity_house_listing.class);
                intent.putExtra("selected_city", selectedCity);
                intent.putExtra("selected_landmark", landmark);
                startActivity(intent);
                finish();
            }
        });

        // Set click listeners for city cards
        cv_mumbai.setOnClickListener(cityClickListener);
        cv_hyderabad.setOnClickListener(cityClickListener);
        cv_chennai.setOnClickListener(cityClickListener);
        cv_bangalore.setOnClickListener(cityClickListener);
    }
}