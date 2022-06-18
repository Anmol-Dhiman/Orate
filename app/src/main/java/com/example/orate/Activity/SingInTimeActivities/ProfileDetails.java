package com.example.orate.Activity.SingInTimeActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.orate.R;
import com.example.orate.databinding.ActivityProflieDetailsBinding;

public class ProfileDetails extends AppCompatActivity {


    private ActivityProflieDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProflieDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = binding.userName.getText().toString().trim();
                String userFullName = binding.userFullName.getText().toString().trim();
                String about = binding.aboutSection.getText().toString().trim();
                String phoneNumber = getIntent().getStringExtra("PhoneNumber");


            }
        });


    }
}