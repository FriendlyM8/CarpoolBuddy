package com.example.carpoolbuddy.User;

import java.util.ArrayList;

public class CISTeacher extends CISUser{
    private String inSchoolTitle;

    public CISTeacher(){

    }

    public CISTeacher(String email, String password, String userID, String userType, double money, int bookedTimes, int totalBookedTimes, ArrayList ownedVehicles, String inSchoolTitle) {
        super(email, password, userID, userType, money, bookedTimes, totalBookedTimes, ownedVehicles);
        this.inSchoolTitle = inSchoolTitle;
    }

    public String getInSchoolTitle() {
        return inSchoolTitle;
    }

    public void setInSchoolTitle(String inSchoolTitle) {
        this.inSchoolTitle = inSchoolTitle;
    }

    @Override
    public String toString() {
        return "CISTeacher{" +
                "inSchoolTitle='" + inSchoolTitle + '\'' +
                '}';
    }
}
