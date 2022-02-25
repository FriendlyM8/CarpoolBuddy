package com.example.carpoolbuddy.User;

import java.util.ArrayList;

public class CISStudent extends CISUser{
    private String studentClass;

    public CISStudent(){
    }

    public CISStudent(String email, String password, String userID, String userType, double money, int bookedTimes, int totalBookedTimes, ArrayList ownedVehicles, String studentClass) {
        super(email, password, userID, userType, money, bookedTimes, totalBookedTimes, ownedVehicles);
        this.studentClass = studentClass;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    @Override
    public String toString() {
        return "CISStudent{" +
                "studentClass='" + studentClass + '\'' +
                '}';
    }
}
