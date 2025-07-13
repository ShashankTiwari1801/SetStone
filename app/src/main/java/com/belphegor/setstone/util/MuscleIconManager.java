package com.belphegor.setstone.util;

import android.content.Context;
import android.util.Log;

import com.belphegor.setstone.R;

import java.util.HashMap;
import java.util.Set;

public class MuscleIconManager {
    Context context;
    HashMap<String, Integer> muscleToDrawableMap = new HashMap<>();

    public MuscleIconManager(Context context) {
        this.context = context;
        init();
    }

    public void init() {
        muscleToDrawableMap.put(context.getString(R.string.muscle_chest), R.drawable.icon_muscle_chest);
        muscleToDrawableMap.put(context.getString(R.string.muscle_biceps), R.drawable.icon_muscle_biceps);
        muscleToDrawableMap.put(context.getString(R.string.muscle_triceps), R.drawable.icon_muscle_triceps);
        muscleToDrawableMap.put(context.getString(R.string.muscle_calves), R.drawable.icon_muscle_calves);
        muscleToDrawableMap.put(context.getString(R.string.muscle_hamstrings), R.drawable.icon_muscle_hamstring);
        muscleToDrawableMap.put(context.getString(R.string.muscle_lats), R.drawable.icon_muscle_lats);
        muscleToDrawableMap.put(context.getString(R.string.muscle_forearms), R.drawable.icon_muscle_forearm);
        muscleToDrawableMap.put(context.getString(R.string.muscle_quads), R.drawable.icon_muscle_quads);
        muscleToDrawableMap.put(context.getString(R.string.muscle_shoulder), R.drawable.icon_muscle_shoulder);
        muscleToDrawableMap.put(context.getString(R.string.muscle_traps), R.drawable.icon_muscle_traps);
        muscleToDrawableMap.put(context.getString(R.string.muscle_back), R.drawable.icon_muscle_back);
        muscleToDrawableMap.put(context.getString(R.string.muscle_glutes), R.drawable.icon_muscle_glutes);
        muscleToDrawableMap.put(context.getString(R.string.muscle_abs), R.drawable.icon_muscle_abs);
    }

    public Integer getMuscleIconFromName(String muscleName){

        if(!muscleToDrawableMap.containsKey(muscleName)){

            Log.e("EEEE", "Couldn't find" + muscleName);
            return muscleToDrawableMap.get(context.getString(R.string.muscle_biceps));

        }

        return muscleToDrawableMap.get(muscleName);
    }

    public Set<String> getAllMuscleSet(){
        return muscleToDrawableMap.keySet();
    }

}
