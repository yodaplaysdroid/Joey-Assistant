package com.oscarwkl.joey_assistant;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_splash_screen);

        sharedPreferences = getSharedPreferences("CURRENCY", MODE_PRIVATE);

        ImageView loading = findViewById(R.id.loading);
        progressBar = findViewById(R.id.progress_bar);

        Glide.with(this)
                .asGif()
                .load(R.drawable.loading)
                .placeholder(R.drawable.loading)
                .into(loading);

        CountDownTimer timer = new CountDownTimer(1000, 20) {
            int currentProgress = 0;
            @Override
            public void onTick(long millisUntilFinished) {
                currentProgress += 1;
                progressBar.setProgress(currentProgress * 100 / 100);
            }
            @Override
            public void onFinish() {
                progressBar.setProgress(50);
                Date currentDate = Calendar.getInstance().getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String dateString = dateFormat.format(currentDate);

                if (dateString.equals(sharedPreferences.getString("DATE", ""))) {
                    progressBar.setProgress(100);
                    startApp();
                } else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("DATE", dateString);
                    editor.apply();
                    new FetchCurrencyRatesTask().execute();
                }
            }
        };
        timer.start();
    }

    private class FetchCurrencyRatesTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("https://openexchangerates.org/api/latest.json?app_id=77bf6427c94247349c88c01df5795745");

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }
                bufferedReader.close();
                inputStream.close();
                urlConnection.disconnect();

                progressBar.setProgress(80);
                return response.toString();
            } catch (IOException e) {
                e.printStackTrace();
                progressBar.setProgress(60);
                return null;
            }
        }
        @Override
        protected void onPostExecute(String jsonString) {
            if (jsonString != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    double cnyRate = jsonObject.getJSONObject("rates").getDouble("CNY");
                    double myrRate = jsonObject.getJSONObject("rates").getDouble("MYR");

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putFloat("CNY", (float) cnyRate);
                    editor.putFloat("MYR", (float) myrRate);
                    editor.apply();

                    progressBar.setProgress(100);
                    startApp();
                } catch (JSONException e) {
                    progressBar.setProgress(99);
                    e.printStackTrace();
                }
            } else if ((sharedPreferences.getFloat("CNY", 0.0f) != 0.0) && (sharedPreferences.getFloat("MYR", 0.0f)) != 0.0) {
                progressBar.setProgress(100);
                startApp();
            } else {
                progressBar.setProgress(99);
            }
        }
    }
    private void startApp() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}