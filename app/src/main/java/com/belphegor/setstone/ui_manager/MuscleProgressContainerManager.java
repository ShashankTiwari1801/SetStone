package com.belphegor.setstone.ui_manager;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.belphegor.setstone.R;
import com.belphegor.setstone.database_manager.DatabaseManager;
import com.belphegor.setstone.ui.MuscleProgress;
import com.belphegor.setstone.util.DateManager;
import com.belphegor.setstone.util.ExerciseDirectoryManager;
import com.belphegor.setstone.util.ExerciseRecordForDuration;
import com.belphegor.setstone.util.JSONHelper;
import com.belphegor.setstone.util.Logger;
import com.belphegor.setstone.util.MuscleIconManager;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MuscleProgressContainerManager {

    final LinearLayout muscleProgressContainer;
    final DatabaseManager databaseManager;
    final DateManager dateManager;
    final ExerciseDirectoryManager exerciseDirectoryManager;
    final Context context;
    List<List<String>> rawWeekData;
    HashMap<String, Integer> muscleFrequencyMap;
    List<String> muscleGroupSortedList;
    float[] frequencyArr;
    List<String> muscles;

    public enum DurationRange{
        WEEK, MONTH, YEAR, ETERNITY
    }

    public MuscleProgressContainerManager(LinearLayout muscleProgressContainer, DatabaseManager databaseManager) {
        this.muscleProgressContainer = muscleProgressContainer;
        this.databaseManager = databaseManager;
        this.dateManager = new DateManager();
        this.exerciseDirectoryManager = new ExerciseDirectoryManager(new JSONHelper(muscleProgressContainer.getContext()));
        this.context = muscleProgressContainer.getContext();

        rawWeekData = new ArrayList<>();
        muscleFrequencyMap = new HashMap<>();
        muscleGroupSortedList = new ArrayList<>();

        muscles = exerciseDirectoryManager.getMuscleList();
        Collections.sort(muscles);

        init();
    }

    public void loadDataFor(DurationRange duration){

        LocalDate first = LocalDate.now(), last = LocalDate.now();

        switch (duration){
            case WEEK:
                first = dateManager.getFirstDateOfTheWeek();
                last = dateManager.getLastDateOfTheWeek();
                break;
            case MONTH:
                first = dateManager.getFirstDayOfTheMonth();
                last = dateManager.getLastDayOfTheMonth();
                break;

            case YEAR:
                first = dateManager.getFirstDayOfTheYear();
                last = dateManager.getLastDayOfTheYear();
                break;

            case ETERNITY:
                first = dateManager.getFirstDayOfEternity();
                last = dateManager.getLastDayOfEternity();
                break;

            default:
                break;
        }

        rawWeekData = databaseManager.getRecordBetween(
                first.toString() , last.toString()
        );


    }

    public void reloadData(ExerciseRecordForDuration.Duration duration){


        List<String> muscles = exerciseDirectoryManager.getMuscleList();
        Collections.sort(muscles);

        rawWeekData = ExerciseRecordForDuration.getRecordForDuration(databaseManager, duration);

        frequencyArr = new float[muscles.size()];

        int totalSets = rawWeekData.size();

        for(List<String> record: rawWeekData) {
            String muscle = exerciseDirectoryManager.getMuscleGroup(
                    Integer.parseInt(record.get(2))
            );
            frequencyArr[muscles.indexOf(muscle)]++;
        }

        for (int i = 0; i < frequencyArr.length; i++) {
            frequencyArr[i] /= (float)totalSets;
        }

        muscleProgressContainer.removeAllViews();
        for (String m : muscles) {
            muscleProgressContainer.addView(
                    MuscleProgress.getMuscleProgressBar(muscleProgressContainer.getContext() , muscleProgressContainer, m, frequencyArr[muscles.indexOf(m)])
            );
        }
    }


    private void init() {
        muscleProgressContainer.removeAllViews();

        reloadData(ExerciseRecordForDuration.Duration.ETERNITY);


    }

}
