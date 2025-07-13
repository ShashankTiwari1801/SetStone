package com.belphegor.setstone.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.belphegor.setstone.R;
import com.belphegor.setstone.util.ResourceManager;

public class ExerciseAdderCategoryPill {

    final LinearLayout parent;
    TextView TVPill;
    final static int idTVPill = R.layout.exercise_adder_category_pill;

    final static int SELECTED_BACKGROUND = R.drawable.background_exercise_adder_pill_selected;
    final static int UNSELECTED_BACKGROUND = R.drawable.background_exercise_adder_pill;

    final static int SELECTED_TEXT_COLOR = R.color.darker_background_color_main;
    final static int UNSELECTED_TEXT_COLOR = R.color.off_white_text;

    final ResourceManager resourceManager;

    public ExerciseAdderCategoryPill(LinearLayout parent){

        this.parent = parent;
        resourceManager = new ResourceManager(parent.getContext());

        init();

    }

    public void init(){

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        TVPill = (TextView) layoutInflater.inflate(idTVPill, parent, false);

    }

    public TextView getRoot(){
        return TVPill;
    }

    public void setText(String text){
        TVPill.setText(text);
    }

    public void setOnClickListener(View.OnClickListener onClickListener){
        TVPill.setOnClickListener(onClickListener);
    }

    public void select(){
        TVPill.setBackground(resourceManager.getDrawableFromID(SELECTED_BACKGROUND));
        TVPill.setTextColor(resourceManager.getColorValue(SELECTED_TEXT_COLOR));
    }
    public void deselect(){
        TVPill.setBackground(resourceManager.getDrawableFromID(UNSELECTED_BACKGROUND));
        TVPill.setTextColor(resourceManager.getColorValue(UNSELECTED_TEXT_COLOR));
    }

}
