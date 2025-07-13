package com.belphegor.setstone.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.belphegor.setstone.R;
import com.belphegor.setstone.util.ResourceManager;

/*
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

    final static int SELECTED_BACKGROUND = R.drawable.exercise_adder_pill_selected_bakground;
    final static int UNSELECTED_BACKGROUND = R.drawable.exercise_adder_pill_background;

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

 */
public class ExerciseSelectorNameCard {


    final LinearLayout parent;
    final ResourceManager resourceManager;

    LinearLayout LLNameCard;
    final static int idLLNameCard = R.layout.exercise_adder_exercise_name_card;

    final static int SELECTED_BACKGROUND = R.drawable.background_exercise_adder_name_card_selected;
    final static int UNSELECTED_BACKGROUND = R.drawable.background_exercise_adder_name_card;

    final static int SELECTED_TEXT_COLOR = R.color.darker_background_color_main;
    final static int UNSELECTED_TEXT_COLOR = R.color.off_white_text;

    public boolean isSelected = false;

    public ExerciseSelectorNameCard(LinearLayout parent) {
        this.parent = parent;
        this.resourceManager = new ResourceManager(parent.getContext());
        init();
    }

    public void init(){

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        LLNameCard = (LinearLayout) layoutInflater.inflate(idLLNameCard, parent, false);

    }

    public void setOnClickListener(View.OnClickListener onClickListener){
        LLNameCard.setOnClickListener(onClickListener);
    }

    public void setExerciseName(String text){
        TextView tvExerciseName = (TextView) LLNameCard.getChildAt(0);
        tvExerciseName.setText(text);
    }

    public void setExerciseId(int id){
        TextView tvExerciseId = (TextView) LLNameCard.getChildAt(1);
        tvExerciseId.setText("#" + id);
    }


    public LinearLayout getRoot(){
        return LLNameCard;
    }

    public void select(){
        LLNameCard.setBackground(resourceManager.getDrawableFromID(SELECTED_BACKGROUND));
        ((TextView)LLNameCard.getChildAt(0)).setTextColor(resourceManager.getColorValue(SELECTED_TEXT_COLOR));
        isSelected = true;
    }
    public void deselect(){
        LLNameCard.setBackground(resourceManager.getDrawableFromID(UNSELECTED_BACKGROUND));
        ((TextView)LLNameCard.getChildAt(0)).setTextColor(resourceManager.getColorValue(UNSELECTED_TEXT_COLOR));
        isSelected = false;
    }

}
