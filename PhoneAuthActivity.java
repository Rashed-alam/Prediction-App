package com.example.rashedalam.callpredictor;

/**
 * Created by asifsabir on 12/5/17.
 */


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

/**
 * Created by AJ
 * Created on 09-Jun-17.
 */

public class PhoneAuthActivity extends AppCompatActivity implements
        View.OnClickListener {

    EditText mPhoneNumberField, mVerificationField;
    Button mStartButton, mVerifyButton, mResendButton;
    ProgressBar progressBar;
    TextView mVerificationHint;
    LinearLayout linearLayout;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    String mVerificationId;

    private static final String TAG = "PhoneAuthActivity";
    private static final int PERMISSION_READ_STATE = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        getSupportActionBar().setTitle("User Authentication");
        mAuth = FirebaseAuth.getInstance();
        //asking for permissions
        if (Build.VERSION.SDK_INT >22)
            ActivityCompat.requestPermissions(PhoneAuthActivity.this, new String[]{Manifest.permission.READ_CALL_LOG}, PERMISSION_READ_STATE);


     //in case this user logged in before and didnt log out
        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }


        linearLayout = (LinearLayout) findViewById(R.id
                .linear_layout);
        mPhoneNumberField = (EditText) findViewById(R.id.field_phone_number);
        mVerificationField = (EditText) findViewById(R.id.field_verification_code);

        mStartButton = (Button) findViewById(R.id.button_start_verification);
        mVerifyButton = (Button) findViewById(R.id.button_verify_phone);
        mResendButton = (Button) findViewById(R.id.button_resend);
        progressBar = (ProgressBar) findViewById(R.id.pb_loading);
        mVerificationHint = (TextView) findViewById(R.id.tv_verification_hint);
        mStartButton.setOnClickListener(this);
        mStartButton.setOnClickListener(this);
        mVerifyButton.setOnClickListener(this);
        mResendButton.setOnClickListener(this);

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Log.d(TAG, "onVerificationCompleted:" + credential);
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.w(TAG, "onVerificationFailed", e);
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    mPhoneNumberField.setError("Invalid phone number.");
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Toast.makeText(PhoneAuthActivity.this, "Quota exceeded", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                //showing a snackbar
                Toast.makeText(PhoneAuthActivity.this, "SMS SENT", Toast.LENGTH_SHORT).show();

                mVerifyButton.setVisibility(View.VISIBLE);
                mVerificationField.setVisibility(View.VISIBLE);
                mVerificationHint.setVisibility(View.VISIBLE);

                progressBar.setVisibility(View.GONE);
                mStartButton.setVisibility(View.GONE);
                Log.d(TAG, "onCodeSent:" + verificationId);
                mVerificationId = verificationId;
                mResendToken = token;
            }
        };
    }


    //permission results
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_READ_STATE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted!
                    //checks on app running ; do not Toast anything here.
                } else {
                    // permission denied
                    Toast.makeText(this, "Permission not granted!", Toast.LENGTH_SHORT).show();

                }
                return;
            }

        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            Toast.makeText(PhoneAuthActivity.this, "VERIFIED!!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(PhoneAuthActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                mVerificationField.setError("Invalid code.");
                            }
                        }
                    }
                });
    }


    private void startPhoneNumberVerification(String phoneNumber) {
        //showing a snackbar
        Toast.makeText(this, "SENDING SMS", Toast.LENGTH_SHORT).show();
        mResendButton.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        progressBar.setVisibility(View.VISIBLE);

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }

    private boolean validatePhoneNumber() {
        String phoneNumber = mPhoneNumberField.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            mPhoneNumberField.setError("Invalid phone number.");
            return false;
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_start_verification:
                if (!validatePhoneNumber()) {
                    return;
                }
                startPhoneNumberVerification(mPhoneNumberField.getText().toString());
                break;
            case R.id.button_verify_phone:
                String code = mVerificationField.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    mVerificationField.setError("Cannot be empty.");
                    return;
                }

                verifyPhoneNumberWithCode(mVerificationId, code);
                break;
            case R.id.button_resend:
                resendVerificationCode(mPhoneNumberField.getText().toString(), mResendToken);
                break;
        }

    }

//    public void checkDatabaseRegistrationData() {
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser != null) {
//
//            //checking whether the user is registered or not, if then send to MainActivity
//            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Users");
//            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot snapshot) {
//                    if (snapshot.hasChild(mAuth.getCurrentUser().getUid().toString())) {
//                        // Shifting to MainActivity
//                        startActivity(new Intent(PhoneAuthActivity.this, MainActivity.class));
//                        finish();
//                    } else {
//                        //sending for registration
//                        Toast.makeText(PhoneAuthActivity.this, "You are not registered!", Toast.LENGTH_LONG).show();
//                        startActivity(new Intent(PhoneAuthActivity.this, MainActivity.class));
//                        finish();
//                    }
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    //DO nothing
//                }
//            });
//
//        }
//    }

}