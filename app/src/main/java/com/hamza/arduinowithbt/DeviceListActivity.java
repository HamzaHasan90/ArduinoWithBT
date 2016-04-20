package com.hamza.arduinowithbt;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.hamza.arduino.BluetoothHandling.DeviceListAdapter;
import com.hamza.arduino.DB.LocalStore;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Device list.
 *
 * @author Hamza Hasan
 */
public class DeviceListActivity extends Activity {
    private ListView mListView;
    private DeviceListAdapter mAdapter;
    private ArrayList<BluetoothDevice> mDeviceList;
    private LocalStore mlocalStore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paired_devices);

        mDeviceList = getIntent().getExtras().getParcelableArrayList("device.list");
        mListView = (ListView) findViewById(R.id.lv_paired);
        mAdapter = new DeviceListAdapter(this);
        mlocalStore = new LocalStore(this);

        mAdapter.setData(mDeviceList);
        mAdapter.setListener(new DeviceListAdapter.OnPairButtonClickListener() {
            @Override
            public void onPairButtonClick(int position) {
                BluetoothDevice device = mDeviceList.get(position);

                if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                    unpairDevice(device);
                } else {
                    showToast("Pairing...");
                    pairDevice(device);
                }
            }
        });
        mListView.setAdapter(mAdapter);
        registerReceiver(mPairReceiver, new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED));
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mPairReceiver);
        super.onDestroy();
    }


    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void pairDevice(BluetoothDevice device) {
        try {
            Method method = device.getClass().getMethod("createBond", (Class[]) null);
            mlocalStore.storeBluetoothDevice(device);
            method.invoke(device, (Object[]) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void goToBluetoothConnectActivity(){
        Intent intent = new Intent(this,BluetoothConnectActivity.class);
        startActivity(intent);
    }

    private void unpairDevice(BluetoothDevice device) {
        try {
            Method method = device.getClass().getMethod("removeBond", (Class[]) null);
            mlocalStore.removeBluetoothDevice();
            method.invoke(device, (Object[]) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final BroadcastReceiver mPairReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                final int state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR);
                final int prevState = intent.getIntExtra(BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE, BluetoothDevice.ERROR);

                if (state == BluetoothDevice.BOND_BONDED && prevState == BluetoothDevice.BOND_BONDING) {
                    showToast("Paired");
                    goToLoginActivity();
                } else if (state == BluetoothDevice.BOND_NONE && prevState == BluetoothDevice.BOND_BONDED) {
                    showToast("Unpaired");
                    goToBluetoothConnectActivity();
                }

                mAdapter.notifyDataSetChanged();
            }
        }
    };
}