package com.example.orate.Activity.Fragments;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.orate.Activity.Adapter;
import com.example.orate.DataModel.UserModel;
import com.example.orate.R;
import com.example.orate.databinding.FragmentContactListBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ContactList extends Fragment {

    public static final int CONTACT_LIST_ACTIVITY_CODE = 1;
    private FragmentContactListBinding binding;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth auth;
    private List<UserModel> contactsList;
    private Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentContactListBinding.inflate(inflater, container, false);
        firebaseDatabase = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        adapter = new Adapter(container.getContext(), CONTACT_LIST_ACTIVITY_CODE);


        binding.contactRecyclerView.setAdapter(adapter);
        binding.contactRecyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        firebaseDatabase.getReference().child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                contactsList.clear();
                for (DataSnapshot dataSnapshots : snapshot.getChildren()) {
                    UserModel user = dataSnapshots.getValue(UserModel.class);
                    if (!user.getPhoneNumber().equals(auth.getUid())) {
                        addInList(user);
                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//TODO implement searchView

//        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                if (contactsList.contains(query)) {
//                    adapter.getFilter().filter(query);
//                } else {
//                    Toast.makeText(container.getContext(), "No Match found", Toast.LENGTH_LONG).show();
//                }
//                return false;
//
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                adapter.getFilter().filter(newText);
//                return false;
//            }
//        });


        return binding.getRoot();

    }

    private void addInList(UserModel user) {
        ContentResolver contentResolver = getActivity().getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                if (user.getPhoneNumber().equals(number) || ("+91" + user.getPhoneNumber()).equals(number)) {
                    contactsList.add(user);
                    return;
                }
            }
        }


    }
}