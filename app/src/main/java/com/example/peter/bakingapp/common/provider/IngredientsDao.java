package com.example.peter.bakingapp.common.provider;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Peter on 11/02/2018.
 */

@Dao
public interface IngredientsDao {

    @Query("SELECT * FROM IngredientsData")
    List<IngredientsData> getAll();

    @Insert
    void insertAll(List<IngredientsData> ingredientsData);

    @Update
    void update(IngredientsData ingredientsData);

    @Delete
    void delete(IngredientsData ingredientsData);

    @Delete
    void deleteAll(List<IngredientsData> ingredientsData);

}
