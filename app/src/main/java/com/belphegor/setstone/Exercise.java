package com.belphegor.setstone;

import androidx.annotation.NonNull;

import java.util.List;

public class Exercise {
    final String date;
    final int exerciseId;
    final float weight;
    final int reps;
    final int rowID;

    final static int ROW_ID = 0;
    final static int DATE_ID = 1;
    final static int EXERCISE_ID_ID = 2;
    final static int WEIGHT_ID = 3;
    final static int REPS_ID = 4;


    public Exercise(List<String> data){
        this.date = data.get(DATE_ID);
        this.exerciseId = Integer.parseInt(data.get(EXERCISE_ID_ID));
        this.weight = Float.parseFloat(data.get(WEIGHT_ID));
        this.reps = Integer.parseInt(data.get(REPS_ID));
        this.rowID = Integer.parseInt(data.get(ROW_ID));
    }

    public String getDate() {
        return date;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public float getWeight() {
        return weight;
    }

    public int getReps() {
        return reps;
    }

    public int getRowID() {
        return rowID;
    }

    @NonNull
    @Override
    public String toString() {

        String res = String.format("DATE = %s | EXERCISEID = %d | WEIGHT = %.2f | REPS = %d", getDate(), getExerciseId(), getWeight(), getReps());
        return res;
//        return super.toString();
    }
}
