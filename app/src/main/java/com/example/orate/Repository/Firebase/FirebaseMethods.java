package com.example.orate.Repository.Firebase;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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
                intent.putExtra("PhoneNumber", phoneNumber);
                context.startActivity(intent);

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
                        intent.putExtra("PhoneNumber", phoneNumber);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }


    public void registerUser(String userName, String about, String fullName, String phoneNumber, String image) {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference reference = storage.getReference().child("Profile Image").child(phoneNumber);
        reference.putFile(Uri.parse(image)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        UserModel user = new UserModel(userName, uri.toString(), about, fullName, phoneNumber);
                        database.getReference().child("User").child(phoneNumber).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
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
}
