package com.belphegor.setstone.ui;

import android.widget.LinearLayout;

import com.belphegor.setstone.R;

public class DailyExerciseViewer {

    private static final int LAYOUT_ID = R.layout.daily_exercise_viewer;
    private static final int CONTAINER_ID = R.id.LL_DAILY_EXERCISE_VIEWER_CONTAINER;

    final LinearLayout LLDailyExerciseViewer;

    LinearLayout containerLL;

    public DailyExerciseViewer(LinearLayout LLDailyExerciseViewer){

        this.LLDailyExerciseViewer = LLDailyExerciseViewer;

        init();

    }

    public void init(){
        containerLL = (LinearLayout)LLDailyExerciseViewer.findViewById(CONTAINER_ID);
    }

    public void addExerciseViewerCard(DailyExerciseViewerCard dailyExerciseViewerCard){
        containerLL.addView(dailyExerciseViewerCard.getRoot(), 0);
    }

    public LinearLayout getContainer(){
        return containerLL;
    }

    public void removeAllViews(){
        int total_views = containerLL.getChildCount();
        if(total_views == 1){return;}
        else{
            while (containerLL.getChildCount() > 1){
                containerLL.removeViewAt(0);
            }
        }
    }

}
