package com.example.orate.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.orate.DataModel.UserModel;
import com.example.orate.Repository.Firebase.FirebaseMethods;
import com.google.firebase.auth.FirebaseUser;


public class FirebaseAuthViewModel extends AndroidViewModel {

    private FirebaseMethods firebaseMethods;
    private MutableLiveData<FirebaseUser> liveData;


    public FirebaseAuthViewModel(@NonNull Application application) {
        super(application);

        firebaseMethods = new FirebaseMethods();
        liveData = firebaseMethods.getUserMutableLiveData();

    }

    public void register(UserModel user) {
        firebaseMethods.registerUser(user);
    }

    public MutableLiveData<FirebaseUser> getLiveData() {
        return liveData;
    }
}
