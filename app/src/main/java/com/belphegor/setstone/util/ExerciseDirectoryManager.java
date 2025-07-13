package com.belphegor.setstone.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ExerciseDirectoryManager {

    final JSONHelper jsonHelper;
    final static String WORKOUT_DIR_PATH = "workout.json";

    HashMap<Integer, String[]> ExerciseMap = new HashMap<>();
    List<String> IDList = new ArrayList<>();
    Set<String> muscles = new HashSet<>();

    public ExerciseDirectoryManager(JSONHelper jsonHelper){
        this.jsonHelper = jsonHelper;
        init();
    }

    public void init() {
        JSONObject jsonObject = jsonHelper.parseJSONFile(WORKOUT_DIR_PATH);
        try {
            for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
                String id = it.next();
                IDList.add(id);
                JSONArray jsonArray = (JSONArray) jsonObject.get(id);
                muscles.add(jsonArray.getString(1));
                ExerciseMap.put(Integer.parseInt(id), new String[]{jsonArray.getString(0), jsonArray.getString(1)});
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    public String getExerciseName(int id) {
        //Log.e(this.getClass().getSimpleName(), " ID + "  + id);
        return Objects.requireNonNull(ExerciseMap.get(id))[0];
    }

    public String getMuscleGroup(int id) {
        return Objects.requireNonNull(ExerciseMap.get(id))[1];
    }

    public List<String> getIDList() {
        return IDList;
    }

    public int getID(String exerciseName) {
        for (int ID : ExerciseMap.keySet()) {
            if (ExerciseMap.get(ID)[0].equals(exerciseName)) {
                return ID;
            }
        }
        return -1;
    }

    public List<String> getMuscleList() {
        Log.e("MUSCLES", muscles.toString());
        return new ArrayList<>(muscles);
    }

    public List<Integer> getAllExerciseForMuscle(String muscle){
        List<Integer> res = new ArrayList<>();

        for (int id: ExerciseMap.keySet()){
            if(ExerciseMap.get(id)[1].equals(muscle)){
                res.add(id);
            }
        }

        return res;
    }

}
