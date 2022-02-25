package com.example.carpoolbuddy.User;

import java.util.ArrayList;

public class CISUser {
    private String email;
    private String password;
    private String userID;
    private String userType;
    private double money;
    private int bookedTimes;
    private int totalBookedTimes;
    ArrayList ownedVehicles = new ArrayList();

    //No argument for firestore
    public CISUser(){
    }

    public CISUser(String email, String password, String userID, String userType, double money, int bookedTimes, int totalBookedTimes, ArrayList ownedVehicles) {
        this.email = email;
        this.password = password;
        this.userID = userID;
        this.userType = userType;
        this.money = money;
        this.bookedTimes = bookedTimes;
        this.totalBookedTimes = totalBookedTimes;
        this.ownedVehicles = ownedVehicles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getBookedTimes() {
        return bookedTimes;
    }

    public void setBookedTimes(int bookedTimes) {
        this.bookedTimes = bookedTimes;
    }

    public int getTotalBookedTimes() {
        return totalBookedTimes;
    }

    public void setTotalBookedTimes(int totalBookedTimes) {
        this.totalBookedTimes = totalBookedTimes;
    }

    public ArrayList getOwnedVehicles() {
        return ownedVehicles;
    }

    public void setOwnedVehicles(ArrayList ownedVehicles) {
        this.ownedVehicles = ownedVehicles;
    }

    @Override
    public String toString() {
        return "CISUser{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userID='" + userID + '\'' +
                ", userType='" + userType + '\'' +
                ", money=" + money +
                ", bookedTimes=" + bookedTimes +
                ", totalBookedTimes=" + totalBookedTimes +
                ", ownedVehicles=" + ownedVehicles +
                '}';
    }
}



