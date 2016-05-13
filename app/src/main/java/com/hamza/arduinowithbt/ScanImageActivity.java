package com.hamza.arduinowithbt;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.hamza.arduino.adapters.DoctorsAdapter;
import com.hamza.arduino.model.Doctor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class ScanImageActivity extends AppCompatActivity {
    private static Doctor[] doctors;
    private ImageView scannedImage;
    private TextView tvScandate;
    private ListView doctorsListView;
    private DoctorsAdapter doctorsAdapter;
    private String fileName;
    private final Toast mToastToShow = Toast.makeText(this, "إضغط مطولاً على واحد من أسماء الأطباء", Toast.LENGTH_LONG);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_image);
        scannedImage = (ImageView) findViewById(R.id.iv_full_scan_image);
        doctorsListView = (ListView) findViewById(R.id.docs_listView);
        tvScandate = (TextView) findViewById(R.id.scan_date);

        Intent intent = getIntent();
        final int scanImage = intent.getExtras().getInt(MainActivity.SCAN_IMAGE);
        final String scanDate = intent.getExtras().getString(MainActivity.SCAN_DATE);
        String scanImagename = intent.getExtras().getString(MainActivity.SCAN_IMAGE_NAME);
        scannedImage.setImageResource(scanImage);
        tvScandate.setText("Taken on: " + scanDate);

        fileName = scanImagename;

        doctorsAdapter = new DoctorsAdapter(this, R.layout.doc_list_item_row, processDoctors());
        doctorsListView.setAdapter(doctorsAdapter);
        doctorsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                Toast.makeText(ScanImageActivity.this, doctors[position].getEmail(), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(ScanImageActivity.this);
                builder.setTitle("Send Email ");
                builder.setMessage("Send an email to this doctor ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                        emailIntent.setData(Uri.parse("mailto:"));
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{doctors[position].getEmail()});
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Following up my checks");
                        emailIntent.putExtra(Intent.EXTRA_TEXT, "Hi doctor, I'm sending you the latest scan check");
                        emailIntent.putExtra(Intent.EXTRA_STREAM, getFileUri(scanImage,scanDate));
                        startActivity(emailIntent);

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.setTitle("Send email");
                alertDialog.show();
                return true;
            }
        });

    }

    private Doctor[] processDoctors() {
        doctors = new Doctor[]{
                new Doctor("Dr. Tranter " , "Timothy" , "Speciality One"  , "tranter@timothy.com" , "123"),
                new Doctor("Dr. Maralyn " , "Mathilda", "Speciality Two"  , "maralyn@mathilda.com", "123"),
                new Doctor("Dr. Laci "    , "Ophilia" , "Speciality Three", "laci@Ophilia .com"   , "123"),
                new Doctor("Dr. Jennifer ", "Phillips", "Speciality One"  , "john_allen@john.com" , "123"),
                new Doctor("Dr. Michelle ", "Perez"   , "Speciality Two"  , "Michelle@Perez.com"  , "123"),
                new Doctor("Dr. Ashley "  , "Green"   , "Speciality Three", "ashely@green.com"    , "123"),
                new Doctor("Dr. Carlos "  , "Taylor"  , "Speciality One"  , "carlos@taylor.com"   , "123"),
                new Doctor("Dr. Pamela "  , "Evans"   , "Speciality Two"  , "pamela@evans.com"    , "123"),
                new Doctor("Dr. Sean "    , "Powell"  , "Speciality Three", "sean@powell.com"     , "123"),
                new Doctor("Dr. Alan "    , "King"    , "Speciality One"  , "alan@king.com"       , "123"),
                new Doctor("Dr. Deborah " , "Davis"   , "Speciality Two"  , "deborah@davis.com"   , "123"),
                new Doctor("Dr. Alice "   , "Adams"   , "Speciality Three", "alice@adams.com"     , "123"),
        };
        return doctors;
    }

    private Uri getFileUri(int resId,String scanDate) {
        Bitmap bitMap = BitmapFactory.decodeResource(getResources(), resId);
        File file = saveImageOnExtStorage(bitMap,scanDate);
        Uri uri = Uri.fromFile(file);
        return uri;
    }

    private File saveImageOnExtStorage(Bitmap bitMap,String scanDate) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_scans");
        myDir.mkdirs();
        String fname = generateName(scanDate) + ".png";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitMap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            return file.getAbsoluteFile();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String generateName(String scanDate) {
        StringBuilder stringBuilder = new StringBuilder("scanImage");
        stringBuilder.append("_"+scanDate);
        Random generator = new Random();
        stringBuilder.append(generator.nextInt(generator.nextInt(10000)));
        return stringBuilder.toString();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
