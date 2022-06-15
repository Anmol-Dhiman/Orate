package com.example.orate.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.telecom.Call;

import androidx.lifecycle.LiveData;

import com.example.orate.Repository.RoomDatabase.CallHistoryModel;
import com.example.orate.Repository.RoomDatabase.DataBase;
import com.example.orate.Repository.RoomDatabase.RoomDAO;

import java.util.List;

public class RepoLiveDataRoom {
    private RoomDAO roomDAO;
    private LiveData<List<CallHistoryModel>> historyLiveData;


    public RepoLiveDataRoom(Application application) {
        DataBase dataBase = DataBase.getInstance(application);
        roomDAO = dataBase.roomDAO();
        historyLiveData = roomDAO.getHistory();
    }

    public void insert(CallHistoryModel callHistoryModel) {
        new InsertHistory(roomDAO).execute(callHistoryModel);
    }

    public LiveData<List<CallHistoryModel>> getHistoryLiveData() {
        return historyLiveData;
    }

    private static class InsertHistory extends AsyncTask<CallHistoryModel, Void, Void> {

        private RoomDAO roomDAO;

        private InsertHistory(RoomDAO roomDAO) {
            this.roomDAO = roomDAO;
        }

        @Override
        protected Void doInBackground(CallHistoryModel... notes) {
            roomDAO.insert(notes[0]);
            return null;
        }
    }
}




