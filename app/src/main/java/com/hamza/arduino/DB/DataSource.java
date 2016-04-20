package com.hamza.arduino.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Hamza on 4/1/2016.
 */
public class DataSource {
    private SQLiteDatabase sqLiteDatabase;
    private SQLiteOpenHelper sqLiteOpenHelper;

    public DataSource(Context context){
        this.sqLiteOpenHelper = new DBHelper(context);
        this.sqLiteDatabase = this.sqLiteOpenHelper.getWritableDatabase();
    }
}
