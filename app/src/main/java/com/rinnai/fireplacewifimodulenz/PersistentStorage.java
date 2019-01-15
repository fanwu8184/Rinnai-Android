package com.rinnai.fireplacewifimodulenz;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by cconci on 22/03/2018.
 */

public class PersistentStorage {

    /*
        Store Value
    */
    public static boolean StoreValueString(Context context, String sharedPrefrenceKey, String sharedPrefrenceID, String value)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPrefrenceKey, Activity.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(sharedPrefrenceID, value);

        return editor.commit();
    }

    public static boolean StoreValueInt(Context context, String sharedPrefrenceKey, String sharedPrefrenceID, int value)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPrefrenceKey, Activity.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putInt(sharedPrefrenceID, value);

        return editor.commit();
    }

    public static boolean StoreValueBoolean(Context context, String sharedPrefrenceKey, String sharedPrefrenceID, boolean value)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPrefrenceKey, Activity.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putBoolean(sharedPrefrenceID, value);

        return editor.commit();
    }

    /*
        Retrieve Stored Value
    */

    public static String RetrieveStoredValueString(Context context,String sharedPrefrenceKey,String sharedPrefrenceID, String defaultValue)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPrefrenceKey, Activity.MODE_PRIVATE);

        String value = sharedPref.getString(sharedPrefrenceID, defaultValue);

        return value;

    }

    public static int RetrieveStoredValueInt(Context context,String sharedPrefrenceKey,String sharedPrefrenceID, int defaultValue)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPrefrenceKey, Activity.MODE_PRIVATE);

        int value = sharedPref.getInt(sharedPrefrenceID, defaultValue);

        return value;

    }

    public static boolean RetrieveStoredValueBoolean(Context context,String sharedPrefrenceKey,String sharedPrefrenceID, boolean defaultValue)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPrefrenceKey, Activity.MODE_PRIVATE);

        boolean value = sharedPref.getBoolean(sharedPrefrenceID, defaultValue);

        return value;

    }

}
