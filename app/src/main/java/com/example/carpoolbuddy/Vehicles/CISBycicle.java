package com.example.carpoolbuddy.Vehicles;

import java.util.ArrayList;

public class CISBycicle extends CISVehicles{
    private int weightCapacity;

    public CISBycicle(){
    }

    public CISBycicle(String owner, String model, String vehicleType, int capacity, int space, String vehicleID, boolean open, double basePrice, ArrayList ridersIDs, int weightCapacity) {
        super(owner, model, vehicleType, capacity, space, vehicleID, open, basePrice, ridersIDs);
        this.weightCapacity = weightCapacity;
    }

    public int getWeightCapacity() {
        return weightCapacity;
    }

    public void setWeightCapacity(int weightCapacity) {
        this.weightCapacity = weightCapacity;
    }

    @Override
    public String toString() {
        return "CISBycicle{" +
                "weightCapacity=" + weightCapacity +
                '}';
    }
}
