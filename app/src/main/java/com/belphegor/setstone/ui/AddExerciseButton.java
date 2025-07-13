package com.belphegor.setstone.ui;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

public class AddExerciseButton {
    final LinearLayout LL_ADD_EXERCISE_BTN;

    public AddExerciseButton(LinearLayout llAddExerciseBtn) {
        LL_ADD_EXERCISE_BTN = llAddExerciseBtn;
    }

    public void setOnClickListener(View.OnClickListener onClickListener){
        LL_ADD_EXERCISE_BTN.setOnClickListener(onClickListener);
    }

    public Context getContext(){
        return LL_ADD_EXERCISE_BTN.getContext();
    }

}
