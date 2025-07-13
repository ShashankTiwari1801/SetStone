package com.belphegor.setstone.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.belphegor.setstone.R;
import com.belphegor.setstone.util.ResourceManager;

import java.util.ArrayList;
import java.util.List;

public class HeatmapCalendarColumn {

    final static int LAYOUT_ID = R.layout.calendar_column;

    final LayoutInflater layoutInflater;
    final int[] data;
    final LinearLayout parent;
    final ResourceManager resourceManager;

    LinearLayout LLCalendarColumn;

    final static int UNSELECTED_BACKGROUND = R.drawable.calendar_unselected;

    final static String[] MONTHS_STR = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
    String[] WEEK = {"M","T","W","T","F","S","S"};

    public HeatmapCalendarColumn(LayoutInflater layoutInflater, int[] data, LinearLayout parent, ResourceManager resourceManager) {

        this.layoutInflater = layoutInflater;
        this.data = data;
        this.parent = parent;
        this.resourceManager = resourceManager;

        init();
    }

    public void init(){

        LLCalendarColumn = (LinearLayout) layoutInflater.inflate(LAYOUT_ID, parent, false);

        List<TextView> cells = new ArrayList<>();

        for (int i = 0; i < LLCalendarColumn.getChildCount(); i++) {
            cells.add((TextView) LLCalendarColumn.getChildAt(i));
        }

        if(data[0] > 5){
            makeMonthColumn(cells); return;
        }

        for (int i = 0; i < data.length; i++) {
            if(data[i] == 1){
                cells.get(i).setBackground(resourceManager.getDrawableFromID(UNSELECTED_BACKGROUND));
            }
            if(data[i] == 0){
                cells.get(i).setBackground(null);
            }

        }

    }

    public void makeMonthColumn(List<TextView> cells){
        int MONTH = (data[0]/100) % 12;
        for (TextView tv : cells){
            tv.setBackground(null);
            LinearLayout.LayoutParams lllp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            tv.setLayoutParams(lllp);

        }
        cells.get(0).setText(MONTHS_STR[MONTH]);

    }

    public LinearLayout getWeekColumn(){
        LinearLayout weekColumn = (LinearLayout) layoutInflater.inflate(LAYOUT_ID, parent, false);

        List<TextView> cells = new ArrayList<>();

        for (int i = 0; i < weekColumn.getChildCount(); i++) {
            cells.add((TextView) weekColumn.getChildAt(i));
        }

        for (int i = 0; i < weekColumn.getChildCount(); i++) {
            cells.get(i).setBackground(null);
            cells.get(i).setText(WEEK[i]);

        }


        return weekColumn;
    }

    public LinearLayout getLLCalendarColumn() {
        return LLCalendarColumn;
    }
}
