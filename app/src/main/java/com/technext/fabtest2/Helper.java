package com.technext.fabtest2;

import android.app.Activity;
import android.content.Context;

/**
 * Created by gaboardi.12392 on 27/01/2015.
 */
public class Helper{

    static Context context;

    public Helper(Context context){
        this.context = context;
    }

    public static int getPx(Context context, int dp){
        float scale = context.getResources().getDisplayMetrics().density;
        return((int) Math.round(dp*scale));
    }
}
