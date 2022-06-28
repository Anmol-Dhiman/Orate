package com.example.orate.Activity.SingInTimeActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.orate.Repository.Firebase.FirebaseMethods;
import com.example.orate.databinding.ActivityOtpVerificationBinding;

public class OtpVerification extends AppCompatActivity {


    private ActivityOtpVerificationBinding binding;
    private String code = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String phoneNumber = getIntent().getStringExtra("PhoneNumber");
        String verificationID = getIntent().getStringExtra("verificationID");


        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (binding.editTextNumber1.getText().toString().trim().isEmpty() ||
                        binding.editTextNumber2.getText().toString().trim().isEmpty() ||
                        binding.editTextNumber3.getText().toString().trim().isEmpty() ||
                        binding.editTextNumber4.getText().toString().trim().isEmpty() ||
                        binding.editTextNumber5.getText().toString().trim().isEmpty() ||
                        binding.editTextNumber6.getText().toString().trim().isEmpty()) {
                    Toast.makeText(OtpVerification.this, "Enter the correct otp...", Toast.LENGTH_SHORT).show();
                } else {
                    code = binding.editTextNumber1.getText().toString().trim() +
                            binding.editTextNumber2.getText().toString().trim() +
                            binding.editTextNumber3.getText().toString().trim() +
                            binding.editTextNumber4.getText().toString().trim() +
                            binding.editTextNumber5.getText().toString().trim() +
                            binding.editTextNumber6.getText().toString().trim();


                    FirebaseMethods.verifyOTP(OtpVerification.this, code, phoneNumber, verificationID);

                }
            }
        });


        binding.resendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (code != null) {
                    Log.d("main", "in the resend otp button ");
                    Toast.makeText(OtpVerification.this, "resending otp", Toast.LENGTH_SHORT).show();
                    finish();
                    FirebaseMethods.sendOtp(OtpVerification.this, phoneNumber);
                }
            }
        });
    }


    private void optInput() {
//        binding.editTextNumber1.addTextChangedListener();
    }


    @Override
    public void onBackPressed() {
        FirebaseMethods.showExitDialog(this);
    }
}