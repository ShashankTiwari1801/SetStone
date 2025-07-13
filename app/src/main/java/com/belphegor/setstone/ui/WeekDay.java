package com.belphegor.setstone.ui;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.belphegor.setstone.R;
import com.belphegor.setstone.util.ResourceManager;

public class WeekDay {

    final LinearLayout LLWeekDay;
    final TextView TVDayName;
    final TextView TVDayDate;

    final static int defaultTextColor = R.color.off_white_text;
    final static int selectedTextColor = R.color.background_color_main;
    final static int selectedBackgroundColor = R.color.off_white_text;
    final static int transparent = R.color.transparent;


    final ResourceManager resourceManager;

    public WeekDay(LinearLayout LLWeekDay, ResourceManager resourceManager){

        this.LLWeekDay = LLWeekDay;
        TVDayName = (TextView) LLWeekDay.getChildAt(0);
        TVDayDate = (TextView) LLWeekDay.getChildAt(1);
        this.resourceManager = resourceManager;

        init();
    }

    public void init(){

    }

    public void setDayName(String dayName){
        TVDayName.setText(dayName);
    }

    public void setDayDate(String dayDate){
        TVDayDate.setText(dayDate);
    }

    public void highlight(){
        TVDayDate.setBackgroundColor(resourceManager.getColorValue(selectedBackgroundColor));
        TVDayDate.setTextColor(resourceManager.getColorValue(selectedTextColor));
    }

    public void select(){
        TVDayName.setBackgroundColor(resourceManager.getColorValue(selectedBackgroundColor));
        TVDayName.setTextColor(resourceManager.getColorValue(selectedTextColor));
    }

    public void unselect(){
        TVDayName.setBackgroundColor(resourceManager.getColorValue(transparent));
        TVDayName.setTextColor(resourceManager.getColorValue(defaultTextColor));
    }

    public void setOnClickListener(View.OnClickListener onClickListener){
        LLWeekDay.setOnClickListener(onClickListener);
    }

}
