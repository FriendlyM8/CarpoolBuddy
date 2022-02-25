package com.example.carpoolbuddy.User;

import java.util.ArrayList;

public class CISParent extends CISUser{
    private int ChildAmount;

    public CISParent(){
    }

    public CISParent(String email, String password, String userID, String userType, double money, int bookedTimes, int totalBookedTimes, ArrayList ownedVehicles, int childAmount) {
        super(email, password, userID, userType, money, bookedTimes, totalBookedTimes, ownedVehicles);
        ChildAmount = childAmount;
    }

    public int getChildAmount() {
        return ChildAmount;
    }

    public void setChildAmount(int childAmount) {
        ChildAmount = childAmount;
    }

    @Override
    public String toString() {
        return "CISParent{" +
                "ChildAmount=" + ChildAmount +
                '}';
    }
}
