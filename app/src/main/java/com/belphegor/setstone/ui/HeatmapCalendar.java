package com.belphegor.setstone.ui;

import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.belphegor.setstone.R;

public class HeatmapCalendar {

    final LinearLayout LLCalendar;
    LinearLayout LLContainer;
    HorizontalScrollView scroller;

    final int CALENDAR_DAYS_COUNT = 400;


    public HeatmapCalendar(LinearLayout llCalendar){

        this.LLCalendar = llCalendar;
        init();
    }

    private void init() {

        LLContainer = LLCalendar.findViewById(R.id.LL_CALENDAR_CONTAINER);
        scroller = (HorizontalScrollView) LLCalendar.getChildAt(0);
    }

    public LinearLayout getRoot() {
        return LLContainer;
    }

    public void addColumn(HeatmapCalendarColumn heatmapCalendarColumn){
        LLContainer.addView(heatmapCalendarColumn.getLLCalendarColumn());
        scroller.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
    }

    public void addWeekColumn(LinearLayout weekColumn){
        LLContainer.addView(weekColumn);
        scroller.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
    }


}
