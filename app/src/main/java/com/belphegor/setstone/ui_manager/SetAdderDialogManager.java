package com.belphegor.setstone.ui_manager;

import android.view.View;

import com.belphegor.setstone.database_manager.DatabaseManager;
import com.belphegor.setstone.ui.SetAdderDialog;

import java.time.LocalDate;

public class SetAdderDialogManager {

    final SetAdderDialog setAdderDialog;
    final SetRecordsContainerManager setRecordsContainerManager;
    final DatabaseManager databaseManager;

    public SetAdderDialogManager(SetAdderDialog setAdderDialog,
                                 SetRecordsContainerManager setRecordsContainerManager,
                                 DatabaseManager databaseManager) {

        this.setAdderDialog = setAdderDialog;
        this.setRecordsContainerManager = setRecordsContainerManager;

        this.databaseManager = databaseManager;

        init();
    }

    public View.OnClickListener submitButtonAction(){
        // required args = DATE, EXERCISE_ID. WEIGHT, REPS
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LocalDate localDate = LocalDate.now();
                String DATE = localDate.toString();

                boolean loaded = setAdderDialog.loadValues();

                if(!loaded){return;}

                int EXERCISE_ID = setRecordsContainerManager.EXERCISE_ID;;
                float WEIGHT = setAdderDialog.getWeight();
                int REPS = setAdderDialog.getReps();

                databaseManager.addExerciseRecordToTable(DATE, WEIGHT, REPS, EXERCISE_ID);

                setRecordsContainerManager.reload();
            }
        };
    }

    public void init() {
        setAdderDialog.setOnClickListener(submitButtonAction());
    }

}
