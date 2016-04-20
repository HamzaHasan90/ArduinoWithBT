package com.hamza.arduino.model;

/**
 * Created by Hamza on 4/7/2016.
 */
public class Patient extends User {

    private String disease;
    private int age;

    public Patient(String email, String pwd) {
        super(email, pwd);
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
