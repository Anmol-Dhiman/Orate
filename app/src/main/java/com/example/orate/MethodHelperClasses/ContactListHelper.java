package com.example.orate.MethodHelperClasses;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.orate.Activity.Adapter;
import com.example.orate.DataModel.UserModel;
import com.example.orate.databinding.FragmentContactListBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class ContactListHelper {

    private static final int CONTACT_LIST_ACTIVITY_CODE = 1;
    private FragmentContactListBinding binding;
    private String phoneNumber;
    private FirebaseDatabase firebaseDatabase;
    private List<UserModel> contactsList;
    private Context context;
    private Adapter adapter;
    public static ContactListHelper contactListHelper = null;

    public void setContactListContext(Context context) {
        this.context = context;
        Log.d("main", "setContactListContext " + this.context);
    }


    public void setBinding(FragmentContactListBinding binding) {
        this.binding = binding;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        Log.d("main", "setPhoneNumber: " + this.phoneNumber);
    }

    public ContactListHelper() {
        contactsList = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public static ContactListHelper getContactListHelper() {
        if (contactListHelper == null) contactListHelper = new ContactListHelper();
        return contactListHelper;
    }


    public void setContactListAdapter() {

        adapter = new Adapter(context, CONTACT_LIST_ACTIVITY_CODE);
        adapter.setContactList(contactsList);
        binding.contactRecyclerView.setAdapter(adapter);
        binding.contactRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        firebaseDatabase.getReference().child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                contactsList.clear();
                for (DataSnapshot dataSnapshots : snapshot.getChildren()) {
                    UserModel user = dataSnapshots.getValue(UserModel.class);
                    Log.d("contact", "onDataChange: " + user.getPhoneNumber());
//                    addInList(user);

                    if (!user.getPhoneNumber().equals(phoneNumber)) {
                        contactsList.add(user);
                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void addInList(UserModel user) {
        ContentResolver contentResolver = ((Activity) context).getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                @SuppressLint("Range")
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                Log.d("contacts", "addInList: " + number);
                Log.d("user", "addInList: " + user.getPhoneNumber());

                if (user.getPhoneNumber().equals(number) || ("+91" + user.getPhoneNumber()).equals(number)) {
                    contactsList.add(user);
                    return;


                }
            }
        }
    }
}
