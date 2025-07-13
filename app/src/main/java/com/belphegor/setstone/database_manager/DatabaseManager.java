package com.belphegor.setstone.database_manager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.belphegor.setstone.Exercise;
import com.belphegor.setstone.util.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DatabaseManager {
    Context context;
    public static final String DATABASE_NAME = "WorkoutData.db";
    public static final int DATABASE_VERSION = 1;
    DatabaseHelper databaseHelper;
    SQLiteDatabase database;

    /*
    TABLE SCHEMA
     */
    public class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }


    public DatabaseManager(Context context) {
        this.context = context;
        init();
    }

    public void createAllTables(){
        String DAILY_ROUTINE_INIT_QRY = "CREATE TABLE IF NOT EXISTS daily_routine (id INTEGER PRIMARY KEY AUTOINCREMENT, day_of_week INTEGER CHECK(day_of_week BETWEEN 0 AND 6), exercise_id INTEGER);";
        String WEIGHT_INIT_QRY = "CREATE TABLE IF NOT EXISTS weight (id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, weight REAL);";
        String EXERCISE_RECORD_INIT_QRY = "CREATE TABLE IF NOT EXISTS exercise_records (id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, exercise_id TEXT, weight REAL, reps INTEGER);";

        executeQRY(DAILY_ROUTINE_INIT_QRY);
        executeQRY(WEIGHT_INIT_QRY);
        executeQRY(EXERCISE_RECORD_INIT_QRY);
    }

    public void init() {

        Logger.dLog("Initialising database manager");

        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();

        createAllTables();
    }

    private List<List<String>> executeQRYForResults(String QUERY) {

        List<List<String>> res = new ArrayList<>();

        Cursor cursor = null;
        try {
            cursor = database.rawQuery(QUERY, null);
            if (cursor.moveToFirst()) {
                do {
                    int col_length = cursor.getColumnCount();

                    List<String> temp = new ArrayList<>();

                    for (int i = 0; i < col_length; i++) {
                        temp.add(cursor.getString(i));
                    }

                    res.add(temp);

                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) cursor.close();
        }

        return res;
    }

    private void executeQRY(String QUERY) {

        database.execSQL(QUERY);

    }

    public void LogList(List<List<String>> records) {

        for (List<String> row : records) {

            Logger.dLog(row.toString());

        }

    }

    public void listAllTables() {

        Logger.iLog("Listing all tables in the database");

        String QRY = "SELECT name FROM sqlite_master WHERE type='table';";

        List<List<String>> res = executeQRYForResults(QRY);

        LogList(res);

    }

    public void printTable(String tableName) {

        Logger.iLog(String.format("Printing contents of the table [%s]", tableName));

        String QRY = String.format("SELECT * FROM %s", tableName);

        LogList(executeQRYForResults(QRY));

    }

    public List<List<String>> getExerciseOnDay(int day_id) {

        Logger.iLog(String.format("Getting exercise list for the day %d", day_id));

        String QUERY = String.format("SELECT exercise_id FROM daily_routine WHERE day_of_week = %d", day_id);

        List<List<String>> res = executeQRYForResults(QUERY);

        return res;
    }

    public void describeTable(String tableName) {
        Logger.iLog(String.format("Describing table %s", tableName));
        String QRY = String.format("PRAGMA table_info(%s)", tableName);
        LogList(executeQRYForResults(QRY));
    }

    public List<String> getDatesBetween(String startDate, String endDate) {

        String QRY = String.format("SELECT DISTINCT date " +
                "FROM exercise_records " +
                "WHERE date >= '%s' AND date <= '%s' " +
                "ORDER BY date", startDate, endDate);

        List<List<String>> res = executeQRYForResults(QRY);

        List<String> RES = new ArrayList<>();

        for (List<String> list : res) {
            RES.addAll(list);
        }

        return RES;
    }

    public List<List<String>> getRecordBetween(String startDate, String endDate) {

        String QRY = String.format("SELECT * " +
                "FROM exercise_records " +
                "WHERE date >= '%s' AND date <= '%s' " +
                "ORDER BY date", startDate, endDate);

        List<List<String>> res = executeQRYForResults(QRY);

        return res;
    }

    public Float getExercisePersonalBest(int exercise_id) {
        float res = 0f;


        Logger.iLog("Getting personal best record");

        String QRY = String.format("SELECT MAX(weight) " +
                "FROM exercise_records " +
                "WHERE exercise_id = %d", exercise_id);

        List<List<String>> data = executeQRYForResults(QRY);

        System.out.println(data);

        try {
            res = Float.parseFloat(data.get(0).get(0));
        }
        catch (Exception e){
            Log.e("EXCEPTION", e.toString());
        }

        Logger.dLog(String.format("personal best record = %.2f for exercise_id = %d", res, exercise_id));

        return res;
    }

    public List<Exercise> getLastNSetDetailsForExercise(int exercise_id, int N) {

        Logger.dLog(String.format("Getting last %d record for the exercise %d", N, exercise_id));

        String QRY = String.format("SELECT * " +
                "FROM exercise_records " +
                "WHERE exercise_id = %d " +
                "ORDER BY id DESC " +
                "LIMIT %d", exercise_id, N);


        List<Exercise> res = new ArrayList<>();

        List<List<String>> temp_res = executeQRYForResults(QRY);

        for (List<String> list : temp_res) {
            res.add(new Exercise(list));
        }

        return res;
    }

    public void addExerciseRecordToTable(String date, float weight, int reps, int exercise_id) {
        /*
        INSERT INTO exercise_records (date, exercise_id, weight, reps) VALUES ('2025-02-03', 158, 47.5, 10)
         */

        Logger.dLog(String.format("Adding exercise record (date, exercise_id, weight, reps)('%s', %d, %.2f, %d)", date, exercise_id, weight, reps));

        String QRY = String.format("INSERT INTO exercise_records (date, exercise_id, weight, reps) VALUES ('%s', %d, %.2f, %d)", date, exercise_id, weight, reps);

        executeQRY(QRY);

    }

    public void removeExerciseRecord(int rowId) {

        Logger.dLog(String.format("Removing exercise_record with ID = %d", rowId));

        String query = String.format("DELETE FROM exercise_records WHERE id = %d", rowId);
        System.out.println(query);
        database.execSQL(query);

    }

    public Float getCurrentBodyWeight() {

        Logger.iLog("Getting the current body weight");
        Float res = 0f;

        String QRY = "SELECT weight FROM weight ORDER BY id DESC LIMIT 1";

        List<List<String>> data = executeQRYForResults(QRY);

        try{
            res = Float.parseFloat(data.get(0).get(0));
        }
        catch (Exception e){
            Log.e("ERROR", e.toString());
            return 60f;
        }

        Logger.dLog(String.format("Current body weight = %.2f" , res));

        return res;
    }

    public void emptyTable(String table_name){
        String QRY = String.format("DELETE FROM %s", table_name);
        Log.e("EE", "DELETED TABLE " + table_name);
//        executeQRY(QRY);
    }

    public void executeInsertQry(String QRY){
        Log.v("EXECUTING", QRY);
        executeQRY(QRY);
        Log.i("SUCCESS", "SUCCESSFULLY UPDATED");
    }

    public List<Integer> getExerciseListForDay(int day_id){
        List<List<String>> data = getExerciseOnDay(day_id);
        List<Integer> res = new ArrayList<>();
        for (List<String> row: data){
            res.add(Integer.parseInt((row.get(0))));
        }
        return res;
    }

    public void addExercisesToDay(int dayID, Set<Integer> exerciseIds){
        String deleteQry = String.format("DELETE FROM daily_routine where day_of_week = %d", dayID);
        Logger.eLog(deleteQry);

        executeQRY(deleteQry);
        for(int id : exerciseIds){
            String insertQry = String.format("INSERT INTO daily_routine (day_of_week, exercise_id) VALUES (%d, %d);", dayID, id);
            executeQRY(insertQry);
            Logger.iLog(insertQry);
        }

    }

    public void removeExerciseForDay(int dayId, int exercise_id){
        String QRY = String.format("DELETE FROM daily_routine WHERE day_of_week = %d AND exercise_id = %d", dayId, exercise_id);
        executeQRY(QRY);
    }

    public List<List<String>> getLastNWeightRecord(int N){
        String QRY = String.format("SELECT date, weight FROM weight ORDER BY id DESC LIMIT %d", N);

        return executeQRYForResults(QRY);
    }

    public void addWeightRecordToTable(String date, float weight){

        String GET_QRY = String.format("SELECT COUNT(*) FROM weight where date = '%s'", date);

        if(Integer.parseInt(executeQRYForResults(GET_QRY).get(0).get(0)) == 0){
            String QRY = String.format("INSERT INTO weight (date, weight) VALUES ('%s', %.2f);", date, weight);
            executeQRY(QRY);
        }
        else{
            String UPDATE_QRY = String.format("UPDATE weight SET weight = %.2f WHERE date = '%s';", weight, date);
            executeQRY(UPDATE_QRY);
            Logger.dLog("Updated Weight record for date " + date);
        }

    }

    public void createUserTable(){
        String QRY = "CREATE TABLE IF NOT EXISTS user_info (name TEXT, height REAL, age INTEGER);";
        executeQRY(QRY);
    }

    public void updateUserTable(String name, float height, int age) {

        String query = String.format("INSERT INTO user_info (name, height, age) VALUES ('%s', %.2f, %d);", name, height, age);
        executeQRY(query);

    }

    public List<List<String>> getUserRecord(){
        String QRY = "SELECT * FROM user_info ORDER BY rowid DESC LIMIT 1;";
        return executeQRYForResults(QRY);
    }
}
