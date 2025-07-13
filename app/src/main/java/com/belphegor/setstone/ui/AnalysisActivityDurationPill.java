package com.belphegor.setstone.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.belphegor.setstone.R;
import com.belphegor.setstone.util.ExerciseRecordForDuration;
import com.belphegor.setstone.util.ResourceManager;

public class AnalysisActivityDurationPill {

    Context context;
    final static int layout_id = R.layout.analysis_activity_duration_pill;
    View view;
    boolean isSelected = false;
    ResourceManager resourceManager;

    final static int SELECTED_BACKGROUND = R.drawable.background_exercise_adder_pill_selected;
    final static int UNSELECTED_BACKGROUND = R.drawable.background_exercise_adder_pill;

    final static int SELECTED_TEXT_COLOR = R.color.darker_background_color_main;
    final static int UNSELECTED_TEXT_COLOR = R.color.off_white_text;


    public AnalysisActivityDurationPill(ExerciseRecordForDuration.Duration duration, ViewGroup parent){
        resourceManager = new ResourceManager(parent.getContext());
        view = LayoutInflater.from(parent.getContext()).inflate(layout_id, parent, false);
        ((TextView) view).setText(duration.toString());
    }

    public View getRoot(){
        return view;
    }

    public void select(){
        ((TextView)view).setBackground(resourceManager.getDrawableFromID(SELECTED_BACKGROUND));
        ((TextView)view).setTextColor(resourceManager.getColorValue(SELECTED_TEXT_COLOR));
    }
    public void deselect(){
        ((TextView)view).setBackground(resourceManager.getDrawableFromID(UNSELECTED_BACKGROUND));
        ((TextView)view).setTextColor(resourceManager.getColorValue(UNSELECTED_TEXT_COLOR));
    }

    public void setOnClickListener(View.OnClickListener onClickListener){
        view.setOnClickListener(onClickListener);
    }

}
