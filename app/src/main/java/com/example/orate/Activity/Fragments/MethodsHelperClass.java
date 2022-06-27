package com.example.orate.Activity.Fragments;


import android.content.Context;
import android.view.View;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.TypeConverters;


import com.example.orate.DataModel.DateConverter;
import com.example.orate.MainActivity;
import com.example.orate.Repository.RoomDatabase.CallHistoryModel;
import com.example.orate.ViewModel.HistoryViewModel;
import com.example.orate.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;


public class MethodsHelperClass {

    private static MethodsHelperClass helperMethods = null;


    public HistoryViewModel historyViewModel = null;
    private ActivityMainBinding binding;
    private String phoneNumber;
    private Context context;
    private String mediaType;
    private String friendPhoneNumber;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private boolean isPeerConnected = false;


    public void setHistoryViewModel(HistoryViewModel historyViewModel) {
        this.historyViewModel = historyViewModel;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setBinding(ActivityMainBinding binding) {
        this.binding = binding;
    }


    public static MethodsHelperClass getHelperMethods() {

        if (helperMethods == null) {
            helperMethods = new MethodsHelperClass();
        }
        return helperMethods;
    }

    public void setUpWebView() {

        binding.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(PermissionRequest request) {
                request.grant(request.getResources());
            }
        });

        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        binding.webView.addJavascriptInterface(new JavaScriptInterface(helperMethods), "Android");

        loadVideoCall();
    }

    private void loadVideoCall() {
        String filePath = "file:android_asset/call.html";
        binding.webView.loadUrl(filePath);

        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                initializePeer();

            }
        });

    }

    private void initializePeer() {

        callJavaScriptFunction("javascript:init(\"${phoneNumber}\")");

        firebaseDatabase.getReference().child("User").child(phoneNumber).child("isAvailable").setValue(true);
        firebaseDatabase.getReference().child("User").child(phoneNumber).child("incoming").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                onCallRequest(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void callJavaScriptFunction(String s) {
        binding.webView.post(new Runnable() {
            @Override
            public void run() {
                binding.webView.evaluateJavascript(s, null);
            }
        });
    }


    private void onCallRequest(String caller) {
        if (caller == null) return;
        binding.callAlertMessage.setVisibility(View.VISIBLE);
        binding.callerName.setText(caller + " is calling....");


        binding.pickUpCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//               incoming call accepted room input

                historyViewModel.insert(new CallHistoryModel(mediaType, friendPhoneNumber, "incoming"));
                binding.callAlertMessage.setVisibility(View.GONE);
                firebaseDatabase.getReference().child("User").child(phoneNumber).child("isConnected").setValue(true);

            }
        });

        binding.cancelCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                incomingMissedCall room input
//                date variable
//                historyViewModel.insert(new CallHistoryModel(mediaType, friendPhoneNumber, "incomingMissedCall"));
                binding.callAlertMessage.setVisibility(View.GONE);
                firebaseDatabase.getReference().child("User").child(phoneNumber).child("incoming").setValue(false);
                firebaseDatabase.getReference().child("User").child(phoneNumber).child("isConnected").setValue(false);

            }
        });


    }

    public void setFriendPhoneNumber(String friendPhoneNumber) {
        this.friendPhoneNumber = friendPhoneNumber;
    }

    public void sendCallRequest(String friendPhoneNumber, String mediaType) {
        if (!isPeerConnected) {
            Toast.makeText(context, "Connectivity issues...", Toast.LENGTH_LONG).show();
            return;
        }

//        this is for inserting into the local room database
        this.mediaType = mediaType;


        firebaseDatabase.getReference().child("User").child(friendPhoneNumber).child("isAvailable").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue().toString() == "true") {

//                    now this will call the method onCallRequest as we have changed the value of incoming
                    setFriendPhoneNumber(friendPhoneNumber);

                    firebaseDatabase.getReference().child("User").child(phoneNumber).child("incoming").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            switchToControls();
                            callJavaScriptFunction("javascript:startCall(\"${friendPhoneNumber}\")");

//                          isConnected is used to check weather two people are doing a call or not right now and on the basis of that we give the input in the room database for call history
                            firebaseDatabase.getReference().child("User").child(phoneNumber).child("isConnected").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

//                                we have to store date here
                                    if (snapshot.getValue().toString() == "true") {
//                                        historyViewModel.insert(new CallHistoryModel(mediaType, friendPhoneNumber, "outgoing", ));
                                    } else {
//                                        historyViewModel.insert(new CallHistoryModel(mediaType, friendPhoneNumber, "outgoingMissedCall", ));
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                } else
                    Toast.makeText(context, "Your friend is not connected....", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public void onPeerConnected() {
        isPeerConnected = true;
    }

    private void switchToControls() {
        binding.basicUi.setVisibility(View.GONE);
        binding.webView.setVisibility(View.VISIBLE);
        binding.mediaControls.setVisibility(View.VISIBLE);
    }
}
