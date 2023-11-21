package com.datadissolve.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.datadissolve.R;

public class open_app_guide extends Fragment {

    public open_app_guide() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_open_app_guide, container, false);

        Button questionMarkButton = view.findViewById(R.id.questionMarkButton);
        questionMarkButton.setOnClickListener(v -> showAppGuide());

        return view;
    }

    public void showAppGuide() {
        Intent intent = new Intent(getActivity(), AppGuide.class);
        startActivity(intent);
    }
}