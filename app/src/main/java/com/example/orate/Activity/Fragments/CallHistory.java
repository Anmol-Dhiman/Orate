package com.example.orate.Activity.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.orate.Activity.Adapter;
import com.example.orate.MainActivity;
import com.example.orate.R;
import com.example.orate.Repository.RoomDatabase.CallHistoryModel;
import com.example.orate.ViewModel.HistoryViewModel;
import com.example.orate.databinding.FragmentCallHistoryBinding;

import java.util.List;


public class CallHistory extends Fragment {

    private FragmentCallHistoryBinding binding;
    private LiveData<List<CallHistoryModel>> liveData;
    private HistoryViewModel viewModel;
    public static final int CALL_HISTORY_ACTIVITY_CODE = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Adapter adapter = new Adapter(container.getContext(), 2);


        binding.historyRecyclerView.setAdapter(adapter);
        binding.historyRecyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));

        binding = FragmentCallHistoryBinding.inflate(inflater, container, false);

        viewModel = MethodsHelperClass.getHelperMethods().historyViewModel;


        viewModel.getHistory().observe(getViewLifecycleOwner(), new Observer<List<CallHistoryModel>>() {
            @Override
            public void onChanged(List<CallHistoryModel> callHistoryModels) {
                adapter.setCallHistoryModelsList(callHistoryModels);
            }
        });


        return binding.getRoot();


    }
}