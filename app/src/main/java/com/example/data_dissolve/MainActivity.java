package com.example.data_dissolve;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login_btn = findViewById(R.id.loginButton);

        login_btn.setOnClickListener(v -> {
            // TODO : click event
            launchDataDissolveActivity();
        });

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new open_app_guide())
                .commit();

    }

    private void launchDataDissolveActivity() {
        android.content.Intent intent = new android.content.Intent(this, DataDissolveActivity.class);
        startActivity(intent);

    }
}