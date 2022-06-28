package com.example.orate.Activity.SingInTimeActivities;

import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.FileObserver;
import android.util.Log;
import android.view.View;

import com.example.orate.Repository.Firebase.FirebaseMethods;
import com.example.orate.databinding.ActivityIntroBinding;

public class Intro extends AppCompatActivity {

    private ActivityIntroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        SharedPreferences preferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
//        String introFirstTime = preferences.getString("introFirstTime", "");


//        if (firstTime.equals("no")) {
//            Log.d("main", "onCreate: intro more than first time code");
//            startActivity(new Intent(Intro.this, MainActivity.class));
//            Intro.this.finish();
//        } else {
//            SharedPreferences.Editor editor = preferences.edit();
//            editor.putString("introFirstTime", "no");
//            editor.apply();
//
//            Log.d("main", "onCreate: intro first time code");
//            binding.getStartedButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    startActivity(new Intent(Intro.this, PhoneNumberInput.class));
//                    Intro.this.finish();
//                }
//            });
//        }

        Log.d("main", "onCreate: intro activity button on click");
        binding.getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intro.this, PhoneNumberInput.class));
                finish();
            }
        });

    }


    @Override
    public void onBackPressed() {
        FirebaseMethods.showExitDialog(this);
    }
}