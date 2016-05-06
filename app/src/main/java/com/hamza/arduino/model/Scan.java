package com.hamza.arduino.model;

import java.util.Date;

/**
 * Created by Hamza90 on 5/6/2016.
 */
public class Scan {

    public String name;
    public int scanImage;
    public Date scanDate;

    public Scan (String name, Date scanDate, int scanImage){
        this.name = name;
        this.scanDate = scanDate;
        this.scanImage = scanImage;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Date getScanDate() {
        return scanDate;
    }

    public void setScanDate(Date scanDate) {
        this.scanDate = scanDate;
    }

    public void setScanImage(int scanImage) {
        this.scanImage = scanImage;
    }

    public int getScanImage() {
        return scanImage;
    }
}
