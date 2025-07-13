package com.belphegor.setstone.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.belphegor.setstone.R;

public class SetRecordsContainer {

    final LinearLayout LL_SET_RECORDS_CONTAINER;
    final int DATE_LAYOUT_ID = R.layout.date_display;
    final LayoutInflater layoutInflater;

    public SetRecordsContainer(LinearLayout llSetRecordsContainer){

        LL_SET_RECORDS_CONTAINER = llSetRecordsContainer;
        layoutInflater = LayoutInflater.from(LL_SET_RECORDS_CONTAINER.getContext());

    }
    public void addSetDetailsCard(SetCard setCard){
        LL_SET_RECORDS_CONTAINER.addView(setCard.getRoot(), 0);
    }

    public void addDateText(String date){
        TextView dateView = (TextView) layoutInflater.inflate(DATE_LAYOUT_ID,LL_SET_RECORDS_CONTAINER, false);
        dateView.setText(date);
        LL_SET_RECORDS_CONTAINER.addView(dateView, 0);
    }

    public void clear(){
        while (LL_SET_RECORDS_CONTAINER.getChildCount() > 1){
            LL_SET_RECORDS_CONTAINER.removeViewAt(0);
        }
    }

    public LinearLayout getRoot(){
        return LL_SET_RECORDS_CONTAINER;
    }

    public Context getContext(){
        return LL_SET_RECORDS_CONTAINER.getContext();
    }
}
