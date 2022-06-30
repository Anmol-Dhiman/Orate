package com.example.orate.Activity.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.orate.Activity.Adapter;
import com.example.orate.Activity.SingInTimeActivities.ProfileDetails;
import com.example.orate.DataModel.UserModel;
import com.example.orate.MainActivity;
import com.example.orate.MethodHelperClasses.ContactListHelper;
import com.example.orate.databinding.FragmentContactListBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ContactList extends Fragment {

    private FragmentContactListBinding binding;
    private ContactListHelper contactListHelper;


    private ActivityResultLauncher<String> mPermissionResult = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {
                    if (result) {

                        contactListHelper = ContactListHelper.getContactListHelper();
                        setContactListHelperClass();
                        contactListHelper.setContactListAdapter();
                    } else {

                        new AlertDialog.Builder(getContext()).setTitle("Need contact permission to show the contacts")
                                .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mPermissionResult.launch(Manifest.permission.READ_CONTACTS);
                                    }
                                }).setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(getContext(), "We can not show the contacts without permission.", Toast.LENGTH_LONG).show();
                                    }
                                }).create().show();
                    }
                }
            });


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mPermissionResult.launch(Manifest.permission.READ_CONTACTS);
        binding = FragmentContactListBinding.inflate(inflater, container, false);

//TODO implement searchView
        return binding.getRoot();
    }


    public void setContactListHelperClass() {
        contactListHelper.setContactListContext(getContext());
        contactListHelper.setBinding(binding);
        contactListHelper.setPhoneNumber(getContext().getSharedPreferences("DATA", Context.MODE_PRIVATE).getString("phoneNumber", ""));
    }
}