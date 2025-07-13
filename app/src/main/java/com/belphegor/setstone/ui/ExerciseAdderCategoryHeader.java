package com.belphegor.setstone.ui;

import android.widget.LinearLayout;

import com.belphegor.setstone.R;

public class ExerciseAdderCategoryHeader {

    final LinearLayout LLCategoryHeader;
    LinearLayout LLContainer;
    final static int idLLContainer = R.id.LL_EXERCISE_ADDER_CATEGORY_CONTAINER;

    public ExerciseAdderCategoryHeader(LinearLayout llCategoryHeader) {
        LLCategoryHeader = llCategoryHeader;

        init();
    }

    private void init() {

        LLContainer = LLCategoryHeader.findViewById(idLLContainer);

    }

    public void addExerciseAdderCategoryPill(ExerciseAdderCategoryPill exerciseAdderCategoryPill){
        LLContainer.addView(exerciseAdderCategoryPill.getRoot());
    }

    public LinearLayout getRoot(){
        return LLContainer;
    }

}
