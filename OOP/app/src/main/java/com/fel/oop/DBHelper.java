package com.fel.oop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.LinkedList;

public class DBHelper extends SQLiteOpenHelper {


    public static final String CRIMINALS = "CRIMINALS";
    public static final String COLUMN_NAME = "COLUMN_NAME";
    public static final String COLUMN_BIRTHDATE = "COLUMN_BIRTHDATE";
    public static final String COLUMN_COUNTRY = "COLUMN_COUNTRY";
    public static final String COLUMN_CRIME = "COLUMN_CRIME";
    public static final String COLUMN_SIGNS = "COLUMN_SIGNS";
    public static final String COLUMN_URI = "COLUMN_URI";
    private static final String COLUMN_ARCHIVE = "COLUMN_ARCHIVE";

    public DBHelper(@Nullable Context context) {
        super(context, "criminals.db", null, 8);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + CRIMINALS + " (" + COLUMN_NAME + " TEXT, " + COLUMN_BIRTHDATE + " TEXT, " + COLUMN_COUNTRY + " TEXT, " + COLUMN_CRIME + " TEXT, " + COLUMN_SIGNS + " TEXT, " + COLUMN_ARCHIVE + " INT, " + COLUMN_URI + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion){ // если бд обновлена до новой версии
            // Удаляем таблицу SPISOK
            db.execSQL("DROP TABLE IF EXISTS " + CRIMINALS);
            // Создаем новую таблицу
            onCreate(db);
        }
    }

    public void DeleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CRIMINALS,null,null);
        db.close();
    }
    public void DeleteOne(String s){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CRIMINALS, COLUMN_NAME + "='" + s + "'", null);
        db.close();
    }
    public void updateArchive(String name, int archiveValue) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ARCHIVE, archiveValue);

        String selection = COLUMN_NAME + " = ?";
        String[] selectionArgs = { name };

        db.update(CRIMINALS, cv, selection, selectionArgs);

        db.close();
    }


    public void AddOne(Criminal criminal){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, criminal.name);
        cv.put(COLUMN_BIRTHDATE, criminal.year);
        cv.put(COLUMN_COUNTRY, criminal.country);
        cv.put(COLUMN_CRIME, criminal.crime);
        cv.put(COLUMN_SIGNS, criminal.signs);
        cv.put(COLUMN_URI, criminal.uri);
        cv.put(COLUMN_ARCHIVE, criminal.arhive);

        db.insert(CRIMINALS,null,cv);

        db.close();
    }
    public LinkedList<Criminal> GetAll() {
        LinkedList<Criminal> list = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(CRIMINALS, null,null,null,null,null,null,null);
        if (cursor.moveToFirst()) {
           do{
               int id_n = cursor.getColumnIndex(COLUMN_NAME);
               int id_y = cursor.getColumnIndex(COLUMN_BIRTHDATE);
               int id_country = cursor.getColumnIndex(COLUMN_COUNTRY);
               int id_crime = cursor.getColumnIndex(COLUMN_CRIME);
               int id_signs = cursor.getColumnIndex(COLUMN_SIGNS);
               int id_u = cursor.getColumnIndex(COLUMN_URI);
               int id_a = cursor.getColumnIndex(COLUMN_ARCHIVE);

               Criminal criminal = new Criminal(cursor.getString(id_n),cursor.getString(id_y),cursor.getString(id_country),cursor.getString(id_crime),cursor.getString(id_signs),cursor.getString(id_u),cursor.getInt(id_a));
               list.add(criminal);
           } while(cursor.moveToNext());
        }

        db.close();
        return list;
    }
}
