package com.oscarwkl.joey_assistant;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class WhatToEatActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private LinearLayout container;
    private TextView chosenFood;
    private Button startStopButton;

    private boolean isShuffling = false;
    private Runnable shuffleRunnable;
    private int currentIndex = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_what_to_eat);

        sharedPreferences = getSharedPreferences("EAT", MODE_PRIVATE);

        ImageView backButton = findViewById(R.id.backButton2);
        FloatingActionButton addButton = findViewById(R.id.addButton);
        ImageView gif1 = findViewById(R.id.gif1);
        container = findViewById(R.id.foodBox);
        chosenFood = findViewById(R.id.chosenFood);
        startStopButton = findViewById(R.id.startStopButton1);

        backButton.setOnClickListener(view -> finish());

        addButton.setOnClickListener(view -> openInputDialog());

        displayFoods();

        Handler handler = new Handler();

        chosenFood.setOnClickListener(v -> {
            if (!chosenFood.getText().toString().equals("我要吃...")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("https://m.amap.com/search/view/keywords=%s", chosenFood.getText().toString())));

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    openError();
                }
            }
        });

        startStopButton.setOnClickListener(v -> {
            ArrayList<String> foods = getArray("FOODS");
            int maxIndex = foods.size();
            if (maxIndex == 0) openError();
            else {
                if (!isShuffling) {
                    startStopButton.setText("STOP");
                    startStopButton.setBackgroundColor(Color.parseColor("#B78E97"));
                    Glide.with(this)
                            .asGif()
                            .load(R.drawable.gif1)
                            .placeholder(R.drawable.gif1)
                            .into(gif1);
                    isShuffling = true;
                    shuffleRunnable = new Runnable() {
                        @Override
                        public void run() {
                            chosenFood.setText(foods.get(currentIndex));
                            currentIndex = (currentIndex + 1) % maxIndex;
                            handler.postDelayed(this, 20);
                        }
                    };
                    handler.post(shuffleRunnable);
                } else {
                    startStopButton.setText("START");
                    startStopButton.setBackgroundColor(Color.parseColor("#478778"));
                    gif1.setImageResource(R.drawable.gif1);
                    isShuffling = false;
                    handler.removeCallbacks(shuffleRunnable);
                }
            }
        });
    }

    public void saveArray(String[] array, String arrayName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        JSONArray jsonArray = new JSONArray(Arrays.asList(array));
        editor.putString(arrayName, jsonArray.toString());
        editor.apply();
    }

    public ArrayList<String> getArray(String arrayName) {
        String storedArrayString = sharedPreferences.getString(arrayName, null);
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

    public void displayFoods() {
        container.removeAllViews();
        ArrayList<String> foods = getArray("FOODS");
        for (int i = 0; i < foods.size(); i++) {
            TextView food = new TextView(WhatToEatActivity.this);
            food.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            food.setId(i);
            food.setClickable(true);
            food.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            food.setTextColor(Color.parseColor("#478778"));
            food.setGravity(Gravity.CENTER);
            food.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);

            int removeIndex = i;
            food.setOnClickListener(v -> {
                foods.remove(removeIndex);
                saveArray(foods.toArray(new String[0]), "FOODS");
                displayFoods();
            });

            food.setText(foods.get(i));
            container.addView(food);
        }
    }

    private void openInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("添加食物");

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.food_input, null);
        builder.setView(dialogView);

        EditText input = dialogView.findViewById(R.id.newFood);
        builder.setPositiveButton("SAVE", (dialog, which) -> {
                ArrayList<String> foods = getArray("FOODS");
                foods.add(input.getText().toString());
                saveArray(foods.toArray(new String[0]), "FOODS");
                displayFoods();
        });
        builder.setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void openError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("炸啦!");

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.error, null);
        builder.setView(dialogView);

        builder.setNegativeButton("好嘟", (dialog, which) -> finish());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
