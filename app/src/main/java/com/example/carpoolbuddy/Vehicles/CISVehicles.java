package com.example.carpoolbuddy.Vehicles;

import java.io.Serializable;
import java.util.ArrayList;

public class CISVehicles implements Serializable {
    private String owner;
    private String model;
    private String vehicleType;
    private int capacity;
    private int space;
    private String vehicleID;
    private boolean open;
    private double basePrice;
    ArrayList RidersIDs = new ArrayList();

    public CISVehicles(){
    }

    public CISVehicles(String owner, String model, String vehicleType, int capacity, int space, String vehicleID, boolean open, double basePrice, ArrayList ridersIDs) {
        this.owner = owner;
        this.model = model;
        this.vehicleType = vehicleType;
        this.capacity = capacity;
        this.space = space;
        this.vehicleID = vehicleID;
        this.open = open;
        this.basePrice = basePrice;
        RidersIDs = ridersIDs;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getSpace() {
        return space;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    public String getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public ArrayList getRidersIDs() {
        return RidersIDs;
    }

    public void setRidersIDs(ArrayList ridersIDs) {
        RidersIDs = ridersIDs;
    }

    @Override
    public String toString() {
        return "CISVehicles{" +
                "owner='" + owner + '\'' +
                ", model='" + model + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", capacity=" + capacity +
                ", space=" + space +
                ", vehicleID='" + vehicleID + '\'' +
                ", open=" + open +
                ", basePrice=" + basePrice +
                ", RidersIDs=" + RidersIDs +
                '}';
    }
}
