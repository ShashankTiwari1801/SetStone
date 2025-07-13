package com.belphegor.setstone.ui;

import android.health.connect.datatypes.units.BloodGlucose;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.belphegor.setstone.util.Logger;
import com.belphegor.setstone.util.MuscleIconManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class MuscleDistributionBar {

    final LinearLayout barLL;
    List<View> muscleBarViews;

    public MuscleDistributionBar(LinearLayout barLL){
        this.barLL = barLL;
        muscleBarViews = new ArrayList<>();
        init();
    }

    private void init() {
        muscleBarViews = new ArrayList<>();
        for (int i = 0; i < barLL.getChildCount(); i++) {
            muscleBarViews.add(barLL.getChildAt(i));
        }
    }

    public void updateData(final int[] muscleExerciseCountArr){
        System.out.println(Arrays.toString(muscleExerciseCountArr));
        barLL.post(new Runnable() {
            @Override
            public void run() {

                final int W = barLL.getWidth();
                int totalSetSum = 0;
                for (int x: muscleExerciseCountArr) {
                    totalSetSum += x;
                }

                int viewsWidth = 0;
                for (int i = 0; i < muscleExerciseCountArr.length; i++) {

                    float factor = muscleExerciseCountArr[i] / (float) totalSetSum;
                    int barWidth = (int) Math.floor(factor * W);
                    viewsWidth += barWidth;
                    LinearLayout.LayoutParams lllp = (LinearLayout.LayoutParams) muscleBarViews.get(i).getLayoutParams();
                    lllp.width = barWidth;
                    muscleBarViews.get(i).setLayoutParams(lllp);

                }
                Logger.eLog(W + " | " + viewsWidth);
            }
        });
    }



}
