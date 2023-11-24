package com.datadissolve.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;

import com.datadissolve.util.DataSanitization;
import com.datadissolve.R;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.Random;

/**
 * This activity is used to pick a document and dissolve.
 * @noinspection ALL
 */
public class DataDissolveActivity extends AppCompatActivity {
    private static final int PICK_FILE_REQUEST = 1;
    private String selectedMethod = "Default";
    private ProgressBar progressBar;
    private TextView progressText;
    private ImageView successImage;
    private TextView backBtn;
    private DocumentFile documentFile;

    private Button deleteFileBtn;

    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_disssolve);

        selectedMethod = getIntent().getStringExtra("selectedDataDissolveMethod");
        Toast.makeText(this, getString(R.string.toast_selected_method) + selectedMethod, Toast.LENGTH_SHORT).show();
        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progressText);
        progressText.setText(R.string.inProgressText);
        successImage = findViewById(R.id.successImage);
        backBtn = findViewById(R.id.backButton);
        deleteFileBtn = findViewById(R.id.deleteFileButton);
        backBtn.setOnClickListener(v -> finish());

        deleteFileBtn.setOnClickListener(v -> deleteFile(uri));

        requestDocument();
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
            uri = data.getData();
            DissolveData(uri);
        }
    }

    private void DissolveData(Uri fileUri) {
        DataDissolveAsyncTask asyncTask = new DataDissolveAsyncTask(this, progressBar, fileUri, selectedMethod);
        asyncTask.execute();
    }

    private void DataDissolveDefault(Uri fileUri) {
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
            // Delete the file
//          deleteFile(fileUri);
            Toast.makeText(this, R.string.toast_dissolve_data_successfully, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void DissolveDataGutmann(Uri fileUri) {
        try {
            // Open the file for both reading and writing
            ParcelFileDescriptor pfd = getContentResolver().openFileDescriptor(fileUri, "rw");
            assert pfd != null;

            // Read the data from the file into a byte array
            FileInputStream fileInputStream = new FileInputStream(pfd.getFileDescriptor());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int bytesRead;
            byte[] buffer = new byte[1024];

            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }

            // Apply Gutmann method to the data
            byte[] data = byteArrayOutputStream.toByteArray();
            DataSanitization.wipeDataGutmann(data);

            // Write the modified data back to the file
            FileOutputStream fileOutputStream = new FileOutputStream(pfd.getFileDescriptor());
            fileOutputStream.write(data);

            // Close the streams and file descriptor
            fileInputStream.close();
            fileOutputStream.close();
            pfd.close();

            // Delete the file
//            DocumentFile documentFile = DocumentFile.fromSingleUri(this, fileUri);
//          deleteFile(fileUri);

            Toast.makeText(this, R.string.toast_dissolve_data_successfully, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, R.string.toast_dissolve_data_failed, Toast.LENGTH_SHORT).show();
        }
    }

    private void DissolveDataDoD(Uri fileUri) {
        try {
            // Open the file for both reading and writing
            ParcelFileDescriptor pfd = getContentResolver().openFileDescriptor(fileUri, "rw");
            assert pfd != null;

            // Read the data from the file into a byte array
            FileInputStream fileInputStream = new FileInputStream(pfd.getFileDescriptor());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int bytesRead;
            byte[] buffer = new byte[1024];

            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }

            // Apply DoD method to the data
            byte[] data = byteArrayOutputStream.toByteArray();
            DataSanitization.wipeDataDoD(data);

            // Write the modified data back to the file
            FileOutputStream fileOutputStream = new FileOutputStream(pfd.getFileDescriptor());
            fileOutputStream.write(data);

            // Close the streams and file descriptor
            fileInputStream.close();
            fileOutputStream.close();
            pfd.close();

            // Delete the file
//            deleteFile(fileUri);

            Toast.makeText(this, R.string.toast_dissolve_data_successfully, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, R.string.toast_dissolve_data_failed, Toast.LENGTH_SHORT).show();
        }
    }

    private void DissolveSchneier(Uri fileUri) {
        try {
            // Open the file for both reading and writing
            ParcelFileDescriptor pfd = getContentResolver().openFileDescriptor(fileUri, "rw");
            assert pfd != null;

            // Read the data from the file into a byte array
            FileInputStream fileInputStream = new FileInputStream(pfd.getFileDescriptor());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int bytesRead;
            byte[] buffer = new byte[1024];

            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }

            // Apply Schneier method to the data
            byte[] data = byteArrayOutputStream.toByteArray();
            DataSanitization.wipeDataSchneier(data);

            // Write the modified data back to the file
            FileOutputStream fileOutputStream = new FileOutputStream(pfd.getFileDescriptor());
            fileOutputStream.write(data);

            // Close the streams and file descriptor
            fileInputStream.close();
            fileOutputStream.close();
            pfd.close();

            // Delete the file
//            deleteFile(fileUri);

            Toast.makeText(this, R.string.toast_dissolve_data_successfully, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, R.string.toast_dissolve_data_failed, Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteFile(Uri fileUri) {
        documentFile = DocumentFile.fromSingleUri(this, fileUri);
        if (documentFile != null) {
            documentFile.delete();
        }
    }

    /**
     * This class is used to dissolve data in the background.
     */
    @SuppressLint("StaticFieldLeak")
    public class DataDissolveAsyncTask extends AsyncTask<Void, Integer, String> {
        private final Context context;
        private final ProgressBar progressBar;
        private final Uri uri;
        private final String additionalData;

        public DataDissolveAsyncTask(Context context, ProgressBar progressBar, Uri uri, String additionalData) {
            this.context = context;
            this.progressBar = progressBar;
            this.uri = uri;
            this.additionalData = additionalData;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Show the ProgressBar before starting the background task
            progressBar.setVisibility(android.view.View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result = null;
            if (uri != null) {
                try {
                    switch (selectedMethod) {
                        case "Default":
                            DataDissolveDefault(uri);
                            break;
                        case "Gutmann":
                            DissolveDataGutmann(uri);
                            break;
                        case "DoD":
                            DissolveDataDoD(uri);
                            break;
                        case "Schneier":
                            DissolveSchneier(uri);
                            break;
                        default:
                            DataDissolveDefault(uri);
                            break;
                    }
                    result = "Success";
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Hide the ProgressBar after the background task is complete
            progressBar.setVisibility(View.GONE);
            successImage.setVisibility(View.VISIBLE);
            backBtn.setVisibility(View.VISIBLE);
            if ("Success".equals(result)) {
                Toast.makeText(context, R.string.toast_success, Toast.LENGTH_SHORT).show();
                progressText.setText(R.string.textDisplaySuccess);
                successImage.setImageResource(R.drawable.ic_success);
            }
            else {
                Toast.makeText(context, R.string.toast_failed, Toast.LENGTH_SHORT).show();
                progressText.setText(R.string.textDisplayFailed);
                successImage.setImageResource(R.drawable.task_error);
            }
        }
    }
}