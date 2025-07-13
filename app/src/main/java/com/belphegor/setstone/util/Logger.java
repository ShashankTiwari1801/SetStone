package com.belphegor.setstone.util;

import android.util.Log;

public class Logger {

    static StackTraceElement getLastClassName(){
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        StackTraceElement myCaller = stackTraceElements[4];
        return myCaller;
    }

    private static String[] getCallerInfo(StackTraceElement stackTraceElement){
        String className = "";
        String methodName = "";

        className = stackTraceElement.getClassName();
        methodName = stackTraceElement.getMethodName();

        String[] classSplit = className.split("\\.");

        className = classSplit[3];

        for (int i = 4; i < classSplit.length; i++) {
            className += "." + classSplit[i];
        }


        return new String[]{className, methodName};
    }

    public static void eLog(String data){
        StackTraceElement caller = getLastClassName();
        String[] callerInfo = getCallerInfo(caller);

        Log.e(callerInfo[0], callerInfo[1] + " | " + data);
    }
    public static void vLog(String data){
        StackTraceElement caller = getLastClassName();
        String[] callerInfo = getCallerInfo(caller);

        Log.v(callerInfo[0], callerInfo[1] + " | " + data);
    }
    public static void iLog(String data){
        StackTraceElement caller = getLastClassName();
        String[] callerInfo = getCallerInfo(caller);

        Log.i(callerInfo[0], callerInfo[1] + " | " + data);
    }
    public static void dLog(String data){
        StackTraceElement caller = getLastClassName();
        String[] callerInfo = getCallerInfo(caller);

        Log.d(callerInfo[0], callerInfo[1] + " | " + data);
    }

}
