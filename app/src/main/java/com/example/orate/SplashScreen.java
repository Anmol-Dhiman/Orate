package com.example.orate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.example.orate.Activity.SingInTimeActivities.Intro;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        SharedPreferences preferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        String firstTime = preferences.getString("FirstTimeOpening", "yes");
//        if the app is installed for the first time in device then this code will run
        if (firstTime.equals("yes")) {
            SharedPreferences.Editor editor = preferences.edit();
            Log.d("main", "before change: " + preferences.getString("FirstTimeOpening", "yes"));
            editor.putString("FirstTimeOpening", "no").apply();

            Log.d("main", "after change: " + preferences.getString("FirstTimeOpening", ""));

            Log.d("main", "onCreate: first time code");

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    /* Create an Intent that will start the Menu-Activity. */
                    startActivity(new Intent(SplashScreen.this, Intro.class));
                    SplashScreen.this.finish();
                }
            }, 2000);


        }
//        else this code will execute
        else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    /* Create an Intent that will start the Menu-Activity. */
                    Log.d("main", "onCreate:more than first time code");
                    Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
                    SplashScreen.this.startActivity(mainIntent);
                    SplashScreen.this.finish();
                }
            }, 2000);
        }

    }

}