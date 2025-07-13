package com.belphegor.setstone.ui_manager;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.belphegor.setstone.R;
import com.belphegor.setstone.database_manager.DatabaseManager;
import com.belphegor.setstone.ui.WeightGraph;
import com.belphegor.setstone.util.DateManager;
import com.belphegor.setstone.util.GraphHelper;
import com.github.mikephil.charting.data.Entry;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeightGraphManager {

    final WeightGraph weightGraph;
    final DatabaseManager databaseManager;
    List<Float> lastNWeightRecords;
    final static int NUMBER_OF_DAYS_TO_GET_RECORD = 7;
    final Context context;
    float[] daysVals;
    final static int THUMBS_UP_ICON = R.drawable.icon_thumbs_up;
    final static int THUMBS_DOWN_ICON = R.drawable.icon_thumbs_down;


    public WeightGraphManager(WeightGraph weightGraph, DatabaseManager databaseManager, Context context) {
        this.weightGraph = weightGraph;
        this.databaseManager = databaseManager;
        this.context = context;

        init();
    }

    public void deleteAll() {
        weightGraph.reload();
    }

    public void loadData() {

        List<List<String>> records = databaseManager.getLastNWeightRecord(NUMBER_OF_DAYS_TO_GET_RECORD);
        Map<String, Float> dateToWeight = new HashMap<>();

        for (List<String> record : records) {
            dateToWeight.put(record.get(0), Float.parseFloat(record.get(1)));
        }

        System.out.println(dateToWeight);

//        Collections.reverse(lastNWeightRecords);

        DateManager dateManager = new DateManager();
        LocalDate first = dateManager.firstDayOfTheWeek;
        LocalDate last = dateManager.lastDayOfTheWeek;

        daysVals = new float[NUMBER_OF_DAYS_TO_GET_RECORD];

        int i = 0;
        while (!first.equals(last.plusDays(1))) {
            if (dateToWeight.containsKey(first.toString())) {
                daysVals[i] = dateToWeight.get(first.toString());
            }
            first = first.plusDays(1);
            i++;
        }

        System.out.println(Arrays.toString(daysVals));

        lastNWeightRecords = new ArrayList<>();
        for (float x : daysVals) {
            lastNWeightRecords.add(x);
        }
        List<Entry> list = GraphHelper.parseDataForGraph(lastNWeightRecords);


        weightGraph.setLineData(GraphHelper.getLineData(list, context.getDrawable(R.drawable.background_graph_back_weight)));
        weightGraph.invalidate();
    }

    private void init() {

        daysVals = new float[NUMBER_OF_DAYS_TO_GET_RECORD];

        loadData();
    }

    public float[] getWeekData() {
        return daysVals;
    }

    public static void fillStartEndValue(TextView startText, TextView endText, TextView deltaText, ImageView thumbsImage, float[] daysVals) {
        float startVal = 0;
        float endVal = 0;
        for (int i = 0; i < NUMBER_OF_DAYS_TO_GET_RECORD; i++) {
            if (daysVals[i] != 0) {
                startVal = daysVals[i];
                break;
            }
        }
        for (int i = NUMBER_OF_DAYS_TO_GET_RECORD - 1; i >= 0; i--) {
            if (daysVals[i] != 0) {
                endVal = daysVals[i];
                break;
            }
        }

        float delta = endVal - startVal;

        if(delta < 0){thumbsImage.setImageResource(THUMBS_UP_ICON);}
        else{thumbsImage.setImageResource(THUMBS_DOWN_ICON);}

        startText.setText(String.format("%.2f Kg", startVal));
        endText.setText(String.format("%.2f Kg", endVal));
        deltaText.setText(String.format("%s%.2f Kg", (delta > 0) ? "+" : "-",delta));
    }
}
