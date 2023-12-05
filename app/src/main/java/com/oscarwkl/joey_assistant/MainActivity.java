package com.oscarwkl.joey_assistant;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("CURRENCY", MODE_PRIVATE);

        CardView conversionButton = findViewById(R.id.conversionButton);
        CardView whatToEatButton = findViewById(R.id.whatToEatButton);
        CardView buyOrNotButton = findViewById(R.id.buyOrNotButton);
        CardView mixueButton = findViewById(R.id.mixueButton);
        CardView escapeRoomButton = findViewById(R.id.escapeRoomButton);
        CardView myPageButton = findViewById(R.id.myPageButton);
        CardView aboutUsButton = findViewById(R.id.aboutUsButton);
        TextView timeGreeting = findViewById(R.id.timeGreeting);
        ImageView timeIcon = findViewById(R.id.timeIcon);

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        if (hour >= 19 || hour < 4) {
            timeGreeting.setText("晚上好！");
            timeIcon.setImageResource(R.drawable.moon);
        } else if (hour < 12) {
            timeGreeting.setText("早上好！");
            timeIcon.setImageResource(R.drawable.morning);
        } else if (hour < 17) {
            timeGreeting.setText("中午好！");
            timeIcon.setImageResource(R.drawable.sun);
        } else {
            timeGreeting.setText("傍上好！");
            timeIcon.setImageResource(R.drawable.evening);
        }

        conversionButton.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    conversionButton.setCardBackgroundColor(Color.parseColor("#C2ADEA"));
                    conversionButton.setElevation(0);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    conversionButton.setCardBackgroundColor(Color.parseColor("#B19CD9"));
                    conversionButton.setElevation(10);
                    break;
            }
            return false;
        });

        whatToEatButton.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    whatToEatButton.setCardBackgroundColor(Color.parseColor("#ACE4C5"));
                    whatToEatButton.setElevation(0);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    whatToEatButton.setCardBackgroundColor(Color.parseColor("#9BD3B4"));
                    whatToEatButton.setElevation(10);
                    break;
            }
            return false;
        });

        buyOrNotButton.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    buyOrNotButton.setCardBackgroundColor(Color.parseColor("#86BBEC"));
                    buyOrNotButton.setElevation(0);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    buyOrNotButton.setCardBackgroundColor(Color.parseColor("#75AADB"));
                    buyOrNotButton.setElevation(10);
                    break;
            }
            return false;
        });

        mixueButton.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mixueButton.setCardBackgroundColor(Color.parseColor("#FFB9C1"));
                    mixueButton.setElevation(0);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    mixueButton.setCardBackgroundColor(Color.parseColor("#FFA8B0"));
                    mixueButton.setElevation(10);
                    break;
            }
            return false;
        });

        escapeRoomButton.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    escapeRoomButton.setCardBackgroundColor(Color.parseColor("#FFE4F1"));
                    escapeRoomButton.setElevation(0);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    escapeRoomButton.setCardBackgroundColor(Color.parseColor("#FFD3E0"));
                    escapeRoomButton.setElevation(10);
                    break;
            }
            return false;
        });

        myPageButton.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    myPageButton.setCardBackgroundColor(Color.parseColor("#FFF193"));
                    myPageButton.setElevation(0);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    myPageButton.setCardBackgroundColor(Color.parseColor("#FFE082"));
                    myPageButton.setElevation(10);
                    break;
            }
            return false;
        });

        aboutUsButton.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    aboutUsButton.setCardBackgroundColor(Color.parseColor("#EDEDED"));
                    aboutUsButton.setElevation(0);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    aboutUsButton.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    aboutUsButton.setElevation(8);
                    break;
            }
            return false;
        });

        conversionButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ConversionActivity.class);
            startActivity(intent);
        });

        whatToEatButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WhatToEatActivity.class);
            startActivity(intent);
        });

        buyOrNotButton.setOnClickListener(v -> openError());

        mixueButton.setOnClickListener(v -> openError());

        escapeRoomButton.setOnClickListener(v -> openError());

        myPageButton.setOnClickListener(v -> openError());

        aboutUsButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("关于我们");

            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.about_us, null);
            builder.setView(dialogView);

            builder.setPositiveButton("知道啦，谢谢！", (dialog, which) -> dialog.dismiss());

            AlertDialog dialog = builder.create();
            dialog.show();
        });

        TextView conversionDescription = findViewById(R.id.conversionButtonDescription);
        conversionDescription.setText("今日份 MYRCNY 汇率: " +
            Float.toString(sharedPreferences.getFloat("CNY", 1.0f) /
                sharedPreferences.getFloat("MYR", 1.0f)));

        TextView whatToEatDescription = findViewById(R.id.whatToEatDescription);
        Random random = new Random();
        ArrayList<String> foods = getArray("FOODS");

        if (foods.size() > 0) {
            whatToEatDescription.setText("今日份美食推荐: " + foods.get(random.nextInt(foods.size())));
        }
    }

    private void openError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("炸啦!");

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.error, null);
        builder.setView(dialogView);

        builder.setNegativeButton("好嘟", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public ArrayList<String> getArray(String arrayName) {
        SharedPreferences prefs = getSharedPreferences("EAT", Context.MODE_PRIVATE);
        String storedArrayString = prefs.getString(arrayName, null);
        if (storedArrayString != null) {
            try {
                JSONArray jsonArray = new JSONArray(storedArrayString);
                String[] array = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    array[i] = jsonArray.getString(i);
                }
                return new ArrayList<>(Arrays.asList(array));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }
}
