package com.example.peter.bakingapp.common.helpers;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.peter.bakingapp.common.provider.IngredientsDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static com.example.peter.bakingapp.common.helpers.Constants.INGREDIENTS_DATABASE_NAME;


public class BakingApp extends Application {

    private static BakingApp bakingAppInstance;
    private Gson mGson;
    private IngredientsDatabase ingredientsDatabase;

    public static BakingApp getBakingAppInstance() {
        return bakingAppInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        bakingAppInstance = this;
        mGson = new GsonBuilder().create();
        ingredientsDatabase = Room.databaseBuilder(getApplicationContext(), IngredientsDatabase.class, INGREDIENTS_DATABASE_NAME).allowMainThreadQueries().build();
    }


    public Gson getmGson() {
        return mGson;
    }

    public IngredientsDatabase getIngredientsDatabase() {
        return ingredientsDatabase;
    }
}
