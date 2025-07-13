package com.belphegor.setstone.ui_manager;

import android.view.View;
import android.widget.LinearLayout;

import com.belphegor.setstone.analysis.DailyTonnage;
import com.belphegor.setstone.ui.AnalysisActivityDurationPill;
import com.belphegor.setstone.util.ExerciseRecordForDuration;

public class AnalysisDurationContainerManager {

    final LinearLayout container;
    final MuscleProgressContainerManager muscleProgressContainerManager;
    final MuscleDistributionBarManager muscleDistributionBarManager;
    final DailyTonnage dailyTonnage;
    AnalysisActivityDurationPill lastSelected = null;

    public AnalysisDurationContainerManager(LinearLayout container,
                                            MuscleProgressContainerManager muscleProgressContainerManager,
                                            MuscleDistributionBarManager muscleDistributionBarManager, DailyTonnage dailyTonnage) {
        this.container = container;
        this.muscleProgressContainerManager = muscleProgressContainerManager;
        this.muscleDistributionBarManager = muscleDistributionBarManager;
        this.dailyTonnage = dailyTonnage;

        init();
    }

    public View.OnClickListener loadAction(AnalysisActivityDurationPill pill,
                                           ExerciseRecordForDuration.Duration duration,
                                           MuscleDistributionBarManager muscleDistributionBarManager,
                                           MuscleProgressContainerManager muscleProgressContainerManager,
                                           DailyTonnage dailyTonnage){

        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                muscleProgressContainerManager.reloadData(duration);
                muscleDistributionBarManager.reloadData(duration);
//                dailyTonnage.reload(duration);

                if(lastSelected != null){
                    lastSelected.deselect();
                }

                pill.select();
                lastSelected = pill;
            }
        };
    }

    private void init() {
        container.removeAllViews();

        for (ExerciseRecordForDuration.Duration duration: ExerciseRecordForDuration.Duration.values()){
            AnalysisActivityDurationPill analysisActivityDurationPill = new AnalysisActivityDurationPill(
                    duration, container
            );
            container.addView(analysisActivityDurationPill.getRoot());
            analysisActivityDurationPill.deselect();
            analysisActivityDurationPill.setOnClickListener(loadAction(
                    analysisActivityDurationPill, duration, muscleDistributionBarManager, muscleProgressContainerManager,
                    dailyTonnage
            ));
        }

    }
}
