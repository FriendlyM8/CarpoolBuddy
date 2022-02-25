package com.example.carpoolbuddy.Vehicles;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpoolbuddy.R;

import java.util.ArrayList;

public class VehicleRecyclerViewAdapter extends RecyclerView.Adapter<VehicleViewHolder> {

    ArrayList<String> mData;
    private OnVehicleListener mOnVehicleListener;

    public VehicleRecyclerViewAdapter(ArrayList data, OnVehicleListener onVehicleListener){
        mData = data;
        this.mOnVehicleListener = onVehicleListener;
    }

    @NonNull
    @Override
    public VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_row_view, parent, false);

        return new VehicleViewHolder(myView, mOnVehicleListener);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleViewHolder holder, int position) {
        holder.vehicleInfoText.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface OnVehicleListener{
        void onVehicleClick(int position);
    }
}
