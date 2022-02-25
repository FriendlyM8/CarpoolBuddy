package com.example.carpoolbuddy.Vehicles;

import java.util.ArrayList;

public class CISHelicopter extends CISVehicles{
    private int maxAltitude;

    public CISHelicopter(){
    }

    public CISHelicopter(String owner, String model, String vehicleType, int capacity, int space, String vehicleID, boolean open, double basePrice, ArrayList ridersIDs, int maxAltitude) {
        super(owner, model, vehicleType, capacity, space, vehicleID, open, basePrice, ridersIDs);
        this.maxAltitude = maxAltitude;
    }

    public int getMaxAltitude() {
        return maxAltitude;
    }

    public void setMaxAltitude(int maxAltitude) {
        this.maxAltitude = maxAltitude;
    }

    @Override
    public String toString() {
        return "CISHelicopter{" +
                "maxAltitude=" + maxAltitude +
                '}';
    }
}
