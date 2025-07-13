package com.belphegor.setstone.ui_manager;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;

import com.belphegor.setstone.ExerciseRecorderActivity;
import com.belphegor.setstone.R;
import com.belphegor.setstone.animations.SlideAnimator;
import com.belphegor.setstone.database_manager.DatabaseManager;
import com.belphegor.setstone.ui.DailyExerciseViewer;
import com.belphegor.setstone.ui.DailyExerciseViewerCard;
import com.belphegor.setstone.util.ExerciseDirectoryManager;
import com.belphegor.setstone.util.MuscleIconManager;

import java.util.ArrayList;
import java.util.List;

public class DailyExerciseViewerManager {

    final DailyExerciseViewer dailyExerciseViewer;
    final DatabaseManager databaseManager;
    final ExerciseDirectoryManager exerciseDirectoryManager;
    final MuscleIconManager muscleIconManager;
    final LayoutInflater layoutInflater;

    int selectedDayID = 0;

    public DailyExerciseViewerManager(DailyExerciseViewer dailyExerciseViewer,
                                      DatabaseManager databaseManager,
                                      ExerciseDirectoryManager exerciseDirectoryManager,
                                      MuscleIconManager muscleIconManager,
                                      LayoutInflater layoutInflater){

        this.dailyExerciseViewer = dailyExerciseViewer;
        this.databaseManager = databaseManager;
        this.exerciseDirectoryManager = exerciseDirectoryManager;
        this.muscleIconManager = muscleIconManager;
        this.layoutInflater = layoutInflater;

        init();
    }

    int I = 0;
    public void init(){
        // no-op
    }

    public List<Integer> parseData(List<List<String>> exerciseForTheDay) {
        List<Integer> res = new ArrayList<>();

        for (List<String> list: exerciseForTheDay){
            for (String s: list){
                res.add(Integer.parseInt(s));
            }
        }

        return res;
    }

    public View.OnClickListener getOnCLickListener(final int exercise_id){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ExerciseRecorderActivity.class);
                intent.putExtra("EXERCISE_ID", exercise_id);
                view.getContext().startActivity(intent);

                ((Activity) view.getContext()).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            }
        };
    }

    public View.OnClickListener getOnRemoveClickListener(int exerciseID){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SlideAnimator.slide((View) view.getParent(), SlideAnimator.Direction.LEFT, 300, 200);
                new CountDownTimer(400,10){

                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        dailyExerciseViewer.getContainer().removeView((View) view.getParent());
                        databaseManager.removeExerciseForDay(selectedDayID, exerciseID);
                        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    }
                }.start();
            }
        };
    }

    public int getSelectedDayID(){
        return selectedDayID;
    }

    public void loadExerciseForDayID(int dayID){
        dailyExerciseViewer.removeAllViews();
        this.selectedDayID = dayID;
        List<List<String>> exerciseForTheDay = databaseManager.getExerciseOnDay(dayID);
        List<Integer> exerciseIds = parseData(exerciseForTheDay);

        for (int id: exerciseIds){
            String exerciseName = exerciseDirectoryManager.getExerciseName(id);
            String muscleName = exerciseDirectoryManager.getMuscleGroup(id);

            DailyExerciseViewerCard dailyExerciseViewerCard = new DailyExerciseViewerCard(
                    layoutInflater, dailyExerciseViewer.getContainer()
            );
            dailyExerciseViewerCard.setExerciseName(exerciseName);

            int muscleIcon = muscleIconManager.getMuscleIconFromName(muscleName);
            dailyExerciseViewerCard.setMuscleImage(muscleIcon);

            dailyExerciseViewerCard.setCardOnClickListener(getOnCLickListener(id));

            dailyExerciseViewerCard.setExitOnCardListener(getOnRemoveClickListener(id));

            dailyExerciseViewer.addExerciseViewerCard(dailyExerciseViewerCard);
        }

    }

}
