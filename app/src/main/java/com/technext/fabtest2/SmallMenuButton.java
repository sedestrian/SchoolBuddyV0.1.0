package com.technext.fabtest2;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

public class SmallMenuButton extends android.support.v4.app.Fragment implements View.OnClickListener{

    AppCompatActivity activity;
    Context context;
    String name;
    int drawableIcon;
    int color;

    public SmallMenuButton() {
        // Required empty public constructor
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDrawableIcon(int drawableIcon) {
        this.drawableIcon = drawableIcon;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.small_menu_button, container, false);
        FloatingActionButton btn = (FloatingActionButton) view.findViewById(R.id.miniCommand);
        btn.setImageResource(drawableIcon);
        btn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.miniCommand): {
                if(name != null)
                    Toast.makeText(context, name, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
