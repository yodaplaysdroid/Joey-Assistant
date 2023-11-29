package com.oscarwkl.joey_assistant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SharedMemory;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ConversionActivity extends AppCompatActivity {
    private EditText usdInput;
    private EditText myrInput;
    private EditText cnyInput;
    private ImageView backButton;

    private SharedPreferences sharedPreferences;
    boolean isEditing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set UI to full screen
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_conversion);

        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        usdInput = findViewById(R.id.usd);
        myrInput = findViewById(R.id.myr);
        cnyInput = findViewById(R.id.cny);
        backButton = findViewById(R.id.backButton1);

        // Get rates from local data
        float myrRate = sharedPreferences.getFloat("myr", 0.0f);
        float cnyRate = sharedPreferences.getFloat("cny", 0.0f);

        // Set default values
        usdInput.setText("1.0");
        myrInput.setText(Float.toString(myrRate));
        cnyInput.setText(Float.toString(cnyRate));

        // usdInput Change Listener
        usdInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!isEditing) {
                    isEditing = true;
                    float usdValue;
                    try {
                        usdValue = Float.parseFloat(usdInput.getText().toString());
                    } catch(Exception e) {
                        usdValue = 0;
                    }
                    float myrValue = usdValue * myrRate;
                    float cnyValue = usdValue * cnyRate;
                    myrInput.setText(Float.toString(myrValue));
                    cnyInput.setText(Float.toString(cnyValue));
                    isEditing = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        // myrInput Change Listener
        myrInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!isEditing) {
                    isEditing = true;
                    float myrValue;
                    try {
                        myrValue = Float.parseFloat(myrInput.getText().toString());
                    } catch(Exception e) {
                        myrValue = 0;
                    }
                    float usdValue = myrValue / myrRate;
                    float cnyValue = myrValue / myrRate * cnyRate;
                    usdInput.setText(Float.toString(usdValue));
                    cnyInput.setText(Float.toString(cnyValue));
                    isEditing = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        // cnyInput Change Listener
        cnyInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!isEditing) {
                    isEditing = true;
                    float cnyValue;
                    try {
                        cnyValue = Float.parseFloat(cnyInput.getText().toString());
                    } catch (Exception e) {
                        cnyValue = 0;
                    }
                    float usdValue = cnyValue / cnyRate;
                    float myrValue = cnyValue / cnyRate * myrRate;
                    usdInput.setText(Float.toString(usdValue));
                    myrInput.setText(Float.toString(myrValue));
                    isEditing = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        // backButton onClick Listener
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

