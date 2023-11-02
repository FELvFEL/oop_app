package com.fel.oop;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public class SearchTest {
    public ActivityTestRule<Find> mActivityRule1 = new ActivityTestRule<>(Find.class);
    public ActivityTestRule<addCriminal> mActivityRule = new ActivityTestRule<>(addCriminal.class);

    @Test
    public void testFindSuccess() {

        mActivityRule.launchActivity(new Intent());

        // Ожидаем, что активность запустится
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Вводим все данные о преступнике
        onView(withId(R.id.name_in)).perform(typeText("Chikatilo"), closeSoftKeyboard());
        onView(withId(R.id.city_in)).perform(replaceText("Russia"), closeSoftKeyboard());
        onView(withId(R.id.crime_in)).perform(click(), typeText("Murders"), closeSoftKeyboard());
        onView(withId(R.id.birthdate_in)).perform(click(), typeText("1.02.1963"), closeSoftKeyboard());
        onView(withId(R.id.signs_in)).perform(click(), typeText("skinny head"), closeSoftKeyboard());

        // Получить контекст активити
        Context context = mActivityRule.getActivity().getApplicationContext();

        // Используйте контекст для получения ресурсов
        Drawable drawable = context.getResources().getDrawable(R.drawable.oleg);

        // Преобразовать Drawable в Bitmap
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

        // Сохранить Bitmap во временном файле
        File file = saveBitmapToFile(context,bitmap);

        // Получить URI временного файла
        Uri uri = Uri.fromFile(file);
        addCriminal activity = mActivityRule.getActivity();
        activity.setSuri(uri.toString());

        // Нажимаем на кнопку "Добавить"
        onView(withId(R.id.ok_in)).perform(click());

        mActivityRule1.launchActivity(new Intent());

        // Ожидаем, что активность запустится
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Вводим Имя искомого преступника
        onView(withId(R.id.f_input)).perform(replaceText("Chikatilo"), closeSoftKeyboard());

        onView(withId(R.id.f_button)).perform(click());

    }
    @Test
    public void testFindUnSuccess() {
        mActivityRule1.launchActivity(new Intent());

        // Ожидаем, что активность запустится
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // Нажимаем на кнопку "Добавить"
        onView(withId(R.id.f_button)).perform(click());

    }

    private File saveBitmapToFile(Context context, Bitmap bitmap) {
        // Создать временный файл
        File file = new File(context.getCacheDir(), "temp_image.jpg");

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

