package com.example.orate.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.orate.Repository.RoomDatabase.CallHistoryModel;

import java.util.List;

public class FirebaseAuthViewModel extends AndroidViewModel {


    public FirebaseAuthViewModel(@NonNull Application application) {
        super(application);
    }

}
