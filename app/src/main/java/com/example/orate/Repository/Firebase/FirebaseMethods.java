package com.example.orate.Repository.Firebase;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;

import com.example.orate.Activity.SingInTimeActivities.OtpVerification;
import com.example.orate.Activity.SingInTimeActivities.ProfileDetails;
import com.example.orate.DataModel.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.concurrent.TimeUnit;


public class FirebaseMethods {


    private MutableLiveData<FirebaseUser> userMutableLiveData;


    public FirebaseMethods() {
        userMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public static void sendOtp(Context context, String phoneNumber) {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        PhoneAuthProvider.OnVerificationStateChangedCallbacks callBacks;
        callBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(context, "There is some issue please try again....", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                Toast.makeText(context, "OTP sent successfully.....", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, OtpVerification.class);
                intent.putExtra("verificationID", s);

                context.startActivity(intent);
                ((Activity) context).finish();

            }


        };


        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber("+91" + phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity((Activity) context)                 // Activity (for callback binding)
                        .setCallbacks(callBacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);


    }


    public static void verifyOTP(Context context, String otp, String phoneNumber, String verificationID) {


        if (verificationID != null) {

            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, otp);
            FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(context, "Phone number verified.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, ProfileDetails.class);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                    }
                }
            });
        }
    }


    public void registerUser(UserModel user) {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference reference = storage.getReference().child("Profile Image").child(user.getPhoneNumber());
        reference.putFile(Uri.parse(user.getImage())).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("main", "on user auth clear:  " + user);
                        user.setImage(uri.toString());
                        database.getReference().child("User").child(user.getPhoneNumber()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                userMutableLiveData.postValue(auth.getCurrentUser());
                            }
                        });
                    }
                });
            }
        });

    }

    public static void showExitDialog(Context context) {
        new AlertDialog.Builder(context).setTitle("Do you want to exit?").setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setNeutralButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences preferences = context.getSharedPreferences("PREFERENCE", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                Log.d("main", "before change  : " + (Activity) context + " " + preferences.getString("FirstTimeOpening", ""));
                editor.putString("FirstTimeOpening", "yes").apply();
                Log.d("main", "after change in intro : " + (Activity) context + " " + preferences.getString("FirstTimeOpening", ""));

                ((Activity) context).finish();

            }
        }).create().show();
    }
}
