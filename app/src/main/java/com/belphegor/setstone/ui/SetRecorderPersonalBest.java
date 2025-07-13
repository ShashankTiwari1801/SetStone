package com.belphegor.setstone.ui;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.belphegor.setstone.R;

public class SetRecorderPersonalBest {

    final TextView TV_SET_RECORDER_PERSONAL_RECORD_TEXT;

    public SetRecorderPersonalBest(LinearLayout LLPersonalBest){
        TV_SET_RECORDER_PERSONAL_RECORD_TEXT = LLPersonalBest.findViewById(R.id.TV_SET_RECORDER_PERSONAL_RECORD_TEXT);
    }

    public void setPersonalBest(float personalBest){

        String toDisplay = String.format("%.2f Kg", personalBest);

        TV_SET_RECORDER_PERSONAL_RECORD_TEXT.setText(toDisplay);

    }
}
