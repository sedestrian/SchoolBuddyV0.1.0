package com.technext.fabtest2;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public abstract class GenericFAB extends android.support.v4.app.Fragment implements View.OnClickListener{

    private static final String animText = "param1";
    int rotation = 225, time = 600;
    boolean animateButtonRotation = false;

    public GenericFAB() {
        // Required empty public constructor
    }

    AppCompatActivity context;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = (AppCompatActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fab, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        ImageButton imgB = (ImageButton) getView().findViewById(R.id.runCommand);
        imgB.setOnClickListener(this);
        super.onActivityCreated(savedInstanceState);
    }

    public abstract void onClick(View v);

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
