package com.example.orate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.orate.Activity.Fragments.CallHistory;
import com.example.orate.Activity.Fragments.ContactList;
import com.example.orate.Activity.Fragments.JavaScriptInterface;
import com.example.orate.Activity.Fragments.MethodsHelperClass;
import com.example.orate.Activity.Fragments.UserProfile;
import com.example.orate.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private boolean isPeerConnected = false;
    private String phoneNumber = null;
    private FirebaseDatabase firebaseDatabase;

    private MethodsHelperClass helperClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new ContactList());
        firebaseDatabase = FirebaseDatabase.getInstance();
        helperClass = MethodsHelperClass.getHelperMethods();
        helperClassSetup();


        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.contactList:
                    replaceFragment(new ContactList());
                    break;
                case R.id.callHistory:
                    replaceFragment(new CallHistory());
                    break;
                case R.id.userProfile:
                    replaceFragment(new UserProfile());
                    break;
            }

            return true;
        });


        phoneNumber = getIntent().getStringExtra("PhoneNumber");
        Bundle bundle = new Bundle();
        bundle.putString("PhoneNumber", phoneNumber);
        new UserProfile().setArguments(bundle);


    }

    private void helperClassSetup() {
        helperClass.setBinding(binding);
        helperClass.setPhoneNumber(phoneNumber);
        helperClass.setContext(MainActivity.this);
        helperClass.setUpWebView();
    }


    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, fragment).commit();
    }


    @Override
    public void onBackPressed() {
        finish();
    }


    @Override
    protected void onDestroy() {
        firebaseDatabase.getReference().child("User").child(phoneNumber).child("isAvailable").setValue(false);
        binding.webView.loadUrl("about:blank");
        super.onDestroy();
    }
}
