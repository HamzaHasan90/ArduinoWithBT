package com.hamza.arduinowithbt;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hamza.arduino.DB.LocalStore;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String MAIN_ACTIVITY = "MainActivity";
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private Button scanButton;
    private LocalStore localStore;
    private BluetoothDevice pairedBluetoothDevice;
    private UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    private BluetoothSocket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);
            // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            // setSupportActionBar(toolbar);
            //Intent intent = getIntent();
            //String name = intent.getStringExtra(LocalStore.LOGGED_USERNAME);
            localStore = new LocalStore(this);
            scanButton = (Button) findViewById(R.id.btn_scan);
            scanButton.setOnClickListener(this);

            Toast.makeText(this, "Welcome " + localStore.getLoggedUsername(), Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            Intent logout = new Intent(MainActivity.this, LoginActivity.class);
            localStore.setUserLoggedIn(false);
            startActivity(logout);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_scan: {

                pairedBluetoothDevice = findPairedArduino();
                try {
                    socket = pairedBluetoothDevice.createInsecureRfcommSocketToServiceRecord(myUUID);
                    socket.connect();

                    InputStream inputStream = socket.getInputStream();
                    OutputStream outputStream = socket.getOutputStream();
                } catch (IOException e) {
                    showToast("ERROR MESSAGE IS : " + e.getMessage());
                    showToast("LOCALIZED ERROR MESSAGE IS : " + e.getLocalizedMessage());

                    try {
                        socket.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                    e.printStackTrace();
                }

            }
        }
    }

    private void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG);
    }

    public BluetoothDevice findPairedArduino() {

        Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
        String pairedDeviceAddress = localStore.getPairedMacAddress();

        for (BluetoothDevice device : devices) {
            if (device.getAddress().equals(pairedDeviceAddress)) {
                return device;
            }
        }
        return null;

    }
}
