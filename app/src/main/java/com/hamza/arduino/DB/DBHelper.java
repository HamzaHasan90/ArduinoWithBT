package com.hamza.arduino.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.hamza.arduino.model.User;

/**
 * Created by Hamza on 3/25/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DATABASE";
    private static final String TABLE_CONTACTS = "CONTACTS";
    private static final String KEY_NAME = "NAME";
    private static final String KEY_EMAIL = "EMAIL";
    private static final String KEY_PH_NO = "PHONE_NUMBER";
    private static final String KEY_PWD = "PWD";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_CONTACTS+" (" +
                KEY_NAME    + " TEXT              NOT NULL   ," +
                KEY_EMAIL   + " TEXT  PRIMARY KEY NOT NULL   ," +
                KEY_PH_NO   + " TEXT                         ," +
                KEY_PWD     + " TEXT                         " +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addContact(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, user.getEmail()); // Contact Name
        values.put(KEY_PWD, user.getPwd()); // Contact Phone

        db.insert(TABLE_CONTACTS, null, values);
        db.close();

    }
    public User getContact(String email){
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_NAME, KEY_EMAIL
                            ,KEY_PH_NO,KEY_PWD }, KEY_EMAIL + "=?",
                    new String[] {email}, null, null, null, null);
            if (cursor.getCount()>0) {
                cursor.moveToFirst();

                User user = new User(cursor.getString(0), cursor.getString(1));
                return user;
            }else{
                return null;
            }
        } catch (Exception e) {
            Log.d("ARDUINO",e.getMessage());
        } finally {
            return null;
        }
    }
}
