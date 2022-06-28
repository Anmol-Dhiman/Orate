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
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.orate.DataModel.UserModel;
import com.example.orate.MainActivity;
import com.example.orate.Repository.Firebase.FirebaseMethods;
import com.example.orate.ViewModel.FirebaseAuthViewModel;
import com.example.orate.databinding.ActivityProflieDetailsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class ProfileDetails extends AppCompatActivity {

    public static final int PERMISSION_CODE = 100;

    private ActivityProflieDetailsBinding binding;
    private ActivityResultLauncher<String> launcher;
    private FirebaseAuthViewModel firebaseAuthViewModel;
    private FirebaseStorage storage = null;
    private String imageUri = null;
    private String phoneNumber = null;
    private String userName = null;
    private String userFullName = null;
    private String about = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProflieDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        storage = FirebaseStorage.getInstance();

        SharedPreferences preferences = getSharedPreferences("DATA", MODE_PRIVATE);
        phoneNumber = preferences.getString("phoneNumber", "");


        firebaseAuthViewModel = new ViewModelProvider(this).get(FirebaseAuthViewModel.class);
        firebaseAuthViewModel.getLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null)
                    Toast.makeText(ProfileDetails.this, "User has been created!", Toast.LENGTH_SHORT).show();
//here we will save the data of use locally


                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("userName", userName).apply();
                Log.d("main", "" + userName);
                editor.putString("userFullName", userFullName).apply();
                Log.d("main", "" + userFullName);
                editor.putString("imageUrl", imageUri).apply();
                Log.d("main", "" + imageUri);
                editor.putString("about", about).apply();
                Log.d("main", "" + about);


//                intent have to be started here
                startActivity(new Intent(ProfileDetails.this, MainActivity.class));
                finish();
            }
        });

        binding.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userDataEntry();
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


    private void userDataEntry() {

        userName = binding.userName.getText().toString().trim();
        userFullName = binding.userFullName.getText().toString().trim();
        about = binding.aboutSection.getText().toString().trim();


        if (userName.isEmpty() || userFullName.isEmpty()) {
            Toast.makeText(ProfileDetails.this, "Enter the details....", Toast.LENGTH_SHORT).show();
        } else {
            if (about.isEmpty()) {
                about = "Hey there I'm using Orate!";
            }
            if (imageUri != null) {
//                        here we will get the uri store the user profile image to firebase storage and get the download link
                if (storage != null) {
                    StorageReference reference = storage.getReference().child("Profile Image").child(phoneNumber);
                    reference.putFile(Uri.parse(imageUri)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    imageUri = uri.toString();
                                    firebaseAuthViewModel.register(new UserModel(userName, imageUri, about, userFullName, phoneNumber, "false", "none", "false"));

                                }
                            });
                        }
                    });
                } else {
                    imageUri = null;
                    Toast.makeText(this, "Connectivity issue.", Toast.LENGTH_SHORT).show();
                }

            } else
                Toast.makeText(ProfileDetails.this, "Set Profile Image..", Toast.LENGTH_SHORT).show();

        }

    }


    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(ProfileDetails.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(ProfileDetails.this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(ProfileDetails.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.INTERNET}, PERMISSION_CODE);
    }


    @Override
    public void onBackPressed() {
        FirebaseMethods.showExitDialog(this);
    }
}