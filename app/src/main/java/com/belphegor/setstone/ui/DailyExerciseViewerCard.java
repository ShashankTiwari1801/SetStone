package com.belphegor.setstone.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.belphegor.setstone.R;

public class DailyExerciseViewerCard {

    private static  final int LAYOUT_ID = R.layout.daily_exercise_viewer_card;
    private static  final int MUSCLE_ICON_ID = R.id.IV_EXERCISE_VIEWER_CARD_MUSCLE_ICON;
    private static  final int REMOVE_ICON_ID = R.id.IV_EXERCISE_VIEWER_CARD_REMOVE_BTN;
    private static  final int NAME_TXT_ID = R.id.TV_EXERCISE_VIEWER_CARD_EXERCISE_NAME;


    private LinearLayout parent;

    private GridLayout GLExerciseViewerCard;

    private ImageView IVMuscleIcon, IVRemoveBtn;
    private TextView TVExerciseName;

    private final LayoutInflater layoutInflater;

    public DailyExerciseViewerCard(LayoutInflater layoutInflater, LinearLayout parent){
        this.layoutInflater = layoutInflater;
        this.parent = parent;

        init();
    }

    public void inflateView(){

        GLExerciseViewerCard = (GridLayout) layoutInflater.inflate(LAYOUT_ID, parent, false);

    }

    public void init(){

        inflateView();

        IVMuscleIcon = GLExerciseViewerCard.findViewById(MUSCLE_ICON_ID);
        IVRemoveBtn = GLExerciseViewerCard.findViewById(REMOVE_ICON_ID);
        TVExerciseName = GLExerciseViewerCard.findViewById(NAME_TXT_ID);

    }

    public void setMuscleImage(int imageResourceID) {
        IVMuscleIcon.setImageResource(imageResourceID);
    }

    public void setExerciseName(String exerciseName){
        TVExerciseName.setText(exerciseName);
    }

    public void setCardOnClickListener(View.OnClickListener listener) {
        GLExerciseViewerCard.setOnClickListener(listener);
    }

    public void setExitOnCardListener(View.OnClickListener listener) {
        IVRemoveBtn.setOnClickListener(listener);
    }

    public GridLayout getRoot(){
        return GLExerciseViewerCard;
    }

}
