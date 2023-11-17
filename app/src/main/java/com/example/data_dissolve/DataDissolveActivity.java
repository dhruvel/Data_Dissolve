package com.example.data_dissolve;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.Random;

/**
 * This activity is used to pick a document and dissolve.
 */
public class DataDissolveActivity extends AppCompatActivity {
    private static final int PICK_FILE_REQUEST = 1;
    private static final String TAG = "DataDissolveActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_disssolve);

        Button btnFileSelection = findViewById(R.id.FilePickerButton);
        btnFileSelection.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestDocument();
            }
        });
    }

    private void requestDocument() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(intent, PICK_FILE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null) {
            // Get the Uri of the selected file
            Uri uri = data.getData();
            DissolveData(uri);
        }
    }

    private void DissolveData(Uri fileUri) {
        try {
            ParcelFileDescriptor pfd = getContentResolver().openFileDescriptor(fileUri, "w");
            assert pfd != null;
            FileOutputStream outputStream = new FileOutputStream(pfd.getFileDescriptor());

            byte[] data = new byte[1024];
            new Random().nextBytes(data);

            FileChannel fileChannel = outputStream.getChannel();
            long fileSize = fileChannel.size();

            for (long i = 0; i < fileSize; i += data.length) {
                outputStream.write(data);
            }

            outputStream.close();
            pfd.close();
            Toast.makeText(this, "Dissolve data successfully", Toast.LENGTH_SHORT).show();
            // Delete the file
            DocumentFile documentFile = DocumentFile.fromSingleUri(this, fileUri);
//            if (documentFile != null) {
//                documentFile.delete();
//            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Dissolve data failed", Toast.LENGTH_SHORT).show();
        }
    }
}