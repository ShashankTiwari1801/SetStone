package com.belphegor.setstone.ui_manager;

import android.os.CountDownTimer;
import android.view.LayoutInflater;

import com.belphegor.setstone.database_manager.DatabaseManager;
import com.belphegor.setstone.ui.HeatmapCalendar;
import com.belphegor.setstone.ui.HeatmapCalendarColumn;
import com.belphegor.setstone.util.DateManager;
import com.belphegor.setstone.util.ResourceManager;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

public class HeatMapCalendarManager {

    final HeatmapCalendar heatmapCalendar;
    final DatabaseManager databaseManager;
    final LayoutInflater layoutInflater;
    final DateManager dateManager;
    final ResourceManager resourceManager;

    final static int CALENDAR_DAY_LENGTH_BEFORE_TODAY = 400;
    final static int CALENDAR_DAY_LENGTH_AFTER_TODAY = 30;


    public HeatMapCalendarManager(HeatmapCalendar heatmapCalendar, DatabaseManager databaseManager, LayoutInflater layoutInflater, ResourceManager resourceManager) {

        this.heatmapCalendar = heatmapCalendar;
        this.databaseManager = databaseManager;
        this.layoutInflater = layoutInflater;
        this.resourceManager = resourceManager;
        this.dateManager = new DateManager();

        init();
    }



    LocalDate temp;
    boolean hasDisplayedFirstMonthName;
    int dayOfTheWeek = 0;
    int[] week_info;
    boolean isEnd = false;

    public void init(){

        LocalDate startDate = dateManager.getNDaysBefore(CALENDAR_DAY_LENGTH_BEFORE_TODAY);
        LocalDate endDate = dateManager.getNDaysAfter(CALENDAR_DAY_LENGTH_AFTER_TODAY);

        System.out.println(startDate);
        System.out.println(endDate);

        LocalDate start = startDate.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate end = endDate.with(TemporalAdjusters.lastDayOfMonth());


        List<String> dates =  databaseManager.getDatesBetween(startDate.toString() , endDate.toString());
//        System.out.println(dates);
//        System.out.println(dates.size());

        temp = start;

        week_info = new int[7];

        dayOfTheWeek = start.getDayOfWeek().getValue() - 1;

        hasDisplayedFirstMonthName = false;

        new CountDownTimer(1000,1){

            @Override
            public void onTick(long l) {
                if(temp.equals(end.plusDays(1))){isEnd = true; this.cancel();}

                LocalDate lastDate = temp.with(TemporalAdjusters.lastDayOfMonth());

                if(!hasDisplayedFirstMonthName){
                    hasDisplayedFirstMonthName = true;

                    int nextMonth = temp.getMonthValue() - 1;
                    week_info[0] = nextMonth * 100;

                    HeatmapCalendarColumn heatmapCalendarColumn = new HeatmapCalendarColumn(layoutInflater,
                            week_info, heatmapCalendar.getRoot(), resourceManager);

                    heatmapCalendar.addColumn(heatmapCalendarColumn);
                    heatmapCalendar.addWeekColumn(heatmapCalendarColumn.getWeekColumn());
                    week_info = new int[7];

                }

                if(dates.contains(temp.toString())){
                    week_info[dayOfTheWeek] = 2;
                }
                else{
                    week_info[dayOfTheWeek] = 1;
                }

                if(dayOfTheWeek == 6 || temp.equals(lastDate)){

                    HeatmapCalendarColumn heatmapCalendarColumn = new HeatmapCalendarColumn(layoutInflater,
                            week_info, heatmapCalendar.getRoot(), resourceManager);

                    heatmapCalendar.addColumn(heatmapCalendarColumn);


                    week_info = new int[7];
                }

                if(temp.equals(lastDate)){

                    int nextMonth = temp.getMonthValue();
                    week_info[0] = nextMonth * 100;

                    HeatmapCalendarColumn heatmapCalendarColumn = new HeatmapCalendarColumn(layoutInflater,
                            week_info, heatmapCalendar.getRoot(), resourceManager);

                    heatmapCalendar.addColumn(heatmapCalendarColumn);

                    week_info = new int[7];

                    heatmapCalendar.addWeekColumn(heatmapCalendarColumn.getWeekColumn());

                }

                temp = temp.plusDays(1);

                dayOfTheWeek += 1;
                dayOfTheWeek %= 7;
            }

            @Override
            public void onFinish() {
                if(!isEnd) {
                    this.start();
                }
            }
        }.start();


    }

    public void generateCalendarColumn(){

    }


}

/*
        while (!temp.equals(end.plusDays(1))) {

            LocalDate lastDate = temp.with(TemporalAdjusters.lastDayOfMonth());

            System.out.println(
                    temp.toString() + " | " + dates.contains(temp.toString())
            );

            if(!hasDisplayedFirstMonthName){
                hasDisplayedFirstMonthName = true;

                int nextMonth = temp.getMonthValue() - 1;
                System.out.println(temp + " | " + nextMonth);
                week_info[0] = nextMonth * 100;

                HeatmapCalendarColumn heatmapCalendarColumn = new HeatmapCalendarColumn(layoutInflater,
                        week_info, heatmapCalendar.getRoot(), resourceManager);

                heatmapCalendar.addColumn(heatmapCalendarColumn);
                heatmapCalendar.addWeekColumn(heatmapCalendarColumn.getWeekColumn());
                week_info = new int[7];

            }

            if(dates.contains(temp.toString())){
                week_info[dayOfTheWeek] = 2;
            }
            else{
                week_info[dayOfTheWeek] = 1;
            }

            if(dayOfTheWeek == 6 || temp.equals(lastDate)){
                System.out.println(Arrays.toString(week_info));

                HeatmapCalendarColumn heatmapCalendarColumn = new HeatmapCalendarColumn(layoutInflater,
                        week_info, heatmapCalendar.getRoot(), resourceManager);

                heatmapCalendar.addColumn(heatmapCalendarColumn);


                week_info = new int[7];
            }

            if(temp.equals(lastDate)){

                int nextMonth = temp.getMonthValue();
                System.out.println(temp + " | " + nextMonth);
                week_info[0] = nextMonth * 100;

                HeatmapCalendarColumn heatmapCalendarColumn = new HeatmapCalendarColumn(layoutInflater,
                        week_info, heatmapCalendar.getRoot(), resourceManager);

                heatmapCalendar.addColumn(heatmapCalendarColumn);

                week_info = new int[7];

                heatmapCalendar.addWeekColumn(heatmapCalendarColumn.getWeekColumn());

            }

            temp = temp.plusDays(1);

            dayOfTheWeek += 1;
            dayOfTheWeek %= 7;
        }

   */