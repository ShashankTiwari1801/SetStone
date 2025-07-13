package com.belphegor.setstone.util;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.belphegor.setstone.R;

public class ResourceManager {

    final Context context;

    public ResourceManager(Context context){

        this.context = context;

    }

    public int getColorValue(int resourceID){
        return context.getColor(resourceID);
    }

    public Drawable getDrawableFromID(int resourceID){
        return ContextCompat.getDrawable(context, resourceID);
    }

}

