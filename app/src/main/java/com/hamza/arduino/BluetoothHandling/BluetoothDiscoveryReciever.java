package com.hamza.arduino.BluetoothHandling;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Hamza on 4/11/2016.
 */
public class BluetoothDiscoveryReciever extends BroadcastReceiver {


    public static final int STATE_NONE = 0;       // we're doing nothing
    public static final int STATE_LISTEN = 1;     // now listening for incoming connections
    public static final int STATE_CONNECTING = 2; // now initiating an outgoing connection
    public static final int STATE_CONNECTED = 3;  // now connected to a remote device


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {

            Toast.makeText(context, "Searching for Bluetooth devices... ", Toast.LENGTH_SHORT).show();

        } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {

            Toast.makeText(context, "Finished searching ! ", Toast.LENGTH_SHORT).show();

        } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {

            //a Bluetooth device was found
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            //DeviceItem newDevice = new DeviceItem(device.getName(), device.getAddress(), "false");
            String deviceClass = device.getBluetoothClass().toString();
            String deviceName = device.getName();
            Toast.makeText(context, "Device class  " + device.getBluetoothClass().getDeviceClass(), Toast.LENGTH_LONG).show();
            Toast.makeText(context, "Device name is  " + device.getName(), Toast.LENGTH_LONG).show();
        } else if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {

            Toast.makeText(context, "This device is connected ", Toast.LENGTH_SHORT).show();
        } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {

            final int state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR);
            final int prevState = intent.getIntExtra(BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE, BluetoothDevice.ERROR);

            if (state == BluetoothDevice.BOND_BONDED && prevState == BluetoothDevice.BOND_BONDING) {
                Toast.makeText(context, "Paired", Toast.LENGTH_SHORT).show();
            } else if (state == BluetoothDevice.BOND_NONE && prevState == BluetoothDevice.BOND_BONDED) {
                Toast.makeText(context, "Unpaired", Toast.LENGTH_SHORT).show();
            }

        }


    }
}
