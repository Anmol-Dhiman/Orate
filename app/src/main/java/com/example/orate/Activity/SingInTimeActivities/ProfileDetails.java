package com.example.orate.Activity.SingInTimeActivities;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;

import com.example.orate.MainActivity;
import com.example.orate.ViewModel.FirebaseAuthViewModel;
import com.example.orate.databinding.ActivityProflieDetailsBinding;
import com.google.firebase.auth.FirebaseUser;


public class ProfileDetails extends AppCompatActivity {

    public static final int PERMISSION_CODE = 100;

    private ActivityProflieDetailsBinding binding;
    private ActivityResultLauncher<String> launcher;
    private FirebaseAuthViewModel firebaseAuthViewModel;
    private String imageUri = null;
    private String phoneNumber = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProflieDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        phoneNumber = getIntent().getStringExtra("PhoneNumber");




        firebaseAuthViewModel = new ViewModelProvider(this).get(FirebaseAuthViewModel.class);
        firebaseAuthViewModel.getLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null)
                    Toast.makeText(ProfileDetails.this, "User has been created!", Toast.LENGTH_SHORT).show();
//                intent have to be started here
                startActivity(new Intent(ProfileDetails.this, MainActivity.class).putExtra("PhoneNumber", phoneNumber));
            }
        });

        binding.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = binding.userName.getText().toString().trim();
                String userFullName = binding.userFullName.getText().toString().trim();
                String about = binding.aboutSection.getText().toString().trim();


                if (userName.isEmpty() || userFullName.isEmpty()) {
                    Toast.makeText(ProfileDetails.this, "Enter the details....", Toast.LENGTH_SHORT).show();
                } else {
                    if (about.isEmpty()) {
                        about = "Hey there I'm using Orate!";
                    }
                    if (imageUri != null) {
                        firebaseAuthViewModel.register(userName, about, userFullName, phoneNumber, imageUri);
                    } else
                        Toast.makeText(ProfileDetails.this, "Set Profile Image..", Toast.LENGTH_SHORT).show();

                }


            }
        });


        binding.profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermission()) {
                    launcher.launch("image/*");
                } else
                    requestPermission();

            }
        });

        launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                imageUri = "" + result;
                binding.profilePicture.setImageURI(result);
            }
        });
    }

    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(ProfileDetails.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(ProfileDetails.this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(ProfileDetails.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.INTERNET}, PERMISSION_CODE);
    }


}