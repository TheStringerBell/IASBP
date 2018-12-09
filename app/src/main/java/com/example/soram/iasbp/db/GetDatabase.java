package com.example.soram.iasbp.db;

import android.content.Context;

import com.example.soram.iasbp.db.model.Connections;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities =  {Connections.class}, version = 1)
public abstract class GetDatabase extends RoomDatabase {

    private static GetDatabase INSTANCE;

    public abstract Dao testDao();


    public static  GetDatabase getAppDatabase(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), GetDatabase.class, "conn_db")

                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance(){
        INSTANCE = null;
    }
}
