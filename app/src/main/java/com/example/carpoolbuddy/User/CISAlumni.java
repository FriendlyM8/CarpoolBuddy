package com.example.carpoolbuddy.User;

import java.util.ArrayList;

public class CISAlumni extends CISUser{
    private int gradYear;

    public CISAlumni(){
    }

    public CISAlumni(String email, String password, String userID, String userType, double money, int bookedTimes, int totalBookedTimes, ArrayList ownedVehicles, int gradYear) {
        super(email, password, userID, userType, money, bookedTimes, totalBookedTimes, ownedVehicles);
        this.gradYear = gradYear;
    }

    public int getGradYear() {
        return gradYear;
    }

    public void setGradYear(int gradYear) {
        this.gradYear = gradYear;
    }

    @Override
    public String toString() {
        return "CISAlumni{" +
                "gradYear=" + gradYear +
                '}';
    }
}
