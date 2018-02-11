package com.example.peter.bakingapp.common.provider;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by Peter on 11/02/2018.
 */

@Database(entities = {IngredientsData.class}, version = 1)
public abstract class IngredientsDatabase extends RoomDatabase {
    public abstract IngredientsDao ingredientsDao();
}
