package com.belphegor.setstone.util;

import com.belphegor.setstone.database_manager.DatabaseManager;

import java.util.HashMap;

public class CaloriesCalculator {

    HashMap<String, Float> METMap;
    final static float OxygenConsumptionRate = 3.5f;
    final static int SECONDS_FOR_TEN_REPS = 35;
    final DatabaseManager databaseManager;
    final ExerciseDirectoryManager exerciseDirectoryManager;

    public CaloriesCalculator(DatabaseManager databaseManager, ExerciseDirectoryManager exerciseDirectoryManager) {


        METMap = new HashMap<>();
        this.databaseManager = databaseManager;
        this.exerciseDirectoryManager = exerciseDirectoryManager;

        init();
    }

    public void init() {
        METMap.put("Chest", 3.5f);
        METMap.put("Shoulder", 4.0f);
        METMap.put("Biceps", 3.5f);
        METMap.put("Triceps", 3.5f);
        METMap.put("Back", 4.0f);
        METMap.put("Glutes", 4.0f);
        METMap.put("Abs", 3.8f);
        METMap.put("Calves", 3.8f);
        METMap.put("Forearms", 3.5f);
        METMap.put("Hamstrings", 4.5f);
        METMap.put("Quads", 4.5f);
        METMap.put("Traps", 4.5f);
        METMap.put("Lats", 4.5f);

    }

    public Float getCalories(int exercise_id, int reps) {

        Logger.dLog(String.format("exercise_id=%d, reps=%d", exercise_id, reps));

        Float exerciseMET = 4f; // default
        exerciseMET = METMap.get(exerciseDirectoryManager.getMuscleGroup(exercise_id));

        Float bodyWeight = databaseManager.getCurrentBodyWeight();

        float timeInSeconds = (reps * SECONDS_FOR_TEN_REPS) / 10f;
        float timeInMinutes = timeInSeconds / 60f;

        float oxygenConsumed = exerciseMET * OxygenConsumptionRate * bodyWeight * timeInMinutes;

        float calories = (oxygenConsumed / 1000f) * 5;

//        System.out.println(String.format("MET = %.2f | reps = %d | timeinmin = %.2f | calories = %.2f",
//                exerciseMET, reps, timeInMinutes, calories));

//        Logger.iLog("");
        Logger.dLog(String.format("calories=%.2f", calories));

        return calories;

    }
}
