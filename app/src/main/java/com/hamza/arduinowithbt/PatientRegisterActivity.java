package com.hamza.arduinowithbt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hamza.arduino.DB.LocalStore;
import com.hamza.arduino.model.Patient;

public class PatientRegisterActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnRegister;
    EditText etFName, etLName, etDisease, etEmail, etPwd, etConfirmPwd;
    LocalStore localStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_register);

        btnRegister = (Button) findViewById(R.id.btn_register);
        etFName = (EditText) findViewById(R.id.et_first_name);
        etLName = (EditText) findViewById(R.id.et_last_name);
        etDisease = (EditText) findViewById(R.id.et_disease);
        etEmail = (EditText) findViewById(R.id.et_email);
        etPwd = (EditText) findViewById(R.id.et_conf_pwd);
        etConfirmPwd = (EditText) findViewById(R.id.et_password);
        btnRegister = (Button) findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);
        localStore = new LocalStore(this);
    }

    @Override
    public void onClick(View v) {

        Patient patient;

        switch (v.getId()) {
            case R.id.btn_register: {
                String fName = etFName.getText().toString();
                String lName = etLName.getText().toString();
                String disease = etDisease.getText().toString();
                String email = etEmail.getText().toString();
                String pwd = etPwd.getText().toString();
                String confPwd = etPwd.getText().toString();
                patient = new Patient(email, pwd);
                patient.setFname(fName);
                patient.setLname(lName);
                patient.setDisease(disease);

                if (!pwd.equals(confPwd)) {
                    Toast.makeText(PatientRegisterActivity.this, "Please make sure you confirm your password correctly",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(this, MainActivity.class);

                    localStore.storePatientData(patient);
                    localStore.setLoggedUsername(patient.getFname());
                    localStore.setUserLoggedIn(true);

                    startActivity(intent);
                }

            }

        }
    }
}
