package com.datadissolve.ui;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.datadissolve.R;

public class AlertFragment extends DialogFragment {
    private static final String PARAMETER_KEY = "warningParameter";

    public static AlertFragment newInstance(String warningType) {
        AlertFragment fragment = new AlertFragment();
        Bundle args = new Bundle();
        args.putString(PARAMETER_KEY, warningType);
        fragment.setArguments(args);
        return fragment;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        assert getArguments() != null;
        String warningType = getArguments().getString("warningParameter", "patterns");


        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                builder.setTitle(R.string.AlertTitle)
                .setNeutralButton(R.string.continueButtonText, (dialog, which) -> dialog.dismiss());

        if(warningType.equals("patterns")) {
            builder.setMessage(R.string.numPatternsAlert);
        }
        else {
            builder.setMessage(R.string.numBitsAlert);
        }


        return builder.create();
    }
}