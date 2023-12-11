package com.oscarwkl.joey_assistant;

import android.app.AlertDialog;
import android.content.Context;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;


public class MixueActivity extends AppCompatActivity {
    private final String[] defaultMenu = {
            "珍珠奶茶", "满杯百香果", "冰打鲜橙", "椰果奶茶", "原味奶茶", "红豆奶茶",
            "芋圆奶茶", "三拼霸霸奶茶", "燕麦奶茶", "布丁奶茶", "双拼奶茶",
            "菠萝甜心橙", "芋圆葡萄", "百香芒芒", "莓果三姐妹", "草莓啵啵",
            "港式杨枝甘露", "桑葚莓莓", "冰鲜柠檬水", "蜜桃四季春",
    };
    private String[] menu;
    private LinearLayout container;
    private TextView chosenDrink;
    private Button startStopButton;

    private boolean isShuffling = false;
    private Runnable shuffleRunnable;
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_mixue);

        ImageView backButton = findViewById(R.id.backButton4);
        ImageView gif3 = findViewById(R.id.gif3);
        Button findMixue = findViewById(R.id.mixueNav);
        container = findViewById(R.id.drinkBox);
        chosenDrink = findViewById(R.id.chosenDrink);
        startStopButton = findViewById(R.id.startStopButton3);

        backButton.setOnClickListener(view -> finish());

        displayDrinks();

        Handler handler = new Handler();

        startStopButton.setOnClickListener(v -> {
            int maxIndex = menu.length;
            if (maxIndex == 0) openError();
            else {
                if (!isShuffling) {
                    startStopButton.setText("STOP");
                    startStopButton.setBackgroundColor(Color.parseColor("#B78E97"));
                    Glide.with(this)
                            .asGif()
                            .load(R.drawable.gif3)
                            .placeholder(R.drawable.gif3)
                            .into(gif3);
                    isShuffling = true;
                    shuffleRunnable = new Runnable() {
                        @Override
                        public void run() {
                            chosenDrink.setText(menu[currentIndex]);
                            currentIndex = (currentIndex + 1) % maxIndex;
                            handler.postDelayed(this, 20);
                        }
                    };
                    handler.post(shuffleRunnable);
                } else {
                    startStopButton.setText("START");
                    startStopButton.setBackgroundColor(Color.parseColor("#478778"));
                    gif3.setImageResource(R.drawable.gif3);
                    isShuffling = false;
                    handler.removeCallbacks(shuffleRunnable);
                }
            }
        });

        findMixue.setOnClickListener(v -> {
            String url = "androidamap://poi?sourceApplication=joeyassistant&keywords=蜜雪冰城&dev=0";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.setPackage("com.autonavi.minimap");

            try {
                startActivity(intent);
            } catch (Exception e) {
                openError();
            }
        });
    }

    public void displayDrinks() {
        SharedPreferences sharedPreferences = getSharedPreferences("MIXUE", Context.MODE_PRIVATE);
        String drinks = sharedPreferences.getString("DRINKS", null);
        if (drinks != null) {
            try {
                JSONArray jsonArray = new JSONArray(drinks);
                menu = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    menu[i] = jsonArray.getString(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                menu = defaultMenu;
            }
        } else {
            menu = defaultMenu;
        }
        for (int i = 0; i < menu.length; i++) {
            TextView food = new TextView(MixueActivity.this);
            food.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            food.setClickable(true);
            food.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            food.setTextColor(Color.parseColor("#BB7480"));
            food.setGravity(Gravity.CENTER);
            food.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
            food.setText(menu[i]);

            container.addView(food);
        }
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

