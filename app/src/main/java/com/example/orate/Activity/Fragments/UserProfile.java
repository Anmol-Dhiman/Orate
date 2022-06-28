package com.example.orate.Activity.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.orate.DataModel.UserModel;
import com.example.orate.MainActivity;
import com.example.orate.R;
import com.example.orate.databinding.FragmentUserProfileBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class UserProfile extends Fragment {

    private FragmentUserProfileBinding binding;
    private FirebaseDatabase database;
    private String phoneNumber = "";
    private FirebaseStorage storage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentUserProfileBinding.inflate(inflater, container, false);

        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        phoneNumber = new MainActivity().getPhoneNumber();
        Log.d("main", "onCreateView: " + phoneNumber);

        return binding.getRoot();
    }


    public void setProfile() {
        if (phoneNumber != null) {
            database.getReference().child("User").child(phoneNumber).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    try {
                        UserModel user = snapshot.getValue(UserModel.class);
                        Glide.with(getContext())
                                .load(user.getImage())
                                .apply(RequestOptions.centerCropTransform())
                                .into(binding.profileImage);

                        binding.userNameProfileFragment.setText(user.getUserName());
                        binding.fullNameProfileFragment.setText(user.getFullName());
                        binding.aboutProfileFargment.setText(user.getAbout());

                        binding.userNameFragmentWithStatus.setText(user.getUserName());

                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Network issue.", Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(), "There is some issue with connectivity please try again.", Toast.LENGTH_LONG).show();
                }
            });
        } else
            Toast.makeText(getContext(), "There is some issue please try again.", Toast.LENGTH_SHORT).show();
    }
}