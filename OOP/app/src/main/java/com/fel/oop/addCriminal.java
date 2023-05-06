package com.fel.oop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.LinkedList;

public class addCriminal extends AppCompatActivity {

    DBHelper dbHelper;
    EditText name;
    EditText birthdate;
    EditText city;
    EditText crime;
    EditText signs;
    Button uri;
    Button ok_in;
    String suri; // переменная в которой хранится картинка

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcriminal);
        dbHelper = new DBHelper(this);
        name = findViewById(R.id.name_in);
        birthdate = findViewById(R.id.birthdate_in);
        city = findViewById(R.id.city_in);
        crime = findViewById(R.id.crime_in);
        signs = findViewById(R.id.signs_in);
        uri = findViewById(R.id.uri_in);
        ok_in = findViewById(R.id.ok_in);

        ok_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addnewcrime();
            }
        });
        uri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImage();
            }
        });
    }

    private void getImage() {  // Открываем активность, выбора картинки из галереи
        Intent intentChooser = new Intent();
        intentChooser.setType("image/*");
        intentChooser.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intentChooser, "Select your image"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { // Обрабатываем результат выбора картинки
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null && data.getData() != null) {
            if (resultCode == RESULT_OK) {

                suri = data.getDataString();
            }
        }
    }


    private void addnewcrime() { // добавляем одного нового преступника в бд
        String sname = String.valueOf(name.getText());
        String sbirthdate = String.valueOf(birthdate.getText());
        String scity = String.valueOf(city.getText());
        String scrime = String.valueOf(crime.getText());
        String ssigns = String.valueOf(signs.getText());

        Criminal criminal = new Criminal(sname,sbirthdate,scity,scrime,ssigns,suri);
        dbHelper.AddOne(criminal);

    }
}