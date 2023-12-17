package com.oscarwkl.joey_assistant;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MuyuActivity extends AppCompatActivity {
    private int widthInPixels;
    SharedPreferences sharedPreferences;
    int localtime = 0;
    private TextView floatMuyu;
    private MediaPlayer mediaPlayer;

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_muyu);

        sharedPreferences = getSharedPreferences("MUYU", MODE_PRIVATE);

        ImageView backButton = findViewById(R.id.backButton5);
        ImageView muyumuyu = findViewById(R.id.muyumuyu);
        TextView times = findViewById(R.id.times);
        floatMuyu = findViewById(R.id.localtime);

        mediaPlayer = MediaPlayer.create(this, R.raw.muyu_sound);

        int widthInDp = 300;
        float scale = getResources().getDisplayMetrics().density;
        widthInPixels = (int) (widthInDp * scale + 0.5f);

        backButton.setOnClickListener(view -> finish());

        muyumuyu.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    ViewGroup.LayoutParams params = muyumuyu.getLayoutParams();
                    params.width = widthInPixels;
                    muyumuyu.setLayoutParams(params);
                    int timesNumber = sharedPreferences.getInt("TIMES", 0);
                    timesNumber++;
                    localtime++;
                    times.setText(String.valueOf(timesNumber));
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("TIMES", timesNumber);
                    editor.apply();

                    playSoundEffect();

                    floatMuyu.setText("+1 (x" + localtime + ")");

                    floatMuyu.setVisibility(View.VISIBLE);
                    Animation fadeOut = new AlphaAnimation(1, 0);
                    fadeOut.setDuration(2000); // Set duration for the animation
                    fadeOut.setFillAfter(true); // Keep the final state after the animation

                    floatMuyu.startAnimation(fadeOut);

                    new Handler().postDelayed(() -> floatMuyu.setVisibility(View.INVISIBLE), 2000);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    ViewGroup.LayoutParams params2 = muyumuyu.getLayoutParams();
                    params2.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    muyumuyu.setLayoutParams(params2);
                    break;
            }
            return false;
        });
    }

    private void playSoundEffect() {
        if (mediaPlayer != null) {
            // Reset the media player to start the sound from the beginning
            mediaPlayer.seekTo(0);
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release(); // Release the MediaPlayer when the activity is destroyed
            mediaPlayer = null;
        }
    }
}
