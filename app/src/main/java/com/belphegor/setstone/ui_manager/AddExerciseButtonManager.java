package com.belphegor.setstone.ui_manager;

import android.content.Intent;
import android.view.View;

import com.belphegor.setstone.ExerciseAdderActivity;
import com.belphegor.setstone.ui.AddExerciseButton;

public class AddExerciseButtonManager {


    final AddExerciseButton addExerciseButton;
    final DailyExerciseViewerManager dailyExerciseViewerManager;

    public AddExerciseButtonManager(AddExerciseButton addExerciseButton, DailyExerciseViewerManager dailyExerciseViewerManager) {
        this.addExerciseButton = addExerciseButton;
        this.dailyExerciseViewerManager = dailyExerciseViewerManager;

        init();
    }

    public View.OnClickListener openExerciseSelectorActivity(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ExerciseAdderActivity.class);
                intent.putExtra("DAY_ID", dailyExerciseViewerManager.getSelectedDayID());
                view.getContext().startActivity(intent);
            }
        };
    }

    public void init(){
        addExerciseButton.setOnClickListener(openExerciseSelectorActivity());
    }


}
