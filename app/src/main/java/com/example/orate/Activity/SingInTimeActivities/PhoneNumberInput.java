package com.example.orate.Activity.SingInTimeActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.orate.Repository.Firebase.AuthenticationMethods;
import com.example.orate.databinding.ActivityPhoneNumberInputBinding;

public class PhoneNumberInput extends AppCompatActivity {

    private ActivityPhoneNumberInputBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhoneNumberInputBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = binding.editTextPhone.getText().toString().trim();
                if (phoneNumber.isEmpty() ||
                        phoneNumber.length() != 10) {
                    Toast.makeText(PhoneNumberInput.this, "Enter the phone number!!", Toast.LENGTH_SHORT).show();
                } else {
                    new AlertDialog.Builder(PhoneNumberInput.this)
                            .setTitle("+91 " + phoneNumber)
                            .setMessage("Is this OK, or would you like to edit the number?")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    AuthenticationMethods.sendOtp(PhoneNumberInput.this, phoneNumber);
                                }
                            })
                            .setNeutralButton("EDIT", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();
                }
            }
        });
    }


}