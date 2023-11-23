package com.datadissolve.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;

import com.datadissolve.R;
import com.datadissolve.util.DataSanitization;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URI;

public class CustomDataSanitizationActivity extends AppCompatActivity {

    private static int PICK_FILE_REQUEST = 1;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_data_sanitization);
    }

    //TODO file request
    private void requestDocument() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(intent, PICK_FILE_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null) {
            uri = data.getData();
            DissolveData(uri);
        }
    }

    private void DissolveData(Uri fileUri) {
        DataDissolveActivity.DataDissolveAsyncTask asyncTask = new DataDissolveActivity.DataDissolveAsyncTask(this, progressBar, fileUri, DataDissolveCustom);
        asyncTask.execute();
    }

    private void DataDissolveCustom(Uri fileUri) {
        try {
            ParcelFileDescriptor pfd = getContentResolver().openFileDescriptor(fileUri,"rw");
            assert pfd != null;

            FileInputStream fileInputStream = new FileInputStream(pfd.getFileDescriptor());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int bytesRead;
            byte[] buffer = new byte[1024];

            while((bytesRead = fileInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }

            byte[] data = byteArrayOutputStream.toByteArray();
           //TODO DataSanitization.wipeDataCustom()

            FileOutputStream fileOutputStream = new FileOutputStream(pfd.getFileDescriptor());
            fileOutputStream.write(data);

            fileInputStream.close();
            fileOutputStream.close();
            pfd.close();

            //TODO make toast
        }
        catch (Exception e){
            e.printStackTrace();
            //TODO make toast
        }
    }

}