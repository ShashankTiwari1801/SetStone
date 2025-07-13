package com.belphegor.setstone.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Pair;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.belphegor.setstone.R;

import java.util.HashMap;
import java.util.Map;

public class MuscleProgress {

    public static int id_progress_bar = R.layout.analysis_activity_muscle_progress;

    static Map<String, Pair<Integer, Integer>> colorMap = new HashMap<>();
    static {

        colorMap.put("Abs", new Pair<>(R.color.abs_card_complement, R.color.abs_card));
        colorMap.put("Biceps", new Pair<>(R.color.biceps_card_complement, R.color.biceps_card));
        colorMap.put("Triceps", new Pair<>(R.color.triceps_card_complement, R.color.triceps_card));
        colorMap.put("Calves", new Pair<>(R.color.calves_card_complement, R.color.calves_card));
        colorMap.put("Hamstrings", new Pair<>(R.color.hamstrings_card_complement, R.color.hamstrings_card));
        colorMap.put("Lats", new Pair<>(R.color.lats_card_complement, R.color.lats_card));
        colorMap.put("Forearms", new Pair<>(R.color.forearms_card_complement, R.color.forearms_card));
        colorMap.put("Quads", new Pair<>(R.color.quads_card_complement, R.color.quads_card));
        colorMap.put("Shoulder", new Pair<>(R.color.shoulder_card_complement, R.color.shoulder_card));
        colorMap.put("Traps", new Pair<>(R.color.traps_card_complement, R.color.traps_card));
        colorMap.put("Back", new Pair<>(R.color.back_card_complement, R.color.back_card));
        colorMap.put("Glutes", new Pair<>(R.color.glutes_card_complement, R.color.glutes_card));
        colorMap.put("Chest", new Pair<>(R.color.chest_card_complement, R.color.chest_card));

    }

    public static LinearLayout getMuscleProgressBar(Context context, LinearLayout parent, String muscleName, float factor) {

        Pair<Integer, Integer> colors = colorMap.get(muscleName);
        if (colors == null) {
            throw new IllegalArgumentException("Unknown muscle: " + muscleName);
        }

        String drawableName = "background_label_" + muscleName.toLowerCase();
        if(muscleName.startsWith("Shou")){drawableName += "s";}

        int drawableRes = context.getResources().getIdentifier(drawableName, "drawable", context.getPackageName());
        Drawable barBackground = context.getDrawable(drawableRes);

        LinearLayout progressBarLayout = (LinearLayout) LayoutInflater.from(context)
                .inflate(id_progress_bar, parent, false);

        TextView muscleLabel = (TextView) progressBarLayout.getChildAt(0);
        muscleLabel.setText(muscleName);
        muscleLabel.setTextColor(ContextCompat.getColor(context, colors.first));

        RelativeLayout barContainer = (RelativeLayout) progressBarLayout.getChildAt(1);
        TextView barFill = (TextView) barContainer.getChildAt(0);
        TextView barPercent = (TextView) barContainer.getChildAt(1);

        barPercent.setText(String.format("%05.2f %%", factor * 100));
        barPercent.setTextColor(ContextCompat.getColor(context, colors.second));

        barFill.setBackground(barBackground);

        barFill.post(() -> {
            int fullWidth = barFill.getWidth();
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) barFill.getLayoutParams();
            params.width = (int) (fullWidth * factor);
            barFill.setLayoutParams(params);
        });

        return progressBarLayout;
    }
}
