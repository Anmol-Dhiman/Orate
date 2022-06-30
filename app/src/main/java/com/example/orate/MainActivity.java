package com.example.orate;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.os.Bundle;



import com.example.orate.Activity.Fragments.CallHistory;
import com.example.orate.Activity.Fragments.ContactList;
import com.example.orate.MethodHelperClasses.MainActivityHelper;
import com.example.orate.Activity.Fragments.UserProfile;
import com.example.orate.ViewModel.HistoryViewModel;
import com.example.orate.databinding.ActivityMainBinding;

import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String phoneNumber = null;
    private FirebaseDatabase firebaseDatabase;
    private boolean isAudio = true;
    private boolean isVideo = true;
    private MainActivityHelper helperClass = null;
    private HistoryViewModel historyViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new ContactList());
        firebaseDatabase = FirebaseDatabase.getInstance();
        helperClass = MainActivityHelper.getHelperMethods();
        historyViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);

        SharedPreferences preferences = getSharedPreferences("DATA", MODE_PRIVATE);
        phoneNumber = preferences.getString("phoneNumber", "");


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


//        the button for video on or off
//        binding.videoButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                isVideo = !isVideo;
//                helperClass.callJavaScriptFunction("javascript:toggleVideo(\"${isVideo}\")");
//                if (isVideo)
//                    binding.videoButton.setImageResource(R.drawable.ic_videomediaon);
//                else
//                    binding.videoButton.setImageResource(R.drawable.ic_videomediaoff);
//            }
//        });


//        the button for audio on and off
//        binding.audioButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                isAudio = !isAudio;
//                helperClass.callJavaScriptFunction("javascript:toggleVideo(\"${isAudio}\")");
//                if (isVideo)
//                    binding.audioButton.setImageResource(R.drawable.ic_audiomediaon);
//                else
//                    binding.audioButton.setImageResource(R.drawable.ic_audiomediaoff);
//            }
//        });

    }

    private void helperClassSetup() {

        helperClass.setHistoryViewModel(historyViewModel);
        helperClass.setBinding(binding);
        helperClass.setMainActivityContext(MainActivity.this);
        helperClass.setPhoneNumber(phoneNumber);
        helperClass.setUpWebView();
    }


    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, fragment).commit();
    }


    @Override
    protected void onStop() {
        super.onStop();
        firebaseDatabase.getReference().child("User").child(phoneNumber).child("isAvailable").setValue("false");
        binding.webView.loadUrl("about:blank");

        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
