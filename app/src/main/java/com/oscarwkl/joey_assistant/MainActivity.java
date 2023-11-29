package com.oscarwkl.joey_assistant;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set UI to full screen
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        setContentView(R.layout.activity_main);

        CardView conversionButton = findViewById(R.id.conversionButton);
        CardView whatToEatButton = findViewById(R.id.whatToEatButton);
        CardView buyOrNotButton = findViewById(R.id.buyOrNotButton);
        CardView mixueButton = findViewById(R.id.mixueButton);
        CardView escapeRoomButton = findViewById(R.id.escapeRoomButton);
        CardView myPageButton = findViewById(R.id.myPageButton);
        CardView aboutUsButton = findViewById(R.id.aboutUsButton);

        // conversionButton effects
        conversionButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Change the background color and elevation when pressed
                        conversionButton.setCardBackgroundColor(Color.parseColor("#C2ADEA"));
                        conversionButton.setElevation(0);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        // Change the background color and elevation when released
                        conversionButton.setCardBackgroundColor(Color.parseColor("#B19CD9"));
                        conversionButton.setElevation(10);
                        break;
                }
                return true;
            }
        });
        // whatToEatButton effects
        whatToEatButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Change the background color and elevation when pressed
                        whatToEatButton.setCardBackgroundColor(Color.parseColor("#ACE4C5"));
                        whatToEatButton.setElevation(0);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        // Change the background color and elevation when released
                        whatToEatButton.setCardBackgroundColor(Color.parseColor("#9BD3B4"));
                        whatToEatButton.setElevation(10);
                        break;
                }
                return true;
            }
        });
        // buyOrNotButton effects
        buyOrNotButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Change the background color and elevation when pressed
                        buyOrNotButton.setCardBackgroundColor(Color.parseColor("#86BBEC"));
                        buyOrNotButton.setElevation(0);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        // Change the background color and elevation when released
                        buyOrNotButton.setCardBackgroundColor(Color.parseColor("#75AADB"));
                        buyOrNotButton.setElevation(10);
                        break;
                }
                return true;
            }
        });
        // mixueButton effects
        mixueButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Change the background color and elevation when pressed
                        mixueButton.setCardBackgroundColor(Color.parseColor("#FFB9C1"));
                        mixueButton.setElevation(0);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        // Change the background color and elevation when released
                        mixueButton.setCardBackgroundColor(Color.parseColor("#FFA8B0"));
                        mixueButton.setElevation(10);
                        break;
                }
                return true;
            }
        });
        // escapeRoomButton effects
        escapeRoomButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Change the background color and elevation when pressed
                        escapeRoomButton.setCardBackgroundColor(Color.parseColor("#FFE4F1"));
                        escapeRoomButton.setElevation(0);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        // Change the background color and elevation when released
                        escapeRoomButton.setCardBackgroundColor(Color.parseColor("#FFD3E0"));
                        escapeRoomButton.setElevation(10);
                        break;
                }
                return true;
            }
        });
        // myPageButton effects
        myPageButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Change the background color and elevation when pressed
                        myPageButton.setCardBackgroundColor(Color.parseColor("#FFF193"));
                        myPageButton.setElevation(0);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        // Change the background color and elevation when released
                        myPageButton.setCardBackgroundColor(Color.parseColor("#FFE082"));
                        myPageButton.setElevation(10);
                        break;
                }
                return true;
            }
        });
        // aboutUsButton effects
        aboutUsButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Change the background color and elevation when pressed
                        aboutUsButton.setCardBackgroundColor(Color.parseColor("#EDEDED"));
                        aboutUsButton.setElevation(0);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        // Change the background color and elevation when released
                        aboutUsButton.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                        aboutUsButton.setElevation(8);
                        break;
                }
                return true;
            }
        });
    }
}
