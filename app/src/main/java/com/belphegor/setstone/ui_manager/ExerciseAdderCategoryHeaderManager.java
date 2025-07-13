package com.belphegor.setstone.ui_manager;

import android.view.View;
import android.widget.LinearLayout;

import com.belphegor.setstone.database_manager.DatabaseManager;
import com.belphegor.setstone.ui.ExerciseAdderCategoryHeader;
import com.belphegor.setstone.ui.ExerciseAdderCategoryPill;
import com.belphegor.setstone.ui.ExerciseSelectorNameCard;
import com.belphegor.setstone.util.ExerciseDirectoryManager;
import com.belphegor.setstone.util.JSONHelper;
import com.belphegor.setstone.util.MuscleIconManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExerciseAdderCategoryHeaderManager {

    final ExerciseAdderCategoryHeader exerciseAdderCategoryHeader;
    final LinearLayout LLExerciseContainer;
    MuscleIconManager muscleIconManager;
    List<ExerciseAdderCategoryPill> exerciseAdderCategoryPillList;

    Set<Integer> selectedExercises;
    final int day_id;
    int selected = 0;

    public ExerciseAdderCategoryHeaderManager(ExerciseAdderCategoryHeader exerciseAdderCategoryHeader, LinearLayout llExerciseContainer, int day_id) {
        this.exerciseAdderCategoryHeader = exerciseAdderCategoryHeader;
        LLExerciseContainer = llExerciseContainer;
        this.selectedExercises = new HashSet<>();

        this.day_id = day_id;

        init();

    }

    public View.OnClickListener onSelectedPillAction(int id, String muscle){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                exerciseAdderCategoryPillList.get(selected).deselect();

                selected = id;

                exerciseAdderCategoryPillList.get(selected).select();

                //LOAD EXERCISES FOR GIVEN MUSCLE
                loadExercise(muscle);

            }
        };
    }


    public View.OnClickListener nameCardClickAction(ExerciseSelectorNameCard exerciseSelectorNameCard, int exercise_id){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(exerciseSelectorNameCard.isSelected){
                    exerciseSelectorNameCard.deselect();
                    selectedExercises.remove(exercise_id);
                }
                else{
                    exerciseSelectorNameCard.select();
                    selectedExercises.add(exercise_id);
                }

            }
        };
    }

    public void loadExercise(String muscle){

        while (LLExerciseContainer.getChildCount() > 1){
            LLExerciseContainer.removeViewAt(0);
        }

        ExerciseDirectoryManager exerciseDirectoryManager = new ExerciseDirectoryManager(
                new JSONHelper(exerciseAdderCategoryHeader.getRoot().getContext())
        );

        List<Integer> ids = exerciseDirectoryManager.getAllExerciseForMuscle(muscle);

        for(int id: ids){

//            TextView temp = new TextView(exerciseAdderCategoryHeader.getRoot().getContext());
//            temp.setText(String.valueOf(id) + " | " + exerciseDirectoryManager.getExerciseName(id));
//            LLExerciseContainer.addView(temp);

            ExerciseSelectorNameCard exerciseSelectorNameCard = new ExerciseSelectorNameCard(LLExerciseContainer);
            exerciseSelectorNameCard.setExerciseId(id);
            exerciseSelectorNameCard.setExerciseName(exerciseDirectoryManager.getExerciseName(id));

            exerciseSelectorNameCard.setOnClickListener(nameCardClickAction(exerciseSelectorNameCard, id));

            if(selectedExercises.contains(id)){
                exerciseSelectorNameCard.select();
            }

            LLExerciseContainer.addView(exerciseSelectorNameCard.getRoot(), 0);

        }
    }

    public void init(){

        exerciseAdderCategoryPillList = new ArrayList<>();

        selectedExercises = new HashSet<>(new DatabaseManager(exerciseAdderCategoryHeader.getRoot().getContext()).getExerciseListForDay(day_id));

        muscleIconManager = new MuscleIconManager(exerciseAdderCategoryHeader.getRoot().getContext());

        exerciseAdderCategoryHeader.getRoot().removeAllViews();

        List<String> muscleList = new ArrayList<>(muscleIconManager.getAllMuscleSet());
        Collections.sort(muscleList);

        int i = 0;
        for (String muscle: muscleList){

            ExerciseAdderCategoryPill exerciseAdderCategoryPill = new ExerciseAdderCategoryPill(exerciseAdderCategoryHeader.getRoot());
            exerciseAdderCategoryPill.setText(muscle);
            exerciseAdderCategoryPill.setOnClickListener(onSelectedPillAction(i, muscle));
            exerciseAdderCategoryHeader.addExerciseAdderCategoryPill(exerciseAdderCategoryPill);
            exerciseAdderCategoryPillList.add(exerciseAdderCategoryPill);

            i++;

        }

        exerciseAdderCategoryPillList.get(selected).select();
        loadExercise(muscleList.get(selected));

    }

    public Set<Integer> getSelectedExercises(){
        return selectedExercises;
    }

}
