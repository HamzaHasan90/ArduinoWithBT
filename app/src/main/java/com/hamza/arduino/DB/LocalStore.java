package com.hamza.arduino.DB;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.hamza.arduino.model.Doctor;
import com.hamza.arduino.model.Patient;
import com.hamza.arduino.model.User;

/**
 * Created by Hamza on 4/7/2016.
 */
public class LocalStore {

    public static final String storage_file_name = "userDetails";
    public static final String LOGGED_IN = "loggedIn";
    public static final String LOGGED_USERNAME = "loggedUsername";
    public static final String MAC_ADDRESS = "MacAddress";
    public SharedPreferences sharedPreferences;


    public LocalStore(Context context) {
        sharedPreferences = context.getSharedPreferences(storage_file_name, 0);
    }

    public void storePatientData(Patient patient) {
        SharedPreferences.Editor storageEditor = sharedPreferences.edit();
        storageEditor.putString(patient.getFname(), patient.getFname());
        storageEditor.putString(patient.getLname(), patient.getLname());
        storageEditor.putString(patient.getDisease(), patient.getDisease());
        storageEditor.putString(patient.getEmail(), patient.getEmail());
        storageEditor.putString(patient.getPwd(), patient.getPwd());
        storageEditor.commit();
    }

    public void storeDoctorData(Doctor doctor) {
        SharedPreferences.Editor storageEditor = sharedPreferences.edit();
        storageEditor.putString(doctor.getFname(), doctor.getFname());
        storageEditor.putString(doctor.getLname(), doctor.getLname());
        storageEditor.putString(doctor.getSpeciality(), doctor.getSpeciality());
        storageEditor.putString(doctor.getEmail(), doctor.getEmail());
        storageEditor.putString(doctor.getPwd(), doctor.getPwd());
        storageEditor.commit();
    }

    public void storeBluetoothDevice(BluetoothDevice bluetoothDevice) {
        SharedPreferences.Editor storageEditor = sharedPreferences.edit();
        storageEditor.putString(MAC_ADDRESS, bluetoothDevice.getAddress());
        storageEditor.commit();
    }

    public void removeBluetoothDevice() {
        SharedPreferences.Editor storageEditor = sharedPreferences.edit();
        storageEditor.remove(MAC_ADDRESS);
        storageEditor.commit();
    }

    public String getPairedMacAddress() {
        return sharedPreferences.getString(MAC_ADDRESS, "");
    }

    public boolean isBluetoothDevicePaired(BluetoothDevice device) {
        if (sharedPreferences.contains(MAC_ADDRESS)) {
            if (sharedPreferences.getString(MAC_ADDRESS, "").equals(device.getAddress()))
                return true;
            else return false;
        } else return false;
    }


    public boolean isUserExist(String email, String pwd) {
        if (sharedPreferences.contains(email) &&
                sharedPreferences.contains(pwd)) {
            return true;
        } else return false;
    }

    public User getLoggedInUser(String email, String password) {
        String userEmail = sharedPreferences.getString(email, "");
        String userPwd = sharedPreferences.getString(password, "");

        User user = new User(userEmail, userPwd);

        return user;
    }

    public void setUserLoggedIn(boolean isLoggedIn) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(LOGGED_IN, isLoggedIn);
        editor.commit();
    }

    public boolean isUserLoggedIn() {
        if (sharedPreferences.getBoolean(LOGGED_IN, false) == true) {
            return true;
        } else {
            return false;
        }
    }

    public void setLoggedUsername(String name) {
        SharedPreferences.Editor storageEditor = sharedPreferences.edit();
        storageEditor.putString(LOGGED_USERNAME, name);
        storageEditor.commit();
    }

    public String getLoggedUsername() {
        String loggedusername = sharedPreferences.getString(LOGGED_USERNAME, "");
        return loggedusername;
    }


    public void clearData(Context context) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        Toast.makeText(context, "Data cleared ", Toast.LENGTH_SHORT).show();
    }


}
