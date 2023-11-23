package com.datadissolve.util;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;

import com.datadissolve.R;
import com.google.android.material.button.MaterialButton;
import com.datadissolve.ui.FileList;





public class storagespaceoptimization extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storagespace);

        MaterialButton storageBtn = findViewById(R.id.optimize_button);

        storageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //permission allowed
                    Intent intent = new Intent(storagespaceoptimization.this, FileList.class);
                    String path = Environment.getExternalStorageDirectory().getPath();
                    intent.putExtra("path",path);
                    startActivity(intent);
                    //permission not allowed
                    requestPermission();

                }

        });

    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(storagespaceoptimization.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else
            return false;
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(storagespaceoptimization.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(storagespaceoptimization.this, "Storage permission is requires,please allow from settings", Toast.LENGTH_SHORT).show();
        } else
            ActivityCompat.requestPermissions(storagespaceoptimization.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 222);
    }
}
