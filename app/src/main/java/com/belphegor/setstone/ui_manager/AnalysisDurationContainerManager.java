package com.belphegor.setstone.ui_manager;

import android.view.View;
import android.widget.LinearLayout;

import com.belphegor.setstone.ui.AnalysisActivityDurationPill;
import com.belphegor.setstone.util.ExerciseRecordForDuration;

public class AnalysisDurationContainerManager {

    final LinearLayout container;
    final MuscleProgressContainerManager muscleProgressContainerManager;
    final MuscleDistributionBarManager muscleDistributionBarManager;
    AnalysisActivityDurationPill lastSelected = null;

    public AnalysisDurationContainerManager(LinearLayout container, MuscleProgressContainerManager muscleProgressContainerManager, MuscleDistributionBarManager muscleDistributionBarManager) {
        this.container = container;
        this.muscleProgressContainerManager = muscleProgressContainerManager;
        this.muscleDistributionBarManager = muscleDistributionBarManager;
        init();
    }

    public View.OnClickListener loadAction(AnalysisActivityDurationPill pill,
                                           ExerciseRecordForDuration.Duration duration,
                                           MuscleDistributionBarManager muscleDistributionBarManager,
                                           MuscleProgressContainerManager muscleProgressContainerManager){

        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                muscleProgressContainerManager.reloadData(duration);
                muscleDistributionBarManager.reloadData(duration);

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
                    analysisActivityDurationPill, duration, muscleDistributionBarManager, muscleProgressContainerManager
            ));
        }

    }
}
