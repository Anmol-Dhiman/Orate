package com.example.orate.Activity.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.orate.Activity.SingInTimeActivities.Intro;
import com.example.orate.R;

import com.example.orate.databinding.FragmentUserProfileBinding;


public class UserProfile extends Fragment {

    private FragmentUserProfileBinding binding;
    private String imageUrl = null;
    private SharedPreferences preferences = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentUserProfileBinding.inflate(inflater, container, false);


        preferences = getActivity().getSharedPreferences("DATA", MODE_PRIVATE);
        imageUrl = preferences.getString("imageUrl", null);
        if (imageUrl != null) {
            Glide.with(getContext())
                    .load(imageUrl)
                    .apply(RequestOptions.centerCropTransform())
                    .into(binding.profileImage);
        } else {
            binding.profileImage.setImageResource(R.drawable.profile_fragment_image);
        }

        binding.userNameProfileFragment.setText(preferences.getString("userName", ""));
        binding.fullNameProfileFragment.setText(preferences.getString("userFullName", ""));
        binding.aboutProfileFargment.setText(preferences.getString("about", ""));
        binding.userNameFragmentWithStatus.setText(preferences.getString("userName", ""));


        binding.signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Do you want to sign out?")
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                singOut();

                            }
                        }).create().show();
            }
        });


        return binding.getRoot();
    }

    private void singOut() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("phoneNumber", "");
        editor.putString("imageUrl", null);
        editor.putString("userName", "");
        editor.putString("userFullName", "");
        editor.putString("about", "");
        SharedPreferences preferences1 = getContext().getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        SharedPreferences.Editor editor1 = preferences1.edit();
        editor1.putString("FirstTimeOpening", "yes");


//        we have to clear the call history

//                HistoryViewModel viewModel = MethodsHelperClass.getHelperMethods().historyViewModel;
//                viewModel.getHistory().observe(getViewLifecycleOwner(), new Observer<List<CallHistoryModel>>() {
//                    @Override
//                    public void onChanged(List<CallHistoryModel> callHistoryModels) {
//                        callHistoryModels.clear();
//                    }
//                });

        ((Activity) getContext()).finish();
        Toast.makeText(getContext(), "Sign out successfully.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getContext(), Intro.class));
    }


}