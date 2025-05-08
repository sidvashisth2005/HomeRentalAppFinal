package com.example.houserentappproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    Context context;

    ArrayList<House> list;

    public MyAdapter(Context context, ArrayList<House> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        House house = list.get(position);
        holder.name.setText("Owner: " + house.getName());
        holder.number.setText("Contact: " + house.getNumber());
        holder.type.setText("Type: " + house.getType());
        holder.rent.setText("Rent: " + house.getRent());
        holder.landmark.setText("Landmark: " + house.getLandmark());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{


        TextView name, number, type, rent, landmark;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.houseName);
            number = itemView.findViewById(R.id.houseNumber);
            type = itemView.findViewById(R.id.houseType);
            rent = itemView.findViewById(R.id.houseRent);
            landmark = itemView.findViewById(R.id.houseLandmark);
        }
    }
}
