package com.datadissolve.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.documentfile.provider.DocumentFile;

import com.datadissolve.R;
import com.datadissolve.util.DataSanitization;
import com.google.android.material.slider.Slider;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.Random;

/**
 * This activity is used to pick a document and dissolve.
 * @noinspection ALL
 */
public class DataDissolveActivity extends AppCompatActivity {
    private static final int PICK_FILE_REQUEST = 1;
    private String selectedMethod;
    private ProgressBar progressBar;
    private TextView progressText;
    private ImageView successImage;
    private TextView backBtn;
    private DocumentFile documentFile;
    private CheckBox deleteFileBtn;
    private Integer customNumPatterns;
    private Integer customNumBits;


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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final boolean[] patternWarningShown = {false};
        final boolean[] bitsWarningShown = {false};

        Slider numPatternSlider = findViewById(R.id.numPatternSlider);
        Slider numBitsSlider = findViewById(R.id.numBitsSlider);

        // Enable the Up button
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(deleteFileBtn.isChecked()) {
                    deleteFile(uri);
                }
                finish();
            }
        });
        requestDocument();
        DissolveData(uri);
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
        new DataDissolveAsyncTask(this, progressBar, fileUri, selectedMethod).execute();
    }

    private void DissolveDefault(Uri fileUri) {
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void DissolveGutmann(DataDissolveAsyncTask task, Uri fileUri) {
        try {
            InputStream inputStream = task.context.getContentResolver().openInputStream(fileUri);
            byte[] data = inputStream.readAllBytes();
            inputStream.close();

            DataSanitization.wipeDataGutmann(data);

            OutputStream outputStream = task.context.getContentResolver().openOutputStream(fileUri);
            outputStream.write(data);
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void DissolveDoD(DataDissolveAsyncTask task, Uri fileUri) {
        try {
            InputStream inputStream = task.context.getContentResolver().openInputStream(fileUri);
            byte[] data = inputStream.readAllBytes();
            inputStream.close();

            DataSanitization.wipeDataDoD(data);

            OutputStream outputStream = task.context.getContentResolver().openOutputStream(fileUri);
            outputStream.write(data);
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void DissolveSchneier(DataDissolveAsyncTask task, Uri fileUri) {
        try {
            InputStream inputStream = task.context.getContentResolver().openInputStream(fileUri);
            byte[] data = inputStream.readAllBytes();
            inputStream.close();

            DataSanitization.wipeDataSchneier(data);

            OutputStream outputStream = task.context.getContentResolver().openOutputStream(fileUri);
            outputStream.write(data);
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void DissolveCustom(DataDissolveAsyncTask task, Uri fileUri){
        try {
            InputStream inputStream = task.context.getContentResolver().openInputStream(fileUri);
            byte[] data = inputStream.readAllBytes();
            inputStream.close();

            DataSanitization.wipeDataCustom(data, customNumPatterns, customNumBits);

            OutputStream outputStream = task.context.getContentResolver().openOutputStream(fileUri);
            outputStream.write(data);
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
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
                        case "Gutmann":
                            DissolveGutmann(this, uri);
                            break;
                        case "DoD":
                            DissolveDoD(this, uri);
                            break;
                        case "Schneier":
                            DissolveSchneier(this, uri);
                            break;
                        case "Custom":
                            DissolveCustom(this, uri);
                            break;
                        default:
                            DissolveDefault(uri);
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
                runOnUiThread(() -> {
                    Toast.makeText(context, R.string.toast_success, Toast.LENGTH_SHORT).show();
                    progressText.setText(R.string.textDisplaySuccess);
                    successImage.setImageResource(R.drawable.ic_success);
                });
            } else {
                runOnUiThread(() -> {
                    Toast.makeText(context, R.string.toast_failed, Toast.LENGTH_SHORT).show();
                    progressText.setText(R.string.textDisplayFailed);
                    successImage.setImageResource(R.drawable.task_error);
                });
            }
        }
    }
}