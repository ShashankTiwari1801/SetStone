package com.belphegor.setstone.util;

import android.content.Context;

import com.belphegor.setstone.database_manager.DatabaseManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileToDatabaseExport {


    final DatabaseManager databaseManager;
    final public static String EXERCISE_DATA_QUERY_FILE_LOC = "erq.txt";
    final public static String WEIGHT_DATA_QUERY_FILE_LOC = "wrq.txt";
    final public static String SCHEDULE_DATA_QUERY_FILE_LOC = "srq.txt";

    final Context context;
    final static String EXERCISE_TABLE = "exercise_records";
    final static String WEIGHT_TABLE = "weight";
    final static String SCHEDULE_TABLE = "daily_routine";


    public FileToDatabaseExport(Context context){

        this.context = context;
        databaseManager = new DatabaseManager(context);

//        init();
    }

    public void init(){

        StringBuilder data = loadFile(EXERCISE_DATA_QUERY_FILE_LOC);
        String temp = data.toString();
        String[] qrys = temp.split("\n");

//        databaseManager.emptyTable(EXERCISE_TABLE);

        for(String qry : qrys){
//            databaseManager.executeInsertQry(qry);
        }

        data = loadFile(WEIGHT_DATA_QUERY_FILE_LOC);
        temp = data.toString();
        qrys = temp.split("\n");

//        databaseManager.emptyTable(WEIGHT_TABLE);
//
        for(String qry : qrys){
//            databaseManager.executeInsertQry(qry);
        }

        data = loadFile(SCHEDULE_DATA_QUERY_FILE_LOC);
        temp = data.toString();
        qrys = temp.split("\n");

//        databaseManager.emptyTable(WEIGHT_TABLE);
//
        for(String qry : qrys){
//            databaseManager.executeInsertQry(qry);
        }
//
//        databaseManager.describeTable("exercise_records");
//        databaseManager.describeTable("daily_routine");
//        databaseManager.describeTable("weight");

    }

    public StringBuilder loadFile(String fileName){
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream is = context.getAssets().open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            reader.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder;
    }


}
