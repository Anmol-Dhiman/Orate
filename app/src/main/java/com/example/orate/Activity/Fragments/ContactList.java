package com.example.orate.Activity.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;


import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.orate.MethodHelperClasses.ContactListHelper;
import com.example.orate.databinding.FragmentContactListBinding;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ContactList extends Fragment {

    private FragmentContactListBinding binding;
    private ContactListHelper contactListHelper;
    private List<String> permissions;


    private ActivityResultLauncher<String[]> mPermissionResult = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
                @Override
                public void onActivityResult(Map<String, Boolean> result) {

                    if (result.get(Manifest.permission.READ_CONTACTS) && result.get(Manifest.permission.CAMERA)) {
                        contactListHelper = ContactListHelper.getContactListHelper();
                        setContactListHelperClass();
                        contactListHelper.setContactListAdapter();
                    } else {
                        Toast.makeText(getContext(), "Need permission for reading contact and camera for video call", Toast.LENGTH_LONG).show();
                    }

                }
            });


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        permissions = new ArrayList<>();
        permissions.add(Manifest.permission.READ_CONTACTS);
        permissions.add(Manifest.permission.CAMERA);

        mPermissionResult.launch(permissions.toArray(new String[0]));
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