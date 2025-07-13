package com.belphegor.setstone.ui_manager;

import android.view.View;

import com.belphegor.setstone.ui.WeekDay;
import com.belphegor.setstone.ui.WeekRow;
import com.belphegor.setstone.util.DateManager;

import java.util.List;

public class WeekRowManager {

    final WeekRow weekRow;
    final DateManager dateManager;
    final DailyExerciseViewerManager dailyExerciseViewerManager;

    final static String[] dayVals = new String[]{"Mo", "Tu", "We", "Th", "Fr", "Sa", "Su"};
    final static String[] dayVals2 = new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};


    List<WeekDay> weekDayList;
    int[] weekDateArr;

    int dayOfTheWeek;
    int last_selected = -1;

    public WeekRowManager(WeekRow weekRow, DailyExerciseViewerManager dailyExerciseViewerManager){
        this.weekRow = weekRow;
        this.dailyExerciseViewerManager = dailyExerciseViewerManager;
        this.dateManager = new DateManager();

        init();
    }

    public void init(){

        weekDayList = weekRow.getWeekDays();
        weekDateArr = dateManager.getWeekDateList();
        dayOfTheWeek = dateManager.getCurrentDayOfTheWeek();

        dailyExerciseViewerManager.loadExerciseForDayID(dayOfTheWeek);
        updateWeekDayRow();
    }


    public View.OnClickListener getOnClickAction(final int I){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dailyExerciseViewerManager.loadExerciseForDayID(I);

                if(last_selected == -1){

                    last_selected = I;
                    weekDayList.get(I).select();

                }
                else{

                    weekDayList.get(last_selected).unselect();

                    if(last_selected != I){
                        weekDayList.get(I).select();
                        last_selected = I;
                    }
                    else{
                        dailyExerciseViewerManager.loadExerciseForDayID(dayOfTheWeek);
                        last_selected = -1;
                    }



                }
            }
        };
    }

    public void updateWeekDayRow(){
        int i = 0;
        for(WeekDay weekDay: weekDayList){
            weekDay.setDayDate(String.valueOf(weekDateArr[i]));
            weekDay.setDayName(String.valueOf(dayVals[i]));

            if(i == dayOfTheWeek){
                weekDay.highlight();
            }

            weekDay.setOnClickListener(getOnClickAction(i));

            i++;
        }
    }
}
