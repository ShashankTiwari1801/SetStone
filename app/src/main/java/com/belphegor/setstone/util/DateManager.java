package com.belphegor.setstone.util;

import java.time.LocalDate;

public class DateManager {

    final LocalDate TODAY;

    public LocalDate firstDayOfTheWeek;
    public LocalDate lastDayOfTheWeek;

    int currentDayOfTheWeek = 0;

    public DateManager(){
        this.TODAY = LocalDate.now();
        init();

    }

    public void init(){

        currentDayOfTheWeek = TODAY.getDayOfWeek().getValue()-1;

        getFirstDateOfTheWeek();
        getLastDateOfTheWeek();
    }

    public LocalDate getFirstDateOfTheWeek(){
        int currDayOfWeek = TODAY.getDayOfWeek().getValue();
        firstDayOfTheWeek = TODAY.minusDays(currDayOfWeek - 1);
        return firstDayOfTheWeek;
    }

    public LocalDate getLastDateOfTheWeek(){
        lastDayOfTheWeek = firstDayOfTheWeek.plusDays(6);
        return lastDayOfTheWeek;
    }

    public int[] getWeekDateList(){

        int[] res = new int[7];

        for (int i = 0; i < 7; i++) {
            LocalDate temp = firstDayOfTheWeek.plusDays(i);
            res[i] = temp.getDayOfMonth();
        }

        return res;
    }

    public LocalDate getFirstDayOfTheMonth(){
        return TODAY.minusDays(TODAY.getDayOfMonth() - 1);
    }
    public LocalDate getLastDayOfTheMonth(){
        return getFirstDayOfTheMonth().plusDays(LocalDate.now().lengthOfMonth()).minusDays(1);
    }

    public LocalDate getFirstDayOfTheYear(){
        int year = TODAY.getYear();
        return LocalDate.of(year, 1, 1);
    }
    public LocalDate getLastDayOfTheYear(){
        int year = TODAY.getYear();
        return LocalDate.of(year, 12, 31);
    }

    public LocalDate getFirstDayOfEternity(){
        int year = TODAY.getYear();
        return LocalDate.of(2000, 1, 1);
    }

    public LocalDate getLastDayOfEternity(){
        int year = TODAY.getYear();
        return LocalDate.of(2100, 12, 31);
    }


    public int getCurrentDayOfTheWeek() {
        return currentDayOfTheWeek;
    }

    public LocalDate getNDaysBefore(int n){
        return TODAY.minusDays(n);
    }

    public LocalDate getNDaysAfter(int n){
        return TODAY.plusDays(n);
    }

}
