package com.example.mamanguo.Register;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mamanguo.R;
import com.example.mamanguo.getAvailableMamaNguo.MainActivity;
import com.example.mamanguo.ui.LoginActivity;

import java.util.Objects;

import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";

    EditText firstNameText;
    EditText lastNameText;
    EditText emailText;
    EditText passwordText;
    EditText reEnterPasswordText;
    Button signUpButton;
    TextView _loginLink;

    private void initComponents() {
        firstNameText = findViewById(R.id.input_firstName);
        lastNameText = findViewById(R.id.input_lastName);
        emailText = findViewById(R.id.input_email);
        passwordText = findViewById(R.id.input_password);
        reEnterPasswordText = findViewById(R.id.input_reEnterPassword);
        signUpButton = findViewById(R.id.btn_signUp);
        _loginLink = findViewById(R.id.link_login);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        initComponents();
        attachListeners();
    }

    public void signUp() {
        Log.d(TAG, "signUp");
        String firstName = firstNameText.getText().toString();
        String lastName = lastNameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (validate()) {
            createIntent(firstName, lastName, email, password);
        }

    }

    private void createIntent(String firstName, String lastName, String email,
                              String password) {
        Bundle extras = new Bundle();
        extras.putString("firstName", firstName);
        extras.putString("lastName", lastName);
        extras.putString("email", email);
        extras.putString("password", password);
        Intent intent = new Intent(SignUpActivity.this, EnterMobileActivity.class);
        intent.putExtras(extras);
        startActivity(intent);
    }


    private boolean validate() {
        boolean valid = true;

        String firstName = firstNameText.getText().toString();
        String lastName = lastNameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String reEnterPassword = reEnterPasswordText.getText().toString();

        if (firstName.isEmpty()) {
            firstNameText.setError("First name is required");
            valid = false;
        } else {
            firstNameText.setError(null);
        }

        if (lastName.isEmpty()) {
            lastNameText.setError("First name is required");
            valid = false;
        } else {
            lastNameText.setError(null);
        }

        if (!email.isEmpty() && !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("Enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }


        if (password.isEmpty() || password.length() < 6 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            reEnterPasswordText.setError(null);
        }

        return valid;
    }

    private void attachListeners() {
        signUpButton.setOnClickListener(v -> signUp());
        _loginLink.setOnClickListener(v -> {
            // Finish the registration screen and return to the Login activity
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        });
    }
}