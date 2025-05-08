package com.example.houserentappproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class activity_home extends AppCompatActivity {
    private Button btnSearch, btnLogout;
    private TextView txtWelcome;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize SessionManager
        sessionManager = new SessionManager(this);

        // Initialize views
        btnSearch = findViewById(R.id.btn_search);
        btnLogout = findViewById(R.id.btn_logout);
        txtWelcome = findViewById(R.id.txt_welcome_home);

        // Set welcome message with user's name if available
        String userName = sessionManager.getUserName();
        if (userName != null && !userName.isEmpty()) {
            txtWelcome.setText("Welcome, " + userName);
        }

        // Set click listeners
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity_home.this, activity_selectcity.class));
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logout();
                startActivity(new Intent(activity_home.this, MainActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Check if user is logged in
        if (!sessionManager.isLoggedIn()) {
            startActivity(new Intent(activity_home.this, MainActivity.class));
            finish();
        }
    }
} 