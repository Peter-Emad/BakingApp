package com.example.peter.bakingapp.common.helpers;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class BakingApp extends Application {

    private static Gson mGson;

    @Override
    public void onCreate() {
        super.onCreate();
        mGson = new GsonBuilder().create();
    }


    public static Gson getmGson() {
        return mGson;
    }


}
