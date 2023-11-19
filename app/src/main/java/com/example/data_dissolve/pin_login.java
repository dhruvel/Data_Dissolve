package com.example.data_dissolve;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class pin_login extends AppCompatActivity {

    private EditText pinEntry;
    private StringBuilder enteredPin = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_login);

        pinEntry = findViewById(R.id.pinEntry);
    }

    public void onNumericButtonClick(View view) {
        String digit = ((Button) view).getText().toString();
        enteredPin.append(digit);
        pinEntry.setText(enteredPin.toString());
    }

    public void onClearButtonClick(View view) {
        enteredPin.setLength(0);
        pinEntry.setText("");
    }

    public void onLoginButtonClick(View view) {
        String correctPin = "1234";

        if(enteredPin.toString().equals(correctPin)) {
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Incorrect PIN, try again", Toast.LENGTH_SHORT).show();
            enteredPin.setLength(0);
            pinEntry.setText("");
        }
    }
}