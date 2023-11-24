package com.datadissolve.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.datadissolve.R;
import com.google.android.material.slider.Slider;



public class CustomDataSanitizationActivity extends AppCompatActivity {

    private Slider numPatternSlider;
    private Slider numBitsSlider;

    private TextView numPatternSliderValue;
    private TextView numBitsSliderValue;

    private Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_data_sanitization);

        numPatternSlider = findViewById(R.id.numPatternSlider);
        numBitsSlider = findViewById(R.id.numBitsSlider);

        numPatternSliderValue = findViewById(R.id.numPatternSliderValue);
        numBitsSliderValue = findViewById(R.id.numBitSliderValue);


        numPatternSlider.addOnChangeListener((slider, value, fromUser) -> {
            numPatternSliderValue.setText((int) (slider.getValue()));
        });

        numBitsSlider.addOnChangeListener((slider, value, fromUser) -> {
            numBitsSliderValue.setText((int) (slider.getValue()));
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