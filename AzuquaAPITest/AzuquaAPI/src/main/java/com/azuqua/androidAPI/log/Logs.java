package com.azuqua.androidAPI.log;

import android.util.Log;

/**
 * Created by BALASASiDHAR on 25-Apr-15.
 */
public class Logs {

    public static void info(String string){
        Log.i("Azuqua",string);
    }

    public static void info(String tag,String string){
        Log.i(tag,string);
    }

    public static void error(String tag, String string) {
        Log.e(tag, string);
    }

    public static void error(String string) {
        Log.e("Azuqua", string);
    }
}
