package com.oscarwkl.joey_assistant;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class WhatToEatActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private ImageView backButton;
    private LinearLayout container;
    private TextView chosenFood;
    private Button startStopButton;

    private boolean isShuffling = false;
    private Runnable shuffleRunnable;
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set UI to full screen
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        setContentView(R.layout.activity_what_to_eat);

        sharedPreferences = getSharedPreferences("whatToEat", MODE_PRIVATE);

        backButton = findViewById(R.id.backButton2);
        container = findViewById(R.id.foodBox);
        chosenFood = findViewById(R.id.chosenFood);
        startStopButton = findViewById(R.id.startStopButton);

        // backButton onClick Listener
        backButton.setOnClickListener(view -> finish());

        FloatingActionButton addButton = findViewById(R.id.addButton);

        displayFoods();

        // Add Listener to add button
        addButton.setOnClickListener(view -> openInputDialog());

        Handler handler = new Handler();

        startStopButton.setOnClickListener(v -> {
            ArrayList<String> foods = getArray("foods");
            int maxIndex = foods.size();
            if (maxIndex == 0) openError();
            else {
                if (!isShuffling) {
                    startStopButton.setText("STOP");
                    startStopButton.setBackgroundColor(Color.parseColor("#B78E97"));
                    isShuffling = true;
                    shuffleRunnable = new Runnable() {
                        @Override
                        public void run() {
                            chosenFood.setText(foods.get(currentIndex));
                            currentIndex = (currentIndex + 1) % (maxIndex);
                            handler.postDelayed(this, 20);
                        }
                    };
                    handler.post(shuffleRunnable);
                } else {
                    startStopButton.setText("START");
                    startStopButton.setBackgroundColor(Color.parseColor("#478778"));
                    isShuffling = false;
                    handler.removeCallbacks(shuffleRunnable);
                }
            }
        });
    }

    // Convert array to JSON and save to SharedPreferences
    public void saveArray(String[] array, String arrayName) {
        SharedPreferences prefs = getSharedPreferences("whatToEat", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray jsonArray = new JSONArray(Arrays.asList(array));
        editor.putString(arrayName, jsonArray.toString());
        editor.apply();
    }

    // Retrieve array from SharedPreferences
    public ArrayList<String> getArray(String arrayName) {
        SharedPreferences prefs = getSharedPreferences("whatToEat", Context.MODE_PRIVATE);
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
        return new ArrayList<String>();
    }

    // Display Foods
    public void displayFoods() {
        container.removeAllViews();
        ArrayList<String> foods = getArray("foods");
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
            int finalI = i;
            food.setOnClickListener(v -> {
                foods.remove(finalI);
                saveArray(foods.toArray(new String[0]), "foods");
                displayFoods();
            });

            food.setText(foods.get(i));
            container.addView(food);
        }
    }

    // Method to create and show the input dialog/modal
    private void openInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("添加食物");

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.food_input, null);
        builder.setView(dialogView);

        EditText input = dialogView.findViewById(R.id.newFood);
        builder.setPositiveButton("SAVE", (dialog, which) -> {
                ArrayList<String> foods = getArray("foods");
                foods.add(input.getText().toString());
                saveArray(foods.toArray(new String[0]), "foods");
                displayFoods();
        });
        builder.setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // error page when crash
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
