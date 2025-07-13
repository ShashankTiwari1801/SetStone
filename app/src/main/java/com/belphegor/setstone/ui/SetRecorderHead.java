package com.belphegor.setstone.ui;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.belphegor.setstone.R;
import com.belphegor.setstone.util.ExerciseDirectoryManager;
import com.belphegor.setstone.util.MuscleIconManager;

public class SetRecorderHead {

    final ImageView IV_MUSCLE_ICON;
    final TextView TV_EXERCISE_NAME;
    final ExerciseDirectoryManager exerciseDirectoryManager;
    final MuscleIconManager muscleIconManager;

    public SetRecorderHead(LinearLayout LLSetRecorderHeader,
                           ExerciseDirectoryManager exerciseDirectoryManager,
                           MuscleIconManager muscleIconManager){

        IV_MUSCLE_ICON = LLSetRecorderHeader.findViewById(R.id.IV_SET_RECORDER_HEADER_MUSCLE_ICON);
        TV_EXERCISE_NAME = LLSetRecorderHeader.findViewById(R.id.IV_SET_RECORDER_HEADER_MUSCLE_NAME);
        this.exerciseDirectoryManager = exerciseDirectoryManager;
        this.muscleIconManager = muscleIconManager;

    }

    public void load(int exercise_id){
        String exercise_name = exerciseDirectoryManager.getExerciseName(exercise_id);
        String muscle_name = exerciseDirectoryManager.getMuscleGroup(exercise_id);

        setIcon(muscleIconManager.getMuscleIconFromName(muscle_name));
        setExercise(exercise_name);
    }

    public void setIcon(int image){
        IV_MUSCLE_ICON.setImageResource(image);
    }
    public void setExercise(String name){
        TV_EXERCISE_NAME.setText(name);
    }
}
