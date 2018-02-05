package com.example.peter.bakingapp.common.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import static com.example.peter.bakingapp.common.helpers.Constants.BAKING_APP_PREF;

/**
 * Created by Peter on 11/01/2018.
 */

public class AppPreferences {
    private static AppPreferences appPreferences;
    private SharedPreferences sharedPreferences;

    public static AppPreferences getInstance(Context context) {
        if (appPreferences == null) {
            appPreferences = new AppPreferences(context);
        }
        return appPreferences;
    }

    private AppPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(BAKING_APP_PREF, Context.MODE_PRIVATE);
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putInt(key, value);
        prefsEditor.apply();
    }

    public int getInt(String key) {
        if (sharedPreferences != null) {
            return sharedPreferences.getInt(key, -1);
        }
        return -1;

    }
}
