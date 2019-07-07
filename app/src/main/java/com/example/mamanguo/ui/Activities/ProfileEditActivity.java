package com.example.mamanguo.ui.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mamanguo.R;

import static com.example.mamanguo.helpers.Constants.EMAIL;
import static com.example.mamanguo.helpers.Constants.FIRST_NAME;
import static com.example.mamanguo.helpers.Constants.LAST_NAME;
import static com.example.mamanguo.helpers.Constants.PHONE_NUMBER;
import static com.example.mamanguo.helpers.Constants.USER_DATA;

public class ProfileEditActivity extends AppCompatActivity {

    Context mContext;
    private EditText firstName_input;
    private EditText lastName_input;
    private EditText email_input;
    private EditText phoneNumber_input;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        firstName_input = findViewById(R.id.input_firstName);
        lastName_input = findViewById(R.id.input_lastName);
        email_input = findViewById(R.id.input_email);
        phoneNumber_input = findViewById(R.id.input_phone_number);
        getUserData();
    }

    private void getUserData() {
        SharedPreferences sharedPreferences = getSharedPreferences(USER_DATA, mContext.MODE_PRIVATE);
        firstName = sharedPreferences.getString(FIRST_NAME, "");
        lastName = sharedPreferences.getString(LAST_NAME, "");
        email = sharedPreferences.getString(EMAIL, "");
        phoneNumber = sharedPreferences.getString(PHONE_NUMBER, "");
        updateViews();
    }

    private void updateViews() {
        firstName_input.setText(firstName);
        lastName_input.setText(lastName);
        email_input.setText(email);
        phoneNumber_input.setText(phoneNumber);
    }
}

