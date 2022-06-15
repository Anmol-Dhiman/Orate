package com.example.orate.Repository.RoomDatabase;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RoomDAO {


    @Insert
    void insert(CallHistoryModel callHistoryModel);

    @Query("SELECT * FROM callHistory ORDER BY id DESC")
    LiveData<List<CallHistoryModel>> getHistory();

}
