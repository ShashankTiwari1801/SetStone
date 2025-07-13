package com.belphegor.setstone.ui;

import android.widget.LinearLayout;

import com.belphegor.setstone.util.ResourceManager;

import java.util.ArrayList;
import java.util.List;

public class WeekRow {

    final LinearLayout LLWeekRow;
    final ResourceManager resourceManager;
    List<WeekDay> weekDays;

    public WeekRow(LinearLayout LLWeekRow, ResourceManager resourceManager){

        this.LLWeekRow = (LinearLayout) LLWeekRow.getChildAt(0);
        this.resourceManager = resourceManager;

        init();

    }
    public void init(){

        weekDays = new ArrayList<>();

        for (int i = 0; i < LLWeekRow.getChildCount(); i++) {
            WeekDay weekDay = new WeekDay((LinearLayout) LLWeekRow.getChildAt(i), resourceManager);
            weekDays.add(weekDay);
        }

    }

    public List<WeekDay> getWeekDays() {
        return weekDays;
    }
}
