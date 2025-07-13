package com.belphegor.setstone;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.belphegor.setstone.animations.JumpAnimator;
import com.belphegor.setstone.database_manager.DatabaseManager;
import com.belphegor.setstone.ui.AddExerciseButton;
import com.belphegor.setstone.ui.DailyExerciseViewer;
import com.belphegor.setstone.ui.HeatmapCalendar;
import com.belphegor.setstone.ui.NavBar;
import com.belphegor.setstone.ui.WeekRow;
import com.belphegor.setstone.ui_manager.AddExerciseButtonManager;
import com.belphegor.setstone.ui_manager.DailyExerciseViewerManager;
import com.belphegor.setstone.ui_manager.HeatMapCalendarManager;
import com.belphegor.setstone.ui_manager.NavBarManager;
import com.belphegor.setstone.ui_manager.WeekRowManager;
import com.belphegor.setstone.util.ExerciseDirectoryManager;
import com.belphegor.setstone.util.FileToDatabaseExport;
import com.belphegor.setstone.util.JSONHelper;
import com.belphegor.setstone.util.MuscleIconManager;
import com.belphegor.setstone.util.ResourceManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


public class MainActivity extends AppCompatActivity {

    Context context;
    RelativeLayout RLMain;

    LinearLayout CONTAINER;
    LinearLayout LLDailyExerciseViewer;
    LinearLayout LLWeekRow;
    LinearLayout LLCalendar;
    LinearLayout LLAddExerciseBtn;

    NavBar navBar;
    NavBarManager navBarManager;
    DailyExerciseViewer dailyExerciseViewer;
    DailyExerciseViewerManager dailyExerciseViewerManager;
    WeekRow weekRow;
    WeekRowManager weekRowManager;
    HeatmapCalendar heatmapCalendar;
    HeatMapCalendarManager heatMapCalendarManager;
    AddExerciseButton addExerciseButton;
    AddExerciseButtonManager addExerciseButtonManager;

    //utils
    DatabaseManager databaseManager;
    JSONHelper jsonHelper;
    ExerciseDirectoryManager exerciseDirectoryManager;
    MuscleIconManager muscleIconManager;
    LayoutInflater layoutInflater;
    ResourceManager resourceManager;

    int global_i = 0;


    public void init(){
        this.context = this;
        this.RLMain = findViewById(R.id.main);

        // init Utils
        databaseManager = new DatabaseManager(context);
        jsonHelper = new JSONHelper(context);
        exerciseDirectoryManager = new ExerciseDirectoryManager(jsonHelper);
        muscleIconManager = new MuscleIconManager(context);
        layoutInflater = LayoutInflater.from(context);
        resourceManager = new ResourceManager(context);



        LLDailyExerciseViewer = findViewById(R.id.LL_DAILY_EXERCISE_VIEWER);
        dailyExerciseViewer = new DailyExerciseViewer(LLDailyExerciseViewer);
        dailyExerciseViewerManager = new DailyExerciseViewerManager(dailyExerciseViewer, databaseManager, exerciseDirectoryManager, muscleIconManager, layoutInflater);

        LLWeekRow = findViewById(R.id.LL_WEEK_ROW);
        weekRow = new WeekRow(LLWeekRow, resourceManager);
        weekRowManager = new WeekRowManager(weekRow, dailyExerciseViewerManager);

        navBar = new NavBar(context, RLMain);
        navBarManager = new NavBarManager(context, navBar, this.getClass());
        RLMain.addView(navBar.getRoot());

        LLCalendar = findViewById(R.id.LL_CALENDAR);
        heatmapCalendar = new HeatmapCalendar(LLCalendar);
        heatMapCalendarManager = new HeatMapCalendarManager(heatmapCalendar, databaseManager, layoutInflater, resourceManager);

        LLAddExerciseBtn = findViewById(R.id.LL_ADD_EXERCISE_BTN);
        addExerciseButton = new AddExerciseButton(LLAddExerciseBtn);
        addExerciseButtonManager = new AddExerciseButtonManager(addExerciseButton, dailyExerciseViewerManager);

        databaseManager.describeTable("exercise_records");

//        FileToDatabaseExport export = new FileToDatabaseExport(context);


    }

    public void addAnim(View child, JumpAnimator jumpAnimator, Intent[] all_activities,final int i){
        child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpAnimator.animateBallJump();

                Intent jumpIntent = all_activities[i];
                Log.e("INTENT = ", jumpIntent.toString());
                startActivity(jumpIntent);

                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        copyDatabaseFromAssets(this, "WorkoutData.db");

        init();

    }

    public static void copyDatabaseFromAssets(Context context, String dbName) {
        File dbPath = context.getDatabasePath(dbName);

        // If already exists, skip copy
        if (dbPath.exists()) {
            Log.v("DB", "Database already exists at: " + dbPath);
            dbPath.delete();
            return;
        }

        // Ensure directories exist
        dbPath.getParentFile().mkdirs();

    }


    public void exportDatabase(Context context) {
        try {
            File dbFile = context.getDatabasePath("Workout.db");
            FileInputStream fis = new FileInputStream(dbFile);

            File exportDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            if (!exportDir.exists()) exportDir.mkdirs();

            File outFile = new File(exportDir, "copied_database.db");
            FileOutputStream fos = new FileOutputStream(outFile);

            byte[] buffer = new byte[1024];
            int length;

            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }

            fos.flush();
            fos.close();
            fis.close();

            Log.d("EXPORT", "DB exported to: " + outFile.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void onResume() {
        super.onResume();

        dailyExerciseViewerManager.loadExerciseForDayID(dailyExerciseViewerManager.getSelectedDayID());

    }
}