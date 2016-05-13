package com.hamza.arduino.model;

import java.util.Date;

/**
 * Created by Hamza90 on 5/6/2016.
 */
public class Scan {

    private  String name;
    private int scanImage;
    private String scanImageName;
    private String scanDate;

    public Scan (String name, String scanDate, int scanImage, String scanImageName){
        this.name = name;
        this.scanDate = scanDate;
        this.scanImage = scanImage;
        this.scanImageName = scanImageName;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getScanDate() {
        return scanDate;
    }

    public void setScanDate(String scanDate) {
        this.scanDate = scanDate;
    }

    public void setScanImage(int scanImage) {
        this.scanImage = scanImage;
    }

    public int getScanImage() {
        return scanImage;
    }

    public void setScanImageName(String scanImageName) {
        this.scanImageName = scanImageName;
    }

    public String getScanImageName() {
        return scanImageName;
    }
}
