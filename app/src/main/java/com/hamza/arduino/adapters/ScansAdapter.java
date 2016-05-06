package com.hamza.arduino.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hamza.arduino.model.Scan;
import com.hamza.arduinowithbt.R;

/**
 * Created by Hamza90 on 5/6/2016.
 */
public class ScansAdapter extends ArrayAdapter<Scan> {
    Context context;
    int layoutResourceId;
    Scan[] data = null;
    public ScansAdapter(Context context, int resource, Scan[] objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutResourceId = resource;
        this.data = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ScansHolder scansHolder;

        if (row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(R.layout.scans_list_item_row, parent, false);

            scansHolder  = new ScansHolder();
            scansHolder.scanName =(TextView) row.findViewById(R.id.tv_scan_name);
            scansHolder.scanImage=(ImageView) row.findViewById(R.id.iv_scan_image);
            scansHolder.scanDate = (TextView) row.findViewById(R.id.tv_scan_date);

            row.setTag(scansHolder);
        }else{
            scansHolder = (ScansHolder) row.getTag();
        }
        Scan scan = data[position];
        scansHolder.scanName.setText(scan.getName());
        scansHolder.scanImage.setImageResource(scan.getScanImage());
        scansHolder.scanImage.setMaxWidth(100);
        scansHolder.scanDate.setText(scan.getScanDate()+"");

        return row;

    }

    static class ScansHolder{
        TextView scanName,scanDate;
        ImageView scanImage;

    }
}
