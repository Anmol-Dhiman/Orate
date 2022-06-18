package com.example.orate.Activity.SingInTimeActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.orate.R;
import com.example.orate.databinding.ActivityIntroBinding;

public class Intro extends AppCompatActivity {

    private ActivityIntroBinding binding;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intro.this, PhoneNumberInput.class));
            }
        });
    }
}