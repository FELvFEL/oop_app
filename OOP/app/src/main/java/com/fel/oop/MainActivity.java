package com.fel.oop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startSpisok(View v) {
        Intent intent = new Intent(this, spisok.class);
        startActivity(intent);
    }  //Список преступников

    public void startAdd(View v) {
        Intent intent = new Intent(this, addCriminal.class);
        startActivity(intent); //Окно добавления нового преступника
    }


}