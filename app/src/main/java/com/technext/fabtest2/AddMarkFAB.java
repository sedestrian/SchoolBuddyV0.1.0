package com.technext.fabtest2;

import android.view.View;
import android.widget.Toast;

/**
 * Created by Alessandro on 4/15/2015.
 */
public class AddMarkFAB extends GenericFAB {
    @Override
    public void onClick(View v) {
        Toast.makeText(context, "Don't touch me you perv!!", Toast.LENGTH_SHORT).show();
    }
}
