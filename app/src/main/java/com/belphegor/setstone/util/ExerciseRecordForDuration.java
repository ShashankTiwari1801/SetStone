package com.belphegor.setstone.util;

import com.belphegor.setstone.database_manager.DatabaseManager;

import java.time.LocalDate;
import java.util.List;

public class ExerciseRecordForDuration {

    final DatabaseManager databaseManager;

    public enum Duration{
        WEEK, MONTH, YEAR, ETERNITY
    }

    public ExerciseRecordForDuration(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public static List<List<String>> getRecordForDuration(DatabaseManager databaseManager, Duration duration){
        DateManager dateManager = new DateManager();

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

        return databaseManager.getRecordBetween(
                first.toString() , last.toString()
        );


    }
}
