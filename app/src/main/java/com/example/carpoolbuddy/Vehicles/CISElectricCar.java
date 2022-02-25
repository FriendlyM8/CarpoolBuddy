package com.example.carpoolbuddy.Vehicles;

import java.util.ArrayList;

public class CISElectricCar extends CISVehicles{
    private int range;

    public CISElectricCar(){
    }

    public CISElectricCar(String owner, String model, String vehicleType, int capacity, int space, String vehicleID, boolean open, double basePrice, ArrayList ridersIDs, int range) {
        super(owner, model, vehicleType, capacity, space, vehicleID, open, basePrice, ridersIDs);
        this.range = range;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    @Override
    public String toString() {
        return "CISElectricCar{" +
                "range=" + range +
                '}';
    }
}
