package com.technext.fabtest2;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by Alessandro on 4/15/2015.
 */
public class AddSubjFab {

    /*Context context;
    AppCompatActivity activity;

    public AddSubjFab(Context context) {
        super(context);
        this.context = context;
        activity = (AppCompatActivity) context;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case (R.id.runCommand):{
                MaterialDialog dialog = new MaterialDialog.Builder(context)
                        .title("Add a new subject")
                        .customView(R.layout.new_subject, false)
                        .positiveText("Submit")
                        .negativeText("Cancel")
                        .positiveColorRes(R.color.primaryColor)
                        .negativeColorRes(R.color.primaryColor)
                        .neutralColor(Color.WHITE)
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                super.onPositive(dialog);
                                DatabaseAdapter databaseAdapter = new DatabaseAdapter(context);
                                databaseAdapter.insertSubject(((EditText) dialog.getCustomView().findViewById(R.id.editText)).getText().toString());
                                RecyclerView list = (RecyclerView) activity.findViewById(R.id.markRecycler);
                                TextView textView = (TextView) activity.findViewById(R.id.empty);
                                list.setVisibility(View.VISIBLE);
                                textView.setVisibility(View.GONE);
                                ((MarksRecyclerAdapter) list.getAdapter()).updateData();
                            }

                            @Override
                            public void onNegative(MaterialDialog dialog) {
                                super.onNegative(dialog);
                            }
                        })
                        .autoDismiss(true)
                        .show();
                View customView = dialog.getCustomView();
                EditText editText = (EditText) customView.findViewById(R.id.editText);
                final TextView label = (TextView) customView.findViewById(R.id.textView);
                final int base = label.getCurrentTextColor();
                editText.setOnFocusChangeListener(
                        new View.OnFocusChangeListener() {
                            @Override
                            public void onFocusChange(View v, boolean hasFocus) {
                                if(!hasFocus == true){
                                    label.setTextColor(getResources().getColor(R.color.primaryColor));
                                }
                                else{
                                    //label.setTextColor(Color.BLACK);
                                }
                            }
                        }
                );
            }
            default:{
                System.out.println("Not the right button");
                break;
            }
        }
    }*/
}
