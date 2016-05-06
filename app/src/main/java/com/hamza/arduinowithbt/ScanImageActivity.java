package com.hamza.arduinowithbt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import com.hamza.arduino.adapters.DoctorsAdapter;
import com.hamza.arduino.model.Doctor;

public class ScanImageActivity extends AppCompatActivity {
    private ImageView scannedImage;
    private ListView doctorsListView;
    private DoctorsAdapter doctorsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_image);
        scannedImage = (ImageView) findViewById(R.id.iv_full_scan_image);
        doctorsListView = (ListView) findViewById(R.id.docs_listView);
        doctorsAdapter = new DoctorsAdapter(this,R.layout.doc_list_item_row,processDoctors());
        doctorsListView.setAdapter(doctorsAdapter);

        Intent intent = getIntent();
        int recievedImage = intent.getExtras().getInt(MainActivity.SCAN_IMAGE);

        scannedImage.setImageResource(recievedImage);

    }

    private Doctor[] processDoctors() {

        Doctor[] doctors = {
                new Doctor(1, "Dr. John", "Allen ","Speciality One "),
                new Doctor(2, "Dr. Jon", "Snow ","Speciality Two "),
                new Doctor(3, "Dr. Arya", "Stark ","Speciality Three "),
        };
        return doctors;
    }
}
