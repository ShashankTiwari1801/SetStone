package com.belphegor.setstone.ui;

import android.content.Context;
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

    static Map<String, Integer> map;

    public static void getMuscleLabelMap() {



    }


    public static LinearLayout getMuscleProgressBar(Context context, LinearLayout parent, String muscleName, float factor){

        map = new HashMap<>();


        Map<String, Pair<Integer, Integer>> map = new HashMap<>();

        map.put("Abs", new Pair<>(R.color.abs_card_complement, R.color.abs_card));
        map.put("Biceps", new Pair<>(R.color.biceps_card_complement, R.color.biceps_card));
        map.put("Triceps", new Pair<>(R.color.triceps_card_complement, R.color.triceps_card));
        map.put("Calves", new Pair<>(R.color.calves_card_complement, R.color.calves_card));
        map.put("Hamstrings", new Pair<>(R.color.hamstrings_card_complement, R.color.hamstrings_card));
        map.put("Lats", new Pair<>(R.color.lats_card_complement, R.color.lats_card));
        map.put("Forearms", new Pair<>(R.color.forearms_card_complement, R.color.forearms_card));
        map.put("Quads", new Pair<>(R.color.quads_card_complement, R.color.quads_card));
        map.put("Shoulder", new Pair<>(R.color.shoulder_card_complement, R.color.shoulder_card));
        map.put("Traps", new Pair<>(R.color.traps_card_complement, R.color.traps_card));
        map.put("Back", new Pair<>(R.color.back_card_complement, R.color.back_card));
        map.put("Glutes", new Pair<>(R.color.glutes_card_complement, R.color.glutes_card));
        map.put("Chest", new Pair<>(R.color.chest_card_complement, R.color.chest_card));


        LinearLayout bar = (LinearLayout) LayoutInflater.from(context).inflate(id_progress_bar, parent, false);

        ((TextView)bar.getChildAt(0)).setTextColor(ContextCompat.getColor(context, map.get(muscleName).first));

        TextView tvbar = (TextView)((RelativeLayout) bar.getChildAt(1)).getChildAt(0);
        TextView tvperc = (TextView)((RelativeLayout) bar.getChildAt(1)).getChildAt(1);

        tvperc.setText(String.format("%05.2f %%", (factor*100)));
        tvperc.setTextColor(ContextCompat.getColor(context, map.get(muscleName).second));

        tvbar.setBackgroundColor(ContextCompat.getColor(context, map.get(muscleName).first));

        tvbar.post(new Runnable() {
            @Override
            public void run() {
                int W = tvbar.getWidth();
                RelativeLayout.LayoutParams lllp = (RelativeLayout.LayoutParams) tvbar.getLayoutParams();
                lllp.width = (int) (W * factor);
                tvbar.setLayoutParams(lllp);
            }
        });



        ((TextView)(bar.getChildAt(0))).setText(muscleName);
        return bar;
    }

}
