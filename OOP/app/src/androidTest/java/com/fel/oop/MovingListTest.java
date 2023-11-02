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
public class MovingListTest {
    public ActivityTestRule<spisok> mActivityRule = new ActivityTestRule<>(spisok.class);

    @Test
    public void testMove() throws InterruptedException {
        mActivityRule.launchActivity(new Intent());

        // Ожидаем, что активность запустится
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.prev)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.next)).perform(click());

    }
    @Test
    public void testMoveFast() throws InterruptedException {
        mActivityRule.launchActivity(new Intent());

        // Ожидаем, что активность запустится
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Нажимаем на кнопку "Вперёд" быстро 20 раз
        for (int i=0; i<20; i+=1) {
            onView(withId(R.id.next)).perform(click());
        }
    }

}
