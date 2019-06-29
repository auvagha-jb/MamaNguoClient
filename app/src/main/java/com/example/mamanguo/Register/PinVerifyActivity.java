package com.example.mamanguo.Register;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.example.mamanguo.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class PinVerifyActivity extends AppCompatActivity {

    private static final String TAG = PinVerifyActivity.class.getSimpleName();
    private PinEntryEditText pinEntry;
    private TextView resendLink;
    private String codeSent;
    private String phoneNumber;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private PhoneAuthProvider.ForceResendingToken mResendToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_verify);
        pinEntry = findViewById(R.id.txt_pin_entry);
        resendLink = findViewById(R.id.resend_link);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressbar);

        if (savedInstanceState == null) {
            phoneNumber = Objects.requireNonNull(getIntent().getExtras()).getString("PHONE_NUMBER");
            sendVerificationCode(phoneNumber);
            Log.d(TAG, String.format("Phone number is %s", phoneNumber));
        } else {
            Log.e(TAG, "Phone number is null");
        }

        if (pinEntry != null) {
            /*pinEntry.setTypeface(ResourcesCompat.getFont(this, R.font.charmonman_regular));*/
            pinEntry.setOnPinEnteredListener(str -> {
                String pinEntered = str.toString();
                if (pinEntered.length() == 6) {
                    verifyPin(pinEntered);
                }
            });
        }

        resendLink.setOnClickListener(v -> {
            resendVerificationCode(phoneNumber, mResendToken);
        });
    }


    private void verifyPin(String pinEntered) {
        Log.d(TAG, String.format("verifyPin: %s", pinEntered));
        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, pinEntered);
        signInWithPhoneAuthCredential(credential);
    }

    private void sendVerificationCode(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(PinVerifyActivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PinVerifyActivity.this, SignUpActivity.class);
                        startActivity(intent);

                    } else {
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(PinVerifyActivity.this, "INCORRECT", Toast.LENGTH_SHORT).show();
                            pinEntry.setError(true);
                            pinEntry.postDelayed(() -> pinEntry.setText(null), 1000);
                        }
                    }
                    progressBar.setVisibility(View.GONE);
                });

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeSent = s;
            mResendToken = forceResendingToken;
        }
    };

    /**
     * Resend verification code
     */
    private void resendVerificationCode(String phoneNumber, PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }


}

