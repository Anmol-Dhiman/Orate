package com.example.orate.Activity.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.orate.Activity.Adapter;
import com.example.orate.Activity.SingInTimeActivities.ProfileDetails;
import com.example.orate.DataModel.UserModel;
import com.example.orate.MainActivity;
import com.example.orate.MethodHelperClasses.ContactListHelper;
import com.example.orate.databinding.FragmentContactListBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ContactList extends Fragment {

    public static final int CONTACT_LIST_ACTIVITY_CODE = 1;
    private FragmentContactListBinding binding;
    private Adapter adapter;
    private ContactListHelper contactListHelper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        requestContactPermission();
        binding = FragmentContactListBinding.inflate(inflater, container, false);


        if (checkContactPermission()) {
            contactListHelper = ContactListHelper.getContactListHelper();
            setContactListHelperClass();
            contactListHelper.setContactListAdapter();
        } else {
            requestContactPermission();
//            we have to put it in the  onResponse of request function
            contactListHelper = ContactListHelper.getContactListHelper();
            setContactListHelperClass();
            contactListHelper.setContactListAdapter();
        }


        //
//
////TODO implement searchView
//
////        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
////            @Override
////            public boolean onQueryTextSubmit(String query) {
////                if (contactsList.contains(query)) {
////                    adapter.getFilter().filter(query);
////                } else {
////                    Toast.makeText(container.getContext(), "No Match found", Toast.LENGTH_LONG).show();
////                }
////                return false;
////
////            }
////
////            @Override
////            public boolean onQueryTextChange(String newText) {
////                adapter.getFilter().filter(newText);
////                return false;
////            }
////        });


        Log.d("main", "onCreateView: " + getContext());


        return binding.getRoot();

    }

    private void requestContactPermission() {

        ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS}, 100);

    }

    private boolean checkContactPermission() {
        return ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED;

    }


    public void setContactListHelperClass() {
        contactListHelper.setContactListContext(getContext());
        contactListHelper.setBinding(binding);
        contactListHelper.setPhoneNumber(getContext().getSharedPreferences("DATA", Context.MODE_PRIVATE).getString("phoneNumber", ""));

    }


}