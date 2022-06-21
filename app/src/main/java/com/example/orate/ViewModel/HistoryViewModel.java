package com.example.orate.ViewModel;

import android.app.Application;
import android.telecom.Call;

import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.orate.Repository.RepoLiveDataRoom;
import com.example.orate.Repository.RoomDatabase.CallHistoryModel;

import java.util.List;

public class HistoryViewModel extends AndroidViewModel {

    private RepoLiveDataRoom repoLiveDataRoom;
    private LiveData<List<CallHistoryModel>> history;


    public HistoryViewModel(@Nullable Application application) {
        super(application);
        repoLiveDataRoom = new RepoLiveDataRoom(application);
        history = repoLiveDataRoom.getHistoryLiveData();
    }

    public LiveData<List<CallHistoryModel>> getHistory() {
        return history;
    }

    public void insert(CallHistoryModel callHistoryModel) {
        repoLiveDataRoom.insert(callHistoryModel);
    }
}
