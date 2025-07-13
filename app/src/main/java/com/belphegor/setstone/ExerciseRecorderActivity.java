package com.belphegor.setstone;

import android.content.Context;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.belphegor.setstone.database_manager.DatabaseManager;
import com.belphegor.setstone.ui.SetAdderDialog;
import com.belphegor.setstone.ui.SetRecorderHead;
import com.belphegor.setstone.ui.SetRecorderPersonalBest;
import com.belphegor.setstone.ui.SetRecordsContainer;
import com.belphegor.setstone.ui_manager.SetAdderDialogManager;
import com.belphegor.setstone.ui_manager.SetRecordsContainerManager;
import com.belphegor.setstone.util.ExerciseDirectoryManager;
import com.belphegor.setstone.util.JSONHelper;
import com.belphegor.setstone.util.MuscleIconManager;

public class ExerciseRecorderActivity extends AppCompatActivity {

    int EXERCISE_ID;
    final static String EXERCISE_ID_INTENT_KEY = "EXERCISE_ID";

    /*
    COMPONENTS:

    set_recorder_header : muscle Icon, Exercise name
    personal_record : WeightText
    Set details list : Container for set_card
    set_adder_dialog : EditText Weight, Reps,  Add Set Button

     */

    //context
    Context context;

    //util
    ExerciseDirectoryManager exerciseDirectoryManager;
    MuscleIconManager muscleIconManager;
    DatabaseManager databaseManager;

    LinearLayout LL_SET_RECORDER_HEADER;
    SetRecorderHead setRecorderHead;

    LinearLayout LL_PERSONAL_RECORD;
    SetRecorderPersonalBest setRecorderPersonalBest;

    LinearLayout LL_SET_RECORDER_SET_DETAILS_CONTAINER;
    SetRecordsContainer setRecordsContainer;
    SetRecordsContainerManager setRecordsContainerManager;

    GridLayout GL_SET_RECORDER_DIALOG;
    SetAdderDialog setAdderDialog;
    SetAdderDialogManager setAdderDialogManager;

    public void init(){

        this.context = this;

        exerciseDirectoryManager = new ExerciseDirectoryManager(new JSONHelper(context));
        muscleIconManager = new MuscleIconManager(context);
        databaseManager = new DatabaseManager(context);


        LL_SET_RECORDER_HEADER = findViewById(R.id.LL_SET_RECORDER_HEADER);
        setRecorderHead = new SetRecorderHead(LL_SET_RECORDER_HEADER, exerciseDirectoryManager, muscleIconManager);
        setRecorderHead.load(EXERCISE_ID);

        LL_PERSONAL_RECORD = findViewById(R.id.LL_PERSONAL_RECORD);
        setRecorderPersonalBest = new SetRecorderPersonalBest(LL_PERSONAL_RECORD);
        setRecorderPersonalBest.setPersonalBest(databaseManager.getExercisePersonalBest(EXERCISE_ID));

        LL_SET_RECORDER_SET_DETAILS_CONTAINER = findViewById(R.id.LL_SET_RECORDER_SET_DETAILS_CONTAINER);
        setRecordsContainer = new SetRecordsContainer(LL_SET_RECORDER_SET_DETAILS_CONTAINER);
        setRecordsContainerManager = new SetRecordsContainerManager(setRecordsContainer, EXERCISE_ID);

        GL_SET_RECORDER_DIALOG = findViewById(R.id.GL_SET_ADDER_DIALOG);
        setAdderDialog = new SetAdderDialog(GL_SET_RECORDER_DIALOG);
        setAdderDialogManager = new SetAdderDialogManager(setAdderDialog, setRecordsContainerManager, databaseManager);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exercise_recorder);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EXERCISE_ID = getIntent().getIntExtra(EXERCISE_ID_INTENT_KEY,0);

        init();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}