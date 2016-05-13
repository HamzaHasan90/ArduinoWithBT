package com.hamza.arduinowithbt;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.hamza.arduino.DB.LocalStore;
import com.hamza.arduino.adapters.ScansAdapter;
import com.hamza.arduino.model.Scan;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.regex.PatternSyntaxException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String MAIN_ACTIVITY = "MainActivity";
    public static final String SCAN_IMAGE = "ScanImage";
    public static final String SCAN_IMAGE_NAME = "ScanImageName";
    public static final String SCAN_DATE = "ScanDate";
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private Button scanButton;
    private LocalStore localStore;
    private BluetoothDevice pairedBluetoothDevice;
    private UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    private BluetoothSocket socket;
    private ListView scansList;
    private Calendar calendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);
            localStore = new LocalStore(this);
            scanButton = (Button) findViewById(R.id.btn_scan);
            scanButton.setOnClickListener(this);
            scansList = (ListView) findViewById(R.id.scan_list);

            Toast.makeText(this, "Welcome " + localStore.getLoggedUsername(), Toast.LENGTH_SHORT).show();
            final Scan[] scans = processScans();
            scansList.setAdapter(new ScansAdapter(this, R.layout.scans_list_item_row, scans));

            scansList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getBaseContext(), ScanImageActivity.class);
                    intent.putExtra(SCAN_IMAGE, scans[position].getScanImage());
                    intent.putExtra(SCAN_DATE, scans[position].getScanDate());
                    intent.putExtra(SCAN_IMAGE_NAME,scans[position].getScanImageName());
                    startActivity(intent);
                }
            });

        } catch (Exception e) {
            showToast("Exception " + e.toString());
        }


    }

    private Scan[] processScans() throws ParseException {
        String s = this.getResources().getResourceEntryName(R.drawable.g1)+".png";
        Scan[] scans = {
                new Scan("Image One"  , getFormated(getToday(calendar))   , R.drawable.g1, this.getResources().getResourceEntryName(R.drawable.g1)+".png"),
                new Scan("Image Two"  , getFormated(getDay(calendar, -1)) , R.drawable.g2, this.getResources().getResourceEntryName(R.drawable.g2)+".png"),
                new Scan("Image Three", getFormated(getDay(calendar, -2)) , R.drawable.g3, this.getResources().getResourceEntryName(R.drawable.g3)+".png"),
                new Scan("Image Four" , getFormated(getDay(calendar, -3)) , R.drawable.g4, this.getResources().getResourceEntryName(R.drawable.g4)+".png")
        };
        return scans;
    }

    private Date getDay(Calendar calendar, int i) {
        calendar.add(Calendar.DATE, i);
        return calendar.getTime();
    }

    private String getFormated(Date date) throws ParseException {
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MMM-dd");
        String strDate = mdformat.format(calendar.getTime());
        return strDate;
    }

    private Date getToday(Calendar calendar) {
        return calendar.getTime();
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
