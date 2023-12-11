package com.oscarwkl.joey_assistant;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class ConversionActivity extends AppCompatActivity {
    private EditText usdInput;
    private EditText myrInput;
    private EditText cnyInput;
    boolean isEditing = false;

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_conversion);

        SharedPreferences sharedPreferences = getSharedPreferences("CURRENCY", MODE_PRIVATE);
        ImageView backButton = findViewById(R.id.backButton1);
        LinearLayout rootLayout = findViewById(R.id.conversionLayout);
        usdInput = findViewById(R.id.usd);
        myrInput = findViewById(R.id.myr);
        cnyInput = findViewById(R.id.cny);

        float myrRate = sharedPreferences.getFloat("MYR", 0.0f);
        float cnyRate = sharedPreferences.getFloat("CNY", 0.0f);

        usdInput.setText("1.0");
        myrInput.setText(Float.toString(myrRate));
        cnyInput.setText(Float.toString(cnyRate));

        usdInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

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
            public void afterTextChanged(Editable editable) {}
        });

        myrInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

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
            public void afterTextChanged(Editable editable) {}
        });

        cnyInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

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
            public void afterTextChanged(Editable editable) {}
        });

        backButton.setOnClickListener(view -> finish());

        rootLayout.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                View focusedView = getCurrentFocus();
                if (focusedView instanceof EditText) {
                    System.out.println("meep");
                    Rect outRect = new Rect();
                    focusedView.getGlobalVisibleRect(outRect);

                    // Check if touch was outside the EditText
                    if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                        // Hide the keyboard
                        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
                        focusedView.clearFocus(); // Optionally, clear focus from EditText
                    }
                }
            }
            return false;
        });
    }
}

