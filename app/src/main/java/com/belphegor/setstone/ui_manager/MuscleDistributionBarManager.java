package com.belphegor.setstone.ui_manager;

import android.os.Looper;
import android.util.Log;

import com.belphegor.setstone.database_manager.DatabaseManager;
import com.belphegor.setstone.ui.MuscleDistributionBar;
import com.belphegor.setstone.util.DateManager;
import com.belphegor.setstone.util.ExerciseDirectoryManager;
import com.belphegor.setstone.util.ExerciseRecordForDuration;
import com.belphegor.setstone.util.Logger;
import com.belphegor.setstone.util.MuscleIconManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MuscleDistributionBarManager {

    final MuscleDistributionBar muscleDistributionBar;
    final DatabaseManager databaseManager;
    final DateManager dateManager;
    final ExerciseDirectoryManager exerciseDirectoryManager;
    final MuscleIconManager muscleIconManager;
    List<List<String>> rawWeekData;
    HashMap<String, Integer> muscleFrequencyMap;
    List<String> muscleGroupSortedList;
    int[] frequencyArr;


    public MuscleDistributionBarManager(MuscleDistributionBar muscleDistributionBar, DatabaseManager databaseManager,
                                        DateManager dateManager, ExerciseDirectoryManager exerciseDirectoryManager,
                                        MuscleIconManager muscleIconManager) {

        this.databaseManager = databaseManager;
        this.muscleDistributionBar = muscleDistributionBar;
        this.dateManager = dateManager;
        this.exerciseDirectoryManager = exerciseDirectoryManager;
        this.muscleIconManager = muscleIconManager;

        init();
    }

    public void reloadData(ExerciseRecordForDuration.Duration duration){

        rawWeekData = ExerciseRecordForDuration.getRecordForDuration(databaseManager, duration);

        parseData();

    }

    public void parseData(){

        muscleFrequencyMap = new HashMap<>();

        for (List<String> record: rawWeekData){
            int exerciseId = Integer.parseInt(record.get(2));
            String muscleGroup = exerciseDirectoryManager.getMuscleGroup(exerciseId);

            muscleFrequencyMap.put(muscleGroup, muscleFrequencyMap.getOrDefault(muscleGroup, 0) + 1);
        }

        Logger.iLog(muscleFrequencyMap.toString());

        frequencyArr = new int[muscleIconManager.getAllMuscleSet().size()];

        for(String key: muscleFrequencyMap.keySet()){
            int muscleId = muscleGroupSortedList.indexOf(key);
            frequencyArr[muscleId] = muscleFrequencyMap.get(key);
        }

        Logger.dLog("FREQ ARR + " + Arrays.toString(frequencyArr));

        muscleDistributionBar.updateData(frequencyArr);
    }

    private void init() {
        rawWeekData = new ArrayList<>();
        muscleFrequencyMap = new HashMap<>();
        frequencyArr = new int[muscleIconManager.getAllMuscleSet().size()];

        muscleGroupSortedList = new ArrayList<>(muscleIconManager.getAllMuscleSet());
        Collections.sort(muscleGroupSortedList);

        Logger.eLog(muscleGroupSortedList.toString());

        reloadData(ExerciseRecordForDuration.Duration.ETERNITY);

        muscleDistributionBar.updateData(frequencyArr);
    }


}
