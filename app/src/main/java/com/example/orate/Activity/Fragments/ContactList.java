package com.example.orate.Activity.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.orate.R;
import com.example.orate.databinding.FragmentContactListBinding;

public class ContactList extends Fragment {


    private FragmentContactListBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentContactListBinding.inflate(inflater, container, false);

//        here we have to call the onclick listener for video can audio calls

        return binding.getRoot();

    }
}