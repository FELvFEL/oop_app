package com.fel.oop;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);

        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPreferences1", Context.MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);

        if (isFirstRun) {
            Toast.makeText(MainActivity.this, "Первый запуск.", Toast.LENGTH_SHORT).show();
            // Код, который должен быть выполнен только при первом запуске приложения
            // Получить Drawable из ресурсов
            @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.oleg);

            // Преобразовать Drawable в Bitmap
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

            // Сохранить Bitmap во временном файле
            File file = saveBitmapToFile(bitmap);

            // Получить URI временного файла
            Uri uri = Uri.fromFile(file);

            // Создать объект Criminal с использованием URI
            Criminal criminal = new Criminal("Олег Нечипоренко", "15.04.1987", "Барселона", "Наркоторговля", "Тату на лице", uri.toString(), 1);

            dbHelper.AddOne(criminal);


            @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable1 = getResources().getDrawable(R.drawable.oleg);
            Bitmap bitmap1 = ((BitmapDrawable) drawable1).getBitmap();
            File file1 = saveBitmapToFile(bitmap1);
            Uri uri1 = Uri.fromFile(file1);
            Criminal criminal1 = new Criminal("Олег Нечипоренко2", "15.04.1988", "Барселона", "Наркоторговля", "Тату на лице", uri1.toString(), 0);
            dbHelper.AddOne(criminal1);

            // Установите флаг isFirstRun в false для указания, что приложение уже было запущено
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isFirstRun", false);
            editor.apply();
        }
    }

    public void startSpisok(View v) {
        Intent intent = new Intent(this, spisok.class);
        startActivity(intent);
    }  //Список преступников

    public void startAbout(View v) {
        Intent intent = new Intent(this, About.class);
        startActivity(intent);
    }  //Список преступников

    public void startAdd(View v) {
        Intent intent = new Intent(this, addCriminal.class);
        startActivity(intent); //Окно добавления нового преступника
    }
    public void startArchive(View v) {
        Intent intent = new Intent(this, archive.class);
        startActivity(intent); //Окно добавления нового преступника
    }

    public void startFind(View v) {
        Intent intent = new Intent(this, Find.class);
        startActivity(intent);
    }
    private File saveBitmapToFile(Bitmap bitmap) {
        // Создать временный файл
        File file = new File(getCacheDir(), "temp_image.jpg");

        try {
            // Создать поток для записи в файл
            FileOutputStream fos = new FileOutputStream(file);

            // Сохранить Bitmap в файл формата JPEG
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);

            // Закрыть поток
            fos.close();

            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}