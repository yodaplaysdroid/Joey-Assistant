package com.oscarwkl.joey_assistant;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    TextView myPageDescrption;

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
        CardView myPageButton = findViewById(R.id.myPageButton);
        CardView aboutUsButton = findViewById(R.id.aboutUsButton);
        TextView timeGreeting = findViewById(R.id.timeGreeting);
        ImageView timeIcon = findViewById(R.id.timeIcon);
        myPageDescrption = findViewById(R.id.myPageDescription);

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

        buyOrNotButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BuyOrNotActivity.class);
            startActivity(intent);
        });

        mixueButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MixueActivity.class);
            startActivity(intent);
        });

        myPageButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("ADMIN LOGIN");

            LinearLayout loginLayout = new LinearLayout(this);
            loginLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            loginLayout.setGravity(Gravity.CENTER);
            loginLayout.setPadding(20, 10, 20, 10);

            EditText password = new EditText(this);
            password.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            password.setHint("ENTER ADMIN PASSWORD");
            password.setInputType(android.text.InputType.TYPE_CLASS_TEXT
                    | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);

            loginLayout.addView(password);

            builder.setPositiveButton("LOGIN", (dialog, which) -> {
                if (password.getText().toString().equals("changge0206")) {
                    Intent intent = new Intent(MainActivity.this, DebugActivity.class);
                    startActivity(intent);
                } else {
                    password.setText(" ");
                    password.setHint("PASSWORD ERROR!");
                }
            });
            builder.setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss());

            builder.setView(loginLayout);
            AlertDialog dialog = builder.create();
            dialog.show();
        });

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
            sharedPreferences.getFloat("CNY", 1.0f) / sharedPreferences.getFloat("MYR", 1.0f));

        TextView whatToEatDescription = findViewById(R.id.whatToEatDescription);
        Random random = new Random();
        ArrayList<String> foods = getArray("FOODS");
        if (foods.size() > 0) {
            whatToEatDescription.setText("今日份美食推荐: " + foods.get(random.nextInt(foods.size())));
        }

        TextView buyOrNotDescription = findViewById(R.id.buyOrNotDescription);
        String[] buy = { "刷卡拿下！", "下次一定！" };
        buyOrNotDescription.setText("今日的风水分析建议: " + buy[random.nextInt(2)]);
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
