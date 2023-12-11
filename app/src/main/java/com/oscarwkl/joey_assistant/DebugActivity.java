package com.oscarwkl.joey_assistant;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.lang.UCharacter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DebugActivity extends AppCompatActivity {
    private TextView get_drinks_return;
    private Button get_drinks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout pageLayout = new LinearLayout(this);
        pageLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        pageLayout.setOrientation(LinearLayout.VERTICAL);
        pageLayout.setGravity(Gravity.CENTER_VERTICAL);
        pageLayout.setPadding(20, 20, 20, 20);

        get_drinks = new Button(this);
        get_drinks.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        get_drinks.setText("GET MIXUE DRINKS");

        get_drinks_return = new TextView(this);
        get_drinks_return.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        get_drinks_return.setText("GET DRINKS RETURN");

        get_drinks.setOnClickListener(v -> new GetMixueDrinks().execute());

        pageLayout.addView(get_drinks);
        pageLayout.addView(get_drinks_return);

        setContentView(pageLayout);
    }

    private class GetMixueDrinks extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("http://192.168.1.100:8000/mixue/get_drinks/");

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

                return response.toString();
            } catch (IOException e) {
                get_drinks_return.setText(e.toString());
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPostExecute(String jsonString) {
            if (jsonString != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONArray drinks = jsonObject.getJSONArray("data");

                    SharedPreferences sharedPreferences = getSharedPreferences("MIXUE", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("DRINKS", drinks.toString());
                    editor.apply();
                    get_drinks_return.setText(sharedPreferences.getString("DRINKS", "nono"));
                } catch (JSONException e) {
                    get_drinks_return.setText(e.toString());
                }
            }
        }
    }
}
