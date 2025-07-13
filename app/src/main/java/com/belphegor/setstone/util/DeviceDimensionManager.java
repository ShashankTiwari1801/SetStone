package com.belphegor.setstone.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.util.Stack;

public class DeviceDimensionManager {

    public static DisplayMetrics getDisplayMetrics(Context context){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        return displayMetrics;
    }

    public static int getDeviceWidth(Context context){
        DisplayMetrics displayMetrics = getDisplayMetrics(context);
        return displayMetrics.widthPixels;
    }

    public static int getDeviceHeight(Context context){
        DisplayMetrics displayMetrics = getDisplayMetrics(context);
        return displayMetrics.heightPixels;
    }

}
