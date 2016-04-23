package com.hamza.arduinowithbt;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hamza.arduino.DB.LocalStore;
import com.hamza.arduino.model.User;

import java.util.Set;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin;
    EditText etEmail, etPassword;
    TextView tvPatientReg, tvDoctorReg;
    LocalStore localStore;
    private BluetoothAdapter bluetoothAdapter;
    private Set<BluetoothDevice> devices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localStore = new LocalStore(this);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter != null) {

            if (!bluetoothAdapter.isEnabled()) {
                Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableAdapter, 0);
            }
            if (!isPairedBlutoothDeviceExists()) {
                activateBluetooth();
            } else {

                Intent goToMain = new Intent(this,MainActivity.class);
                startActivity(goToMain);
                /*if (localStore.isUserLoggedIn() && !localStore.getLoggedUsername().equals("")) {
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra(LocalStore.LOGGED_USERNAME, localStore.getLoggedUsername());
                    startActivity(intent);
                } else {
                    setContentView(R.layout.activity_login);
                    btnLogin = (Button) findViewById(R.id.btn_login);
                    btnLogin.setOnClickListener(this);

                    etEmail = (EditText) findViewById(R.id.et_email);
                    etPassword = (EditText) findViewById(R.id.et_password);
                    tvPatientReg = (TextView) findViewById(R.id.tv_patient_reg);
                    tvDoctorReg = (TextView) findViewById(R.id.tv_doctor_reg);
                    tvDoctorReg.setOnClickListener(this);
                    tvPatientReg.setOnClickListener(this);
                }*/
            }


        } else {

            new AlertDialog.Builder(this)
                    .setTitle("Not compatible")
                    .setMessage("This application needs Bluetooth")
                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

    }

    private boolean isPairedBlutoothDeviceExists() {
        BluetoothDevice device;
        devices = bluetoothAdapter.getBondedDevices();
        if (bluetoothAdapter.getBondedDevices() != null) {

            Object[] pairedDevices = devices.toArray();

            for (int i = 0; i < pairedDevices.length; i++) {
                device = (BluetoothDevice) pairedDevices[i];

                if (localStore.isBluetoothDevicePaired(device)) {
                    return true;
                }
            }

        }
        return false;
    }

    @Override
    public void onClick(View v) {
        User user;

        switch (v.getId()) {

            case R.id.btn_login:
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (email == null || email.equals("") || password == null || password.equals("")) {
                    Toast toast = new Toast(this);
                    toast.setGravity(50, 50, 50);
                    toast.makeText(LoginActivity.this, "Please enter Email and Password", Toast.LENGTH_LONG).show();
                } else {
                    if (localStore.isUserExist(email, password)) {

                        user = localStore.getLoggedInUser(email, password);
                        localStore.setUserLoggedIn(true);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("email", user.getEmail());
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, getResources().getText(R.string.wrong_email_or_password), Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.tv_patient_reg:
                Intent reg_intent = new Intent(this, PatientRegisterActivity.class);
                startActivity(reg_intent);
                break;

            case R.id.tv_doctor_reg:

                Intent doc_intent = new Intent(this, DoctorRegisterActivity.class);
                startActivity(doc_intent);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_clear:
                localStore.clearData(this);
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*public void scanForBluetoothDevices() {
        // Turns Bluetooth On
        activateBluetooth();


        IntentFilter filter = new IntentFilter();

        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        registerReceiver(new BluetoothDiscoveryReciever(), filter);

        bluetoothAdapter.startDiscovery();

        while (bluetoothAdapter.isDiscovering()) {


        }

    }*/

    private void activateBluetooth() {
        Intent bluetoothConnect = new Intent(this, BluetoothConnectActivity.class);
        startActivity(bluetoothConnect);
    }

}
