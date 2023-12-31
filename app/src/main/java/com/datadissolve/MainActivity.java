package com.datadissolve;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.datadissolve.ui.DataSanitizationSelectionActivity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import android.widget.EditText;


/**
 * Main Activity for the Data Dissolve application.
 * This activity is the first activity that is launched when the application is started.
 * It is responsible for launching the DataSanitizationSelectionActivity.
 */
public class MainActivity extends AppCompatActivity {
    private EditText pinEntry;
    private final StringBuilder enteredPin = new StringBuilder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button login_button = findViewById(R.id.loginButton);

        pinEntry = findViewById(R.id.pinEntry);

        Button clear_button = findViewById(R.id.clear_button);

        login_button.setOnClickListener( v -> login_handler());
        clear_button.setOnClickListener(v -> clear_button_handler());
    }

    public void onNumericButtonClick(View view) {
        String digit = ((Button) view).getText().toString();
        enteredPin.append(digit);
        pinEntry.setText(enteredPin.toString());
    }

    public void clear_button_handler() {
        enteredPin.setLength(0);
        pinEntry.setText("");
    }

    public void login_handler() {
        String correctPin = ""; //TODO: Add correct pin here

        if(enteredPin.toString().equals(correctPin)) {
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, DataSanitizationSelectionActivity.class);
            startActivity(intent);
        }

        else {
            Toast.makeText(this, "Incorrect PIN, try again", Toast.LENGTH_SHORT).show();
            enteredPin.setLength(0);
            pinEntry.setText("");
        }
    }
}