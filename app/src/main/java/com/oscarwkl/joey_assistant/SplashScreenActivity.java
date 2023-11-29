package com.oscarwkl.joey_assistant;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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

public class SplashScreenActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private ImageView loading;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        loading = findViewById(R.id.loading);
        progressBar = findViewById(R.id.progress_bar);

        // Adding splash screen gif
        Glide.with(this)
                .asGif()
                .load(R.drawable.loading)
                .placeholder(R.drawable.loading)
                .into(loading);

        // CountDownTimer for 1s with interval 20ms for loading animation
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
                // Get the current date
                Date currentDate = Calendar.getInstance().getTime();

                // Convert the date to a string using a SimpleDateFormat
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String dateString = dateFormat.format(currentDate);

                if (dateString.equals(sharedPreferences.getString("date", "null"))) {
                    progressBar.setProgress(100);
                    startApp();
                } else {
                    // Save the date to SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("date", dateString);
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
                // URL to fetch JSON data (Replace with your actual URL)
                URL url = new URL("https://openexchangerates.org/api/latest.json?app_id=77bf6427c94247349c88c01df5795745");

                // Establish connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");

                // Get response as string
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }

                // Close streams
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
                    // Parse JSON response
                    JSONObject jsonObject = new JSONObject(jsonString);

                    // Get the value of rates
                    double cnyRate = jsonObject.getJSONObject("rates").getDouble("CNY");
                    double myrRate = jsonObject.getJSONObject("rates").getDouble("MYR");

                    // Save cnyRate to SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putFloat("cny", (float) cnyRate);
                    editor.putFloat("myr", (float) myrRate);
                    editor.apply();

                    progressBar.setProgress(100);
                    startApp();
                } catch (JSONException e) {
                    progressBar.setProgress(99);
                    e.printStackTrace();
                }
            } else if ((sharedPreferences.getFloat("cny", 0.0f) != 0.0) && (sharedPreferences.getFloat("myr", 0.0f)) != 0.0) {
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