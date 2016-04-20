package com.hamza.arduino.model;

/**
 * Created by Hamza on 4/7/2016.
 */
public class Doctor extends User {

    private String speciality;

    public Doctor(String email, String pwd) {
        super(email, pwd);
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
}
