package com.fel.oop;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

public class Find extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 123;
    DBHelper dbHelper;
    EditText f_input;
    ImageButton f_image;
    TextView f_name;
    TextView f_birthdate;
    TextView f_country;
    TextView f_crime;
    TextView f_signs;
    ImageButton f_button;
    ImageView  image10, nahod_spisok,nahod_spisok1, nahod_archive, nahod_archive1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        dbHelper = new DBHelper(this);
        f_image = findViewById(R.id.f_image);
        f_image.setMaxWidth(150);
        f_image.setMaxHeight(200);
        f_name = findViewById(R.id.f_name);
        f_birthdate = findViewById(R.id.f_birthdate);
        f_country = findViewById(R.id.f_country);
        f_crime = findViewById(R.id.f_crime);
        f_signs = findViewById(R.id.f_signs);
        f_button = findViewById(R.id.f_button);
        f_input = findViewById(R.id.f_input);
        image10 = findViewById(R.id.image10);
        nahod_spisok = findViewById(R.id.nahod_spisok);
        nahod_spisok1 = findViewById(R.id.nahod_spisok1);
        nahod_archive = findViewById(R.id.nahod_archive);
        nahod_archive1 = findViewById(R.id.nahod_archive1);

        f_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    private void search() {
        LinkedList<Criminal> list = dbHelper.GetAll();
        String name1 = String.valueOf(f_input.getText());
        for (Criminal criminal:list) {
          if (criminal.name.equals(name1)) {
              f_name.setText(criminal.name);
              f_birthdate.setText(getString(R.string.date,criminal.year));
              f_country.setText(getString(R.string.country,criminal.country));
              f_crime.setText(getString(R.string.crime,criminal.crime));
              f_signs.setText(getString(R.string.signs,criminal.signs));
              if (criminal.arhive == 0) {
                  nahod_archive.setVisibility(View.INVISIBLE);
                  nahod_archive1.setVisibility(View.INVISIBLE);
                  nahod_spisok.setVisibility(View.VISIBLE);
                  nahod_spisok1.setVisibility(View.VISIBLE);
              }
              if (criminal.arhive == 1) {
                  nahod_spisok.setVisibility(View.INVISIBLE);
                  nahod_spisok1.setVisibility(View.INVISIBLE);
                  nahod_archive.setVisibility(View.VISIBLE);
                  nahod_archive1.setVisibility(View.VISIBLE);
              }
              String text = criminal.uri;

              // Check for permission to read external storage
              if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                  // Permission granted, load image
                  Uri uri = Uri.parse(text);
                  f_image.setVisibility(View.VISIBLE);
                  image10.setVisibility(View.VISIBLE);
                  f_image.setImageURI(uri);  //выводим картинку из галереи, если разрешение, дано
              } else {
                  // Permission not granted, request it
                  ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
              }
          }
        }
        if (f_name.getText().equals("")) {
            f_input.setText("");
            f_input.setHint("Не найдено");
        }
    }
    public void startMain(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}