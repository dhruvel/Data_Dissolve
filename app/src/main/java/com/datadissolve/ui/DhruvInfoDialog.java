package com.datadissolve.ui;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;

import com.datadissolve.R;

/**
 * This class represents a custom dialog that displays information about the app.
 */
class DhruvInfoDialog extends Dialog {
    public DhruvInfoDialog(Context context) {
        super(context);
        setContentView(R.layout.dhruv_info_dialog);
        Button closeButton = findViewById(R.id.info_dialog_close_button);
        closeButton.setOnClickListener(v -> {
            dismiss(); // Close the dialog when the close button is clicked
        });
    }
}