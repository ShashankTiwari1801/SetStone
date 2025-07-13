package com.belphegor.setstone;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.belphegor.setstone.database_manager.DatabaseManager;
import com.belphegor.setstone.ui.ExerciseAdderCategoryHeader;
import com.belphegor.setstone.ui_manager.ExerciseAdderCategoryHeaderManager;
import com.belphegor.setstone.util.Logger;

public class ExerciseAdderActivity extends AppCompatActivity {

    final static String[] WEEKDAYS = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"};

    TextView TVDayText;
    final static int idTVDayText = R.id.TV_EXERCISE_ADDER_DAY_TEXT;

    int day_id = 0;

    final static int idLLExerciseAdder = R.id.LL_EXERCISE_ADDER_HEADER;
    LinearLayout LLHeader;
    ExerciseAdderCategoryHeader exerciseAdderCategoryHeader;
    ExerciseAdderCategoryHeaderManager exerciseAdderCategoryHeaderManager;

    final static int idLLExerciseContainerList = R.id.LL_EXERCISE_ADDER_LIST_CONTAINER;
    LinearLayout LLExerciseContainerList;

    final static int idTVDoneBtn = R.id.TV_DONE_BTN;
    TextView TVDoneBtn;

    public void init(){

        TVDayText = findViewById(idTVDayText);
        TVDayText.setText(WEEKDAYS[day_id]);

        LLExerciseContainerList = findViewById(idLLExerciseContainerList);

        LLHeader = findViewById(idLLExerciseAdder);
        exerciseAdderCategoryHeader = new ExerciseAdderCategoryHeader(LLHeader);
        exerciseAdderCategoryHeaderManager = new ExerciseAdderCategoryHeaderManager(exerciseAdderCategoryHeader, LLExerciseContainerList, day_id);

        TVDoneBtn = findViewById(idTVDoneBtn);
        TVDoneBtn.setOnClickListener(submitBtnOnClickListener());
    }

    public View.OnClickListener submitBtnOnClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.eLog("Selected ids = " + exerciseAdderCategoryHeaderManager.getSelectedExercises().toString());
                DatabaseManager databaseManager = new DatabaseManager(view.getContext());
                databaseManager.addExercisesToDay(day_id, exerciseAdderCategoryHeaderManager.getSelectedExercises());
                finish();
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exercise_adder);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        day_id = getIntent().getIntExtra("DAY_ID", -1);

        init();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}