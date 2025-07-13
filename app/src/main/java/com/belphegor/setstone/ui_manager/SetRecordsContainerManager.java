package com.belphegor.setstone.ui_manager;

import android.os.CountDownTimer;
import android.view.View;

import com.belphegor.setstone.Exercise;
import com.belphegor.setstone.animations.SlideAnimator;
import com.belphegor.setstone.database_manager.DatabaseManager;
import com.belphegor.setstone.ui.SetCard;
import com.belphegor.setstone.ui.SetRecordsContainer;
import com.belphegor.setstone.util.CaloriesCalculator;
import com.belphegor.setstone.util.ExerciseDirectoryManager;
import com.belphegor.setstone.util.JSONHelper;

import java.util.Collections;
import java.util.List;

public class SetRecordsContainerManager {

    final static int RECORD_HISTORY_COUNT = 50;

    final SetRecordsContainer setRecordsContainer;
    DatabaseManager databaseManager;
    final int EXERCISE_ID;
    List<Exercise> exerciseList;
    CaloriesCalculator caloriesCalculator;

    public SetRecordsContainerManager(SetRecordsContainer setRecordsContainer, int EXERCISE_ID){

        this.setRecordsContainer = setRecordsContainer;
        this.EXERCISE_ID = EXERCISE_ID;

        init();
    }

    public void reload(){

        setRecordsContainer.clear();
        exerciseList = databaseManager.getLastNSetDetailsForExercise(EXERCISE_ID, RECORD_HISTORY_COUNT);
        loadData();
    }

    public View.OnClickListener removeCard(int ROW_ID){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseManager.removeExerciseRecord(ROW_ID);

                SlideAnimator.slide((View) view.getParent(), SlideAnimator.Direction.LEFT, 300, 100);

                new CountDownTimer(200,10){

                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        setRecordsContainer.getRoot().removeView((View) view.getParent());
                        reload();
                    }
                }.start();


            }
        };
    }


    public void loadData(){
        Collections.reverse(exerciseList);
        String curr_date = "";
        if(!exerciseList.isEmpty()) {
            curr_date = exerciseList.get(0).getDate();
        }
        int set_id = 1;

        for (Exercise exercise : exerciseList){
            if(!curr_date.equals(exercise.getDate())){
                setRecordsContainer.addDateText(curr_date);
                curr_date = exercise.getDate();
                set_id = 1;
            }

            SetCard setCard = new SetCard(setRecordsContainer.getRoot());
            float calories = caloriesCalculator.getCalories(exercise.getExerciseId(), exercise.getReps());

            setCard.makeCard(set_id, exercise.getWeight(), exercise.getReps(), calories);

            setCard.setOnClickListener(removeCard(exercise.getRowID()));
            setRecordsContainer.addSetDetailsCard(setCard);

            set_id ++;
        }
        setRecordsContainer.addDateText(curr_date);

    }

    public void init(){

        databaseManager = new DatabaseManager(setRecordsContainer.getContext());
        caloriesCalculator = new CaloriesCalculator(databaseManager,
                new ExerciseDirectoryManager(new JSONHelper(setRecordsContainer.getContext())));

        reload();

    }


}
