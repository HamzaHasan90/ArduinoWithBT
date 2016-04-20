package com.hamza.arduino.model;

/**
 * Created by Hamza on 3/25/2016.
 */
public class User {

    private String fname;
    private String lname;
    private String email;
    private String pwd;

    public User(String email, String pwd) {
        this.email = email;
        this.pwd = pwd;
    }

    public User(String name, String email, String pwd) {
        this.fname = name;
        this.email = email;
        this.pwd = pwd;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
