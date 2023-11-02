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
import android.widget.ImageView;
import android.widget.TextView;
import android.Manifest;
import android.widget.Toast;

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
    ImageView avatar;
    ImageButton next;
    ImageButton prev;
    ImageButton del;
    ImageButton to_archive;
    int i=0, p=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spisok);
        del = findViewById(R.id.del);
        next = findViewById(R.id.next);
        prev = findViewById(R.id.prev);
        avatar = findViewById(R.id.avatar);
        to_archive = findViewById(R.id.to_archive);
        name_out = findViewById(R.id.name_out);
        birthdate_out = findViewById(R.id.birthdate_out);
        country_out = findViewById(R.id.country_out);
        crime_out = findViewById(R.id.crime_out);
        signs_out = findViewById(R.id.signs_out);
        dbHelper = new DBHelper(this);



        LinkedList<Criminal> list = dbHelper.GetAll(); // достаём из бд все записи
        if (list.isEmpty()) {
            Toast.makeText(spisok.this, "Пусто.", Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            p=0;
            while (list.get(i).arhive == 1) {
                p++;
                i+=1;
                if (list.size()-1<i){
                    i=0;
                }
                if (p>list.size()){
                    Toast.makeText(spisok.this, "Пусто.", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                }
            }
            if (list.get(i).arhive == 0) {
                show(list);
            }
        }


        to_archive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toArchive(list);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 do {
                    i = i + 1;
                    if (list.size()-1<i){
                        i=0;
                    }
                } while (list.get(i).arhive == 1);
                show(list);
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                do {
                    i = i - 1;
                    if (i<0) {
                        i=list.size()-1;
                    }
                } while (list.get(i).arhive == 1);
                show(list);
            }
        });
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                del(list);
            }
        });

    }

    private void toArchive(LinkedList<Criminal> list) {
        String name1 = list.get(i).name;
        dbHelper.updateArchive(name1,1);
        list.get(i).arhive = 1;
        if (list.size() == 1) {
            // Если это была последняя запись, закрываем активити
            finish();
        } else {
            p=0;
            // Переключаемся на следующую запись
            do {
                p++;
                if (p > list.size()){
                    Toast.makeText(spisok.this, "Пусто.", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                }
                i = i + 1;
                if (list.size()-1<i){
                    i=0;
                }
            } while (list.get(i).arhive == 1);
            show(list);
        }
    }

    private void del(LinkedList<Criminal> list) {
        String name1 = list.get(i).name;
        dbHelper.DeleteOne(name1);
        list.remove(i);
        if (list.size() == 0) {
            // Если это была последняя запись, закрываем активити
            finish();
        } else {
            p=0;
            // Переключаемся на следующую запись
            do {
                p++;
                if (p > list.size()){
                    Toast.makeText(spisok.this, "Пусто.", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                }
                i = i + 1;
                if (list.size()-1<i){
                    i=0;
                }
            } while (list.get(i).arhive == 1);
            show(list);
        }
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
        text = list.get(i).name;
        name_out.setText(text);


        text = list.get(i).year;
        birthdate_out.setText(getString(R.string.date,text));

        text = list.get(i).country;
        country_out.setText(getString(R.string.country,text));

        text = list.get(i).crime;
        crime_out.setText(getString(R.string.crime,text));

        text = list.get(i).signs;
        signs_out.setText(getString(R.string.signs,text));


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
    public void startMain(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}


