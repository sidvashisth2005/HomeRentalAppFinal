package com.example.houserentappproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class activity_selectcity extends AppCompatActivity {

    CardView cv_mumbai, cv_hyderabad, cv_chennai, cv_bangalore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectcity);

        cv_mumbai = findViewById(R.id.cv_img_mumbai);
        cv_hyderabad = findViewById(R.id.cv_img_hyderabad);
        cv_chennai = findViewById(R.id.cv_img_chennai);
        cv_bangalore = findViewById(R.id.cv_img_banglore);

        View.OnClickListener cityClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedCity = "";
                if (view.getId() == R.id.cv_img_mumbai) {
                    selectedCity = "Mumbai";
                } else if (view.getId() == R.id.cv_img_hyderabad) {
                    selectedCity = "Hyderabad";
                } else if (view.getId() == R.id.cv_img_chennai) {
                    selectedCity = "Chennai";
                } else if (view.getId() == R.id.cv_img_banglore) {
                    selectedCity = "Bangalore";
                }

                Intent dir = new Intent(getApplicationContext(), activity_house_listing.class);
                dir.putExtra("selected_city", selectedCity);
                startActivity(dir);
            }
        };

        cv_mumbai.setOnClickListener(cityClickListener);
        cv_hyderabad.setOnClickListener(cityClickListener);
        cv_chennai.setOnClickListener(cityClickListener);
        cv_bangalore.setOnClickListener(cityClickListener);
    }
}