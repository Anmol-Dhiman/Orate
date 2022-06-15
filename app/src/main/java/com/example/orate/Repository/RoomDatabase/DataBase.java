package com.example.orate.Repository.RoomDatabase;


import android.content.Context;

import androidx.room.Database;

import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {CallHistoryModel.class}, version = 1)
public abstract class DataBase extends RoomDatabase {

    private static DataBase instance;
    public abstract RoomDAO roomDAO();
    public static synchronized DataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext()
                            , DataBase.class, "CallHistoryDataBase")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }

}
