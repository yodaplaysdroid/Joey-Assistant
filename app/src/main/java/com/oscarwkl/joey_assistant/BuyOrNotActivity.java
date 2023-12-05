package com.oscarwkl.joey_assistant;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BuyOrNotActivity extends AppCompatActivity {
    private int times = 0;
    private Runnable shuffleRunnable;
    private int currentIndex = 0;
    private final String[] displays = { "刷卡拿下！", "下次一定！" };

    private int buyProbability = 0;
    private int notBuyProbability = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_buy_or_not);

        Button startStopButton = findViewById(R.id.startStopButton2);
        ImageView backButton = findViewById(R.id.backButton3);
        ImageView gif2 = findViewById(R.id.gif2);
        TextView buy = findViewById(R.id.buy);
        TextView tempResults = findViewById(R.id.results);

        backButton.setOnClickListener(view -> finish());

        Handler handler = new Handler();

        startStopButton.setOnClickListener(v -> {
            if (times == 0) {
                buyProbability = 0;
                notBuyProbability = 0;
                tempResults.setText("刷卡下单     0 : 0     下次一定");
                times++;
                startStopButton.setText(Integer.toString(times));
                startStopButton.setBackgroundColor(Color.parseColor("#AEA042"));
                Glide.with(this)
                        .asGif()
                        .load(R.drawable.gif2)
                        .placeholder(R.drawable.gif2)
                        .into(gif2);
                shuffleRunnable = new Runnable() {
                    @Override
                    public void run() {
                        buy.setText(displays[currentIndex]);
                        currentIndex = (currentIndex + 1) % 2;
                        handler.postDelayed(this, 20);
                    }
                };
                handler.post(shuffleRunnable);
            } else {
                times++;

                if (currentIndex == 0) buyProbability++;
                else notBuyProbability++;
                tempResults.setText("刷卡下单     " + buyProbability + " : " + notBuyProbability + "     下次一定");

                if (times == 10) {
                    times = 0;
                    startStopButton.setText("START");
                    startStopButton.setBackgroundColor(Color.parseColor("#478778"));
                    gif2.setImageResource(R.drawable.gif2);
                    handler.removeCallbacks(shuffleRunnable);
                    buy.setText((buyProbability > notBuyProbability) ? "刷卡下单！" : "下次一定！");
                } else if (times == 9) {
                    startStopButton.setText(Integer.toString(times));
                    startStopButton.setBackgroundColor(Color.parseColor("#B78E97"));
                } else {
                    startStopButton.setText(Integer.toString(times));
                }
            }
        });
    }
}
