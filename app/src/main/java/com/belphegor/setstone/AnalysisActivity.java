package com.belphegor.setstone;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.belphegor.setstone.analysis.DailyTonnage;
import com.belphegor.setstone.database_manager.DatabaseManager;
import com.belphegor.setstone.ui.AnalysisActivityDurationPill;
import com.belphegor.setstone.ui.MuscleDistributionBar;
import com.belphegor.setstone.ui.MuscleProgress;
import com.belphegor.setstone.ui.NavBar;
import com.belphegor.setstone.ui.WeightGraph;
import com.belphegor.setstone.ui_manager.AnalysisDurationContainerManager;
import com.belphegor.setstone.ui_manager.MuscleDistributionBarManager;
import com.belphegor.setstone.ui_manager.MuscleProgressContainerManager;
import com.belphegor.setstone.ui_manager.NavBarManager;
import com.belphegor.setstone.ui_manager.WeightGraphManager;
import com.belphegor.setstone.util.DateManager;
import com.belphegor.setstone.util.DeviceDimensionManager;
import com.belphegor.setstone.util.ExerciseDirectoryManager;
import com.belphegor.setstone.util.ExerciseRecordForDuration;
import com.belphegor.setstone.util.JSONHelper;
import com.belphegor.setstone.util.Logger;
import com.belphegor.setstone.util.MuscleIconManager;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AnalysisActivity extends AppCompatActivity {


    Context context;

    DatabaseManager databaseManager;
    MuscleIconManager muscleIconManager;
    DateManager dateManager;
    ExerciseDirectoryManager exerciseDirectoryManager;

    final static int id_RL_MAIN = R.id.main;
    RelativeLayout RLMain;

    NavBar navBar;
    NavBarManager navBarManager;

    final static int id_LL_HOR_GRAPH = R.id.LL_HORIZONTAL_GRAPH;
    LinearLayout LL_HORIZONTAL_GRAPH;

    final static int id_muscle_progress_container_ll = R.id.LL_MUSCLE_PROGRESS_CONTAINER;
    LinearLayout muscleProgressContainerLL;

    final static int id_analysis_duration_container_ll = R.id.LL_ANALYSIS_DURATION_CONTAINER;
    LinearLayout analysisDurationContainer;
    AnalysisDurationContainerManager analysisDurationContainerManager;

    MuscleDistributionBar muscleDistributionBar;
    MuscleDistributionBarManager muscleDistributionBarManager;


    MuscleProgressContainerManager muscleProgressContainerManager;
    //GRAPHS
//    final static int id_daily_tonnage_lc = R.id.LC_DAILY_TONNAGE;
    LineChart dailyTonnageLC;
    DailyTonnage dailyTonnage;

    public void init(){
        RLMain = findViewById(id_RL_MAIN);
        LL_HORIZONTAL_GRAPH = findViewById(id_LL_HOR_GRAPH);
        muscleProgressContainerLL = findViewById(id_muscle_progress_container_ll);
        analysisDurationContainer = findViewById(id_analysis_duration_container_ll);
//        dailyTonnageLC = findViewById(id_daily_tonnage_lc);
    }

    private void initUtils() {
        muscleIconManager = new MuscleIconManager(context);
        databaseManager = new DatabaseManager(context);
        dateManager = new DateManager();
        exerciseDirectoryManager = new ExerciseDirectoryManager(new JSONHelper(context));
    }

    public void initElements(){
        navBar = new NavBar(context, RLMain);
        navBarManager = new NavBarManager(context, navBar, context.getClass());
        RLMain.addView(navBar.getRoot());

        muscleDistributionBar = new MuscleDistributionBar(LL_HORIZONTAL_GRAPH);
        muscleDistributionBarManager = new MuscleDistributionBarManager(muscleDistributionBar, databaseManager,
                dateManager, exerciseDirectoryManager, muscleIconManager);

        muscleProgressContainerManager = new MuscleProgressContainerManager(muscleProgressContainerLL,databaseManager);

        muscleDistributionBarManager.reloadData(ExerciseRecordForDuration.Duration.ETERNITY);
        muscleProgressContainerManager.reloadData(ExerciseRecordForDuration.Duration.ETERNITY);

//        dailyTonnage = new DailyTonnage(databaseManager, dailyTonnageLC);

        analysisDurationContainerManager = new AnalysisDurationContainerManager(analysisDurationContainer, muscleProgressContainerManager,
                muscleDistributionBarManager, dailyTonnage);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_analysis);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.context = this;
        init();
        initUtils();
        initElements();
    }



    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}