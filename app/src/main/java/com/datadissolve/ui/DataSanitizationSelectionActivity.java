package com.datadissolve.ui;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.datadissolve.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * This activity is the main activity for the app. It is responsible for loading the fragment
 * that contains the list of items to be sanitized.
 */
public class DataSanitizationSelectionActivity extends AppCompatActivity {
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        fab = findViewById(R.id.fab);
        setupFab();
        if (savedInstanceState == null) {
            loadFragment();
        }
    }

    private void setupFab() {
        fab.setOnClickListener(view -> {
            DhruvInfoDialog customDialog = new DhruvInfoDialog(this);
            TextView authorNameTextView = customDialog.findViewById(R.id.author_name_text_view);
            TextView activityNameTextView = customDialog.findViewById(R.id.activity_name_text_view);
            TextView versionTextView = customDialog.findViewById(R.id.version_text_view);
            authorNameTextView.setText(R.string.author_dhruv);
            activityNameTextView.setText(String.format("%s%s", getString(R.string.infoBarActivity), this.getClass().getSimpleName()));
            versionTextView.setText(String.format("%s%s", getString(R.string.appInfoVersionNumber), getAppVersion()));
            customDialog.show();
        });
    }

    private String getAppVersion() {
        String appVersion;
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            appVersion = packageInfo.versionName;
            return appVersion;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "Unknown";
    }

    public void loadFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new ItemListFragment(), "ItemListFragment")
                .commitAllowingStateLoss();
    }
}