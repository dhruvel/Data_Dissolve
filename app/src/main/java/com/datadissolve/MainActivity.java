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
        login_button.setOnClickListener(v -> launchDataSelectionActivity());

        pinEntry = findViewById(R.id.pinEntry);

        Button clear_button = findViewById(R.id.clear_button);

        login_button.setOnClickListener( v -> login_handler());
        clear_button.setOnClickListener(v -> clear_button_handler());
    }

    private void launchDataSelectionActivity() {
        android.content.Intent intent = new android.content.Intent(this, DataSanitizationSelectionActivity.class);
        startActivity(intent);
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
        String correctPin = "1234";

        if(enteredPin.toString().equals(correctPin)) {
            Toast.makeText(this, R.string.toast_login_successful, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, DataSanitizationSelectionActivity.class);
            startActivity(intent);
            finish();
        }

        else {
            Toast.makeText(this, R.string.toast_incorrect_pin_try_again, Toast.LENGTH_SHORT).show();
            enteredPin.setLength(0);
            pinEntry.setText("");
        }
    }
}