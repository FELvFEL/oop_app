package com.fel.oop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.Manifest;
import java.io.InputStream;
import java.util.LinkedList;

public class spisok extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE =123 ;
    DBHelper dbHelper;
    TextView name_out;
    TextView birthdate_out;
    TextView country_out;
    TextView crime_out;
    TextView signs_out;
    ImageButton avatar;
    ImageButton next;
    ImageButton prev;
    int i=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spisok);
        next = findViewById(R.id.next);
        prev = findViewById(R.id.prev);
        avatar = findViewById(R.id.avatar);
        avatar.setMaxWidth(150);
        avatar.setMaxHeight(200);
        name_out = findViewById(R.id.name_out);
        birthdate_out = findViewById(R.id.birthdate_out);
        country_out = findViewById(R.id.country_out);
        crime_out = findViewById(R.id.crime_out);
        signs_out = findViewById(R.id.signs_out);
        dbHelper = new DBHelper(this);



        LinkedList<Criminal> list = dbHelper.GetAll(); // достаём из бд все записи
        show(list);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i=i+1;show(list);
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i=i-1;show(list);
            }
        });


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, load image
                LinkedList<Criminal> list = dbHelper.GetAll();
                String text = list.get(0).uri;
                Uri uri = Uri.parse(text);
                avatar.setImageURI(uri); //выводим картинку из галереи, если разрешение, дано
            } else {
                // Permission not granted, display error message or handle appropriately
            }
        }
    }

    public void show(LinkedList<Criminal> list) {

        String text="";
        if (list.size()-1<i){
            i=0;
        }
        if (i<0) {
            i=list.size()-1;
        }
        text = list.get(i).name;
        name_out.setText(text);

        text = list.get(i).year;
        birthdate_out.setText(getString(R.string.date,text));

        text = list.get(i).country;
        country_out.setText(text);

        text = list.get(i).crime;
        crime_out.setText(text);

        text = list.get(i).signs;
        signs_out.setText(text);


        text = list.get(i).uri;

        // Check for permission to read external storage
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // Permission granted, load image
            Uri uri = Uri.parse(text);
            avatar.setImageURI(uri);  //выводим картинку из галереи, если разрешение, дано
        } else {
            // Permission not granted, request it
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }
    }


