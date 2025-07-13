package com.belphegor.setstone.analysis;

import android.graphics.Color;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.belphegor.setstone.R;
import com.belphegor.setstone.database_manager.DatabaseManager;
import com.belphegor.setstone.util.DateManager;
import com.belphegor.setstone.util.ExerciseRecordForDuration;
import com.belphegor.setstone.util.GraphHelper;
import com.belphegor.setstone.util.Logger;
import com.belphegor.setstone.util.ResourceManager;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DailyTonnage {

    final DateManager dateManager;
    final DatabaseManager databaseManager;
    final LineChart dailyTonnageLC;


    public DailyTonnage(DatabaseManager databaseManager, LineChart dailyTonnageLC) {

        this.databaseManager = databaseManager;
        this.dailyTonnageLC = dailyTonnageLC;
        this.dateManager = new DateManager();

        init();

    }

    public LineDataSet getCustomizedLineDateSet(LineDataSet lineDataSet){
        lineDataSet.setLineWidth(0.5f);
        lineDataSet.setColor(
                ContextCompat.getColor(dailyTonnageLC.getContext(), R.color.gradient_end_neon_energy)
        );
        return lineDataSet;
    }

    public void parseRawData(List<LocalDate> fl, List<List<String>> rawData){

        Map<String, Float> dateToTonnage = new HashMap<>();

        for(List<String> record: rawData){

            String date = record.get(0);
            Float values = Float.parseFloat(record.get(1));

            dateToTonnage.put(date, values);

        }

        LocalDate first = fl.getFirst();
        LocalDate last = fl.getLast();

        System.out.println(ChronoUnit.DAYS.between(first, last));

        List<Float> dailyVals = new ArrayList<>();
        List<Integer> monthStartDays = new ArrayList<>();

        int i = 0;
        while (!first.equals(last.plusDays(1))) {

            if(dateToTonnage.containsKey(first.toString())){
                dailyVals.add(dateToTonnage.get(first.toString()));
            }
            else{
                dailyVals.add(0f);
            }

            if(first.getDayOfMonth() == 1){
                monthStartDays.add(i);
            }

            i++;
            first = first.plusDays(1);
        }

        List<Entry> graphData = GraphHelper.parseDataForGraph(dailyVals);

        LineData lineData = GraphHelper.getLineData(
                graphData,
                dailyTonnageLC.getContext().getDrawable(R.drawable.background_graph_back_weight)
        );

        XAxis xAxis = dailyTonnageLC.getXAxis();
        xAxis.setEnabled(true);
        xAxis.removeAllLimitLines(); // Clear old lines if needed
        xAxis.setDrawLabels(false);


        Logger.iLog(monthStartDays.toString());

        List<String> monthNames = Arrays.asList(
                "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        );

        i = 0;
        for (int day : monthStartDays) {
            LimitLine line = new LimitLine(day, monthNames.get(i%monthNames.size()));
            line.setTextColor(Color.WHITE);
            line.setLineColor(ContextCompat.getColor(dailyTonnageLC.getContext(), R.color.blue_grad));
            line.setLineWidth(0.2f);
            xAxis.addLimitLine(line);
            i++;
        }

        dailyTonnageLC.invalidate();

        LineDataSet lineDataSet = ((LineDataSet)lineData.getDataSets().get(0));
        lineDataSet = getCustomizedLineDateSet(lineDataSet);

        dailyTonnageLC.setData(lineData);

        dailyTonnageLC.invalidate();
    }

    public void reload(ExerciseRecordForDuration.Duration duration){

        List<LocalDate> fl = ExerciseRecordForDuration.getFirstLast(duration);
        System.out.println(fl.toString());

        List<List<String>> rawData = databaseManager.getDailyTonnageBetween(
                fl.getFirst().toString(), fl.getLast().toString()
        );

        parseRawData(fl, rawData);
    }

    public void init(){
        GraphHelper.customizeLineChart(dailyTonnageLC);

    }
}
