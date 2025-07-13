package com.belphegor.setstone.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.belphegor.setstone.util.GraphHelper;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.renderer.LineChartRenderer;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class WeightGraph {
    final Context context;
    final LineChart weightLineChart;

    public WeightGraph(Context context, LineChart weightLineChart) {
        this.context = context;
        this.weightLineChart = weightLineChart;
        init();
        customize();
    }

    public void reload(){
        weightLineChart.clear();
        init();
        customize();
        weightLineChart.invalidate();
    }

    public void init(){
        GraphHelper.customizeLineChart(weightLineChart);
    }

    public class RoundedLineChartRenderer extends LineChartRenderer {

        public RoundedLineChartRenderer(LineDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
            super(chart, animator, viewPortHandler);
        }

        @Override
        protected void drawLinear(Canvas c, ILineDataSet dataSet) {
            super.drawLinear(c, dataSet);

            // Set paint to have rounded stroke caps
            mRenderPaint.setStrokeCap(Paint.Cap.ROUND);
        }
    }

    public void customize(){


        weightLineChart.setRenderer(new RoundedLineChartRenderer(
                weightLineChart, weightLineChart.getAnimator(), weightLineChart.getViewPortHandler()
        ));


        XAxis xAxis = weightLineChart.getXAxis();
//        xAxis.setEnabled(true);

        xAxis.setGranularity(1f); // minimum interval
        xAxis.setLabelCount(5, true); // suggest 5 labels (1 per week)

        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int day = (int) value;



                // Only label every 7th day
                if (day % 7 == 0) {


                    if(day != 0) {
                        LimitLine verticalLine = new LimitLine(day, "");
                        verticalLine.setLineColor(Color.RED);
                        verticalLine.setLineWidth(1f);
                        verticalLine.enableDashedLine(1f, 10f, 0f); // Optional: dashed style
                        xAxis.addLimitLine(verticalLine);
                    }
                    return "Week " + ((day/7) + 1); // or format as date if you want

                } else {
                    return ""; // empty to hide label
                }


            }
        });

        YAxis yAxis = weightLineChart.getAxisLeft();
        yAxis.setEnabled(false);

        weightLineChart.setScaleEnabled(false);

    }
    public void setLineData(LineData lineData){
        weightLineChart.setData(lineData);
    }

    public void invalidate() {
        weightLineChart.invalidate();
    }
}
