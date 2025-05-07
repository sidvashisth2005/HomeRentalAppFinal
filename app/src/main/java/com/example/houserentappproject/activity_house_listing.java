package com.example.houserentappproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class activity_house_listing extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapter myAdapter;
    ArrayList<House> list;
    String selectedCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_listing);

        // Get the selected city from intent
        selectedCity = getIntent().getStringExtra("selected_city");
        if (selectedCity == null) {
            selectedCity = "";
        }

        recyclerView = findViewById(R.id.housesRecyclerView);
        database = FirebaseDatabase.getInstance().getReference("House List");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new MyAdapter(this, list);
        recyclerView.setAdapter(myAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear(); // Clear the list before adding new items
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    House house = dataSnapshot.getValue(House.class);
                    if (house != null) {
                        // If no city is selected or if the house's city matches the selected city
                        if (selectedCity.isEmpty() || house.getCity().equalsIgnoreCase(selectedCity)) {
                            list.add(house);
                        }
                    }
                }
                myAdapter.notifyDataSetChanged();
                
                if (list.isEmpty()) {
                    Toast.makeText(activity_house_listing.this, 
                        "No houses found in " + selectedCity, 
                        Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(activity_house_listing.this, 
                    "Error loading houses: " + error.getMessage(), 
                    Toast.LENGTH_SHORT).show();
            }
        });
    }
}