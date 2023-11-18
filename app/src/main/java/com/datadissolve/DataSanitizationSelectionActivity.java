package com.datadissolve;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


public class DataSanitizationSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_sanitization_selection);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ItemListFragment())
                    .commitAllowingStateLoss();
        }
    }

}