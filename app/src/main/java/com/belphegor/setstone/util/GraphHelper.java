package com.belphegor.setstone.util;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class GraphHelper {

    public static LineData getLineData(List<Entry> data, Drawable backgroundDrawable){

        LineDataSet lineDataSet = new LineDataSet(data, "");
        customizeLineDataSet(lineDataSet, backgroundDrawable ,Color.WHITE);
        LineData lineData = new LineData(lineDataSet);
        return lineData;
    }

    public static void customizeLineDataSet(LineDataSet lineDataSet, Drawable backgroundDrawable, int graphColor) {
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setValueTextColor(Color.WHITE);
        lineDataSet.setColor(graphColor);
        lineDataSet.setFillDrawable(backgroundDrawable);
        lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        lineDataSet.setLineWidth(5);

    }


    public static void customizeLineChart(LineChart lineChart) {
        YAxis yAxisL = lineChart.getAxisLeft();
        YAxis yAxisR = lineChart.getAxisRight();
        XAxis xAxisU = lineChart.getXAxis();
        xAxisU.setDrawGridLines(false);
        yAxisR.setEnabled(false);
        yAxisL.setDrawGridLines(false);
        yAxisL.setTextColor(Color.WHITE);
        lineChart.setHighlightPerDragEnabled(false);
        lineChart.setHighlightPerTapEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setDrawBorders(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.setDescription(null);
        xAxisU.setEnabled(false);
    }

    public static List<Entry> parseDataForGraph(List<Float> data){
        int I = 0;
        List<Entry> res = new ArrayList<>();
        for(Float x: data){
            res.add(new Entry(I++, x));
        }
        return res;
    }

}
