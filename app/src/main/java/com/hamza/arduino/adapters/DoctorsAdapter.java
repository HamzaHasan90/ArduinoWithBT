package com.hamza.arduino.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hamza.arduino.model.Doctor;
import com.hamza.arduinowithbt.R;

/**
 * Created by Hamza90 on 5/5/2016.
 */
public class DoctorsAdapter extends ArrayAdapter<Doctor> {
    Context context;
    int layoutResourceId;
    Doctor[] data = null;

    public DoctorsAdapter(Context context, int resource, Doctor[] objects) {
        super(context, resource, objects);
        this.layoutResourceId = resource;
        this.context = context;
        this.data = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        DoctorsHolder holder;
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(R.layout.doc_list_item_row, parent, false);

            holder = new DoctorsHolder();
            holder.tvDocName = (TextView)row.findViewById(R.id.tv_doc_name);
            holder.tvDocSpeciality = (TextView)row.findViewById(R.id.tv_doc_speciality);

            row.setTag(holder);
        }
        else
        {
            holder = (DoctorsHolder)row.getTag();
        }

        Doctor doctor = data[position];
        holder.tvDocName.setText(doctor.getFname() + " "+ doctor.getLname());
        holder.tvDocSpeciality.setText(doctor.getSpeciality());

        return row;
    }

    static class DoctorsHolder
    {
        TextView tvDocName,tvDocSpeciality;
    }
}