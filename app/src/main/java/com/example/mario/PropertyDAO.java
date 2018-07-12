package com.example.mario;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface PropertyDAO {

    @Insert
    void insert(IncompleteProperty prop);

    @Query("DELETE FROM properties")
    void deleteAll();

    @Query("SELECT * FROM properties")
    LiveData<List<IncompleteProperty>> getAllIncompleteProperties();
}
