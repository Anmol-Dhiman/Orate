package com.example.orate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.orate.Activity.Fragments.UserProfile;
import com.example.orate.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String phoneNumber = getIntent().getStringExtra("PhoneNumber");

        Bundle bundle = new Bundle();
//TODO : we have to remove the phone Number
        bundle.putString("PhoneNumber", "9992514648");
        new UserProfile().setArguments(bundle);


//    TODO    here we have to show the menu items


//      TODO  and we have to call the javascript method to connect to the server
//as we are changing the fragments the activity will always exists
//so until and unless the user clens the app data from the storage we have to be still connected to the server


    }
}
