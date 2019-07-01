package com.example.mamanguo.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mamanguo.R;
import com.example.mamanguo.Register.EnterMobileActivity;
import com.example.mamanguo.Register.SignUpActivity;
import com.example.mamanguo.Retrofit.MamaNguoApi;
import com.example.mamanguo.Retrofit.RetrofitClient;
import com.example.mamanguo.Retrofit.User;
import com.example.mamanguo.chooseServices.ServicesActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @BindView(R.id.input_email)
    EditText emailText;
    @BindView(R.id.input_password)
    EditText passwordText;
    @BindView(R.id.btn_login)
    Button loginButton;
    @BindView(R.id.link_signup)
    TextView signUpLink;
    MamaNguoApi retrofitInstance;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        retrofitInstance = RetrofitClient.getRetrofitInstance().create(MamaNguoApi.class);
        attachListeners();
    }

    public void login() {
        Log.d(TAG, "Login");

        if (validate()) {
            loginButton.setEnabled(false);
            String email = emailText.getText().toString();
            String password = passwordText.getText().toString();
            userLogin(email, password);
        }
    }


    private void userLogin(String email, String password) {
        User user = new User(email, password);
        Call <User> call = retrofitInstance.userLogin(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User userResponse = response.body();
                if(userResponse.getStatus()){
                    onLoginSuccess(userResponse);
                } else {
                    onLoginFailed(userResponse.getMessage());
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    private void onLoginSuccess(User user) {
        loginButton.setEnabled(true);
        Bundle extras = new Bundle();
        extras.putString("firstName", user.getFirstName());
        extras.putString("lastName", user.getLastName());
        extras.putString("email", user.getEmail());
        extras.putString("phoneNumber", user.getPhoneNumber());
        Intent intent = new Intent(LoginActivity.this, ServicesActivity.class);
        intent.putExtras(extras);
        startActivity(intent);
    }

    private void onLoginFailed(String message) {
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
        loginButton.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("Enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty()) {
            passwordText.setError("A password is required");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }

    public void attachListeners() {
        loginButton.setOnClickListener(v -> login());
        signUpLink.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivityForResult(intent, REQUEST_SIGNUP);
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        });
    }
}
