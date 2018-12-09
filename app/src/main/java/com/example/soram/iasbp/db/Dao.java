package com.example.soram.iasbp.db;

import com.example.soram.iasbp.db.model.Connections;

import java.util.List;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@androidx.room.Dao
public interface Dao {

    @Query("SELECT * FROM connections")
    List<Connections> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Connections... connections);

    @Update()
    void updateTable(Connections connections);

    @Delete
    void delete(Connections connections);

    @Query("DELETE FROM connections")
    void nukeTable();


}
