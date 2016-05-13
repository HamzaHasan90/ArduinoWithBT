package com.hamza.arduino.model;

/**
 * Created by Hamza on 4/7/2016.
 */
public class Doctor extends User {

    private String speciality;
    private int id;

    public Doctor(String fName, String lName,String speciality, String email, String pwd) {
        super(fName, lName, email, pwd);
        this.speciality = speciality;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
