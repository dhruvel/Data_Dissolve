package com.datadissolve.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.datadissolve.R;
import com.google.android.material.slider.Slider;



public class CustomDataSanitizationActivity extends AppCompatActivity {

    private Slider numPatternSlider;
    private Slider numBitsSlider;

    private TextView numPatternSliderValue;
    private TextView numBitsSliderValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_data_sanitization);

        numPatternSlider = findViewById(R.id.numPatternSlider);
        numBitsSlider = findViewById(R.id.numBitsSlider);

        numPatternSliderValue = findViewById(R.id.numPatternSliderValue);
        numBitsSliderValue = findViewById(R.id.numBitSliderValue);
        Button continueButton = findViewById(R.id.continueButton);

        numPatternSlider.addOnChangeListener((slider, value, fromUser) -> {
            numPatternSliderValue.setText((int) (slider.getValue()));

            if(slider.getValue() > 5) {
                AlertFragment warningDialogPatterns = AlertFragment.newInstance("patterns");
                warningDialogPatterns.show(getSupportFragmentManager(), "warning_dialog");
            }
        });

        numBitsSlider.addOnChangeListener((slider, value, fromUser) -> {
            numBitsSliderValue.setText((int) (slider.getValue()));

            if(slider.getValue() > 256) {
                AlertFragment warningDialogBits = AlertFragment.newInstance("bits");
                warningDialogBits.show(getSupportFragmentManager(), "warning_dialog");
            }
        });
        continueButton.setOnClickListener(v -> sendCustomParams());
    }

    private void sendCustomParams() {
        Intent intent = new Intent(this, DataDissolveActivity.class);
        intent.putExtra("selectedDataDissolveMethod", "Custom");
        intent.putExtra("numPatterns", numPatternSlider.getValue());
        intent.putExtra("numBits", numBitsSlider.getValue());
        startActivity(intent);
    }
}