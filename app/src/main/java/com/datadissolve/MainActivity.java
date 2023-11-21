package com.datadissolve;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import com.datadissolve.ui.DataSanitizationSelectionActivity;

/**
 * Main Activity for the Data Dissolve application.
 * This activity is the first activity that is launched when the application is started.
 * It is responsible for launching the DataSanitizationSelectionActivity.
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button login_btn = findViewById(R.id.loginButton);
        login_btn.setOnClickListener(v -> launchDataSelectionActivity());
    }

    private void launchDataSelectionActivity() {
        android.content.Intent intent = new android.content.Intent(this, DataSanitizationSelectionActivity.class);
        startActivity(intent);
    }
}