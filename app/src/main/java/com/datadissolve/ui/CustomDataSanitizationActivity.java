package com.datadissolve.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.datadissolve.R;
import com.google.android.material.slider.Slider;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBar;

import java.util.concurrent.atomic.AtomicInteger;


public class CustomDataSanitizationActivity extends AppCompatActivity {

    private TextView numPatternSliderValueLabel;
    private TextView numBitsSliderValueLabel;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_data_sanitization);

        final boolean[] patternWarningShown = {false};
        final boolean[] bitsWarningShown = {false};

        AtomicInteger numPatternSliderValue = new AtomicInteger();
        AtomicInteger numBitsSliderValue = new AtomicInteger();

        Slider numPatternSlider = findViewById(R.id.numPatternSlider);
        Slider numBitsSlider = findViewById(R.id.numBitsSlider);

        numPatternSliderValue.set(3);
        numBitsSliderValue.set(128);

        numPatternSliderValueLabel = findViewById(R.id.numPatternSliderValue);
        numBitsSliderValueLabel = findViewById(R.id.numBitSliderValue);

        Button continueButton = findViewById(R.id.continueButton);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable the Up button
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        numPatternSlider.addOnChangeListener((slider, value, fromUser) -> {
            numPatternSliderValue.set((int) value);
            numPatternSliderValueLabel.setText(Float.toString(value));

            if(numPatternSliderValue.get() > 5 && !patternWarningShown[0]) {
                AlertFragment warningDialogBits = AlertFragment.newInstance("patterns");
                warningDialogBits.show(getSupportFragmentManager(), "warning_dialog");
                patternWarningShown[0] = true;
            }
            else if (numPatternSliderValue.get() < 5) {
                patternWarningShown[0] = false;
            }
        });

        numBitsSlider.addOnChangeListener((slider, value, fromUser) -> {
            numBitsSliderValueLabel.setText(Float.toString(value));
            numBitsSliderValue.set((int) value);

           if(numBitsSliderValue.get() > 256 && !bitsWarningShown[0]) {
               AlertFragment warningDialogBits = AlertFragment.newInstance("bits");
               warningDialogBits.show(getSupportFragmentManager(), "warning_dialog");
               bitsWarningShown[0] = true;
           }

           else if (numBitsSliderValue.get() < 256) {
               bitsWarningShown[0] = false;
           }
      });

        Log.d("sendParams", "patterns: " + numBitsSliderValue.get());
        Log.d("sendParams", "bits: " + numBitsSliderValue.get());

        continueButton.setOnClickListener(v -> sendCustomParams(numPatternSliderValue.get(), numBitsSliderValue.get()));
    }

    private void sendCustomParams(int numPatternSliderValue, int numBitsSliderValue) {
        Log.d("sendParams", "" + numPatternSliderValue);
        Log.d("sendParams", "" + numBitsSliderValue);

        Intent intent = new Intent(this, DataDissolveActivity.class);
        intent.putExtra("selectedDataDissolveMethod", "Custom");
        intent.putExtra("customNumPatterns", numPatternSliderValue);
        intent.putExtra("customNumBits", numBitsSliderValue);
        startActivity(intent);
    }
}