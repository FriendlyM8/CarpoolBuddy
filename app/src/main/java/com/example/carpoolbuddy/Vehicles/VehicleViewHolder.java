package com.example.carpoolbuddy.Vehicles;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpoolbuddy.R;

public class VehicleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    protected TextView vehicleInfoText;
    VehicleRecyclerViewAdapter.OnVehicleListener onVehicleListener;

    public VehicleViewHolder(@NonNull View itemView, VehicleRecyclerViewAdapter.OnVehicleListener onVehicleListener) {
        super(itemView);

        vehicleInfoText = itemView.findViewById(R.id.vehicleTextView);
        this.onVehicleListener = onVehicleListener;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onVehicleListener.onVehicleClick(getAdapterPosition());
    }
}
