package com.example.orate.Activity.SingInTimeActivities;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import androidx.appcompat.app.AppCompatActivity;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.net.Uri;
import android.os.Bundle;

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


    private ActivityProflieDetailsBinding binding;
    private ActivityResultLauncher<String> launcher;
    private FirebaseAuthViewModel firebaseAuthViewModel;
    private FirebaseStorage storage = null;
    private String imageUri = null;
    private String phoneNumber = null;
    private String userName = null;
    private String userFullName = null;
    private String about = null;

    private ActivityResultLauncher<String> mPermissionResult = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {
                    if (result) {
                        launcher.launch("image/*");
                    } else {
                        new AlertDialog.Builder(ProfileDetails.this)
                                .setTitle("Permission Required to get the Image")
                                .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mPermissionResult.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                                    }
                                }).setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(ProfileDetails.this, "You need to set the profile image", Toast.LENGTH_LONG).show();
                                    }
                                }).create().show();
                    }
                }
            });


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

                editor.putString("userFullName", userFullName).apply();
                editor.putString("imageUrl", imageUri).apply();
                editor.putString("about", about).apply();


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
                mPermissionResult.launch(Manifest.permission.READ_EXTERNAL_STORAGE);

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
                                    firebaseAuthViewModel.register(new UserModel(userName, imageUri, about, userFullName, phoneNumber, "false", "false", "false"));

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


    @Override
    public void onBackPressed() {
        FirebaseMethods.showExitDialog(this);
    }
}