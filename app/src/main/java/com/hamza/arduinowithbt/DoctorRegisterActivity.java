package com.hamza.arduinowithbt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hamza.arduino.DB.LocalStore;
import com.hamza.arduino.model.Doctor;

public class DoctorRegisterActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnRegister;
    EditText etFName, etLName, etSpecialty, etEmail, etPwd, etConfirmPwd;
    LocalStore localStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_register);

        btnRegister = (Button) findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);
        etFName = (EditText) findViewById(R.id.et_first_name);
        etLName = (EditText) findViewById(R.id.et_last_name);
        etSpecialty = (EditText) findViewById(R.id.et_disease);
        etEmail = (EditText) findViewById(R.id.et_email);
        etPwd = (EditText) findViewById(R.id.et_conf_pwd);
        etConfirmPwd = (EditText) findViewById(R.id.et_password);
        localStore = new LocalStore(this);
    }

    @Override
    public void onClick(View v) {
        Doctor doctor;
        switch (v.getId()) {
            case R.id.btn_register: {

                String fname = etFName.getText().toString();
                String lname = etLName.getText().toString();
                String speciality = etSpecialty.getText().toString();
                String email = etEmail.getText().toString();
                String pwd = etPwd.getText().toString();
                String confPwd = etPwd.getText().toString();
                doctor = new Doctor(email, pwd);
                doctor.setFname(fname);
                doctor.setLname(lname);
                doctor.setSpeciality(speciality);


                if (!pwd.equals(confPwd)) {
                    Toast.makeText(DoctorRegisterActivity.this, "Please make sure you confirm your password correctly",
                            Toast.LENGTH_SHORT).show();

                } else {
                    localStore.storeDoctorData(doctor);
                    localStore.setLoggedUsername(doctor.getFname());
                    localStore.setUserLoggedIn(true);

                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }

            }

        }


    }
}
