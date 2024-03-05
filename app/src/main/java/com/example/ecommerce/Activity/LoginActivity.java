package com.example.ecommerce.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ecommerce.ModelClass.User.Users;
import com.example.ecommerce.R;
import com.example.ecommerce.ViewModel.LoginViewMode;
import com.example.ecommerce.ViewModel.UsersViewModel;
import com.example.ecommerce.databinding.ActivityLoginBinding;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding mainxml;
    SharedPreferences sharedPreferences;
    UsersViewModel loginViewModel;
    Users matchingUser;

    public static final String SHARED_PREF_NAME = "MyPrefs";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mainxml = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(mainxml.getRoot());

        loginViewModel = ViewModelProviders.of(this).get(UsersViewModel.class);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        if (isLoggedIn()) {
            dayrectMainActivity();
        } else {
            mainxml.login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loginUser();
                }
            });
        }

    }

    private void dayrectMainActivity() {
        Intent homeIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(homeIntent);
        finishAffinity();
    }

    private void loginUser() {

        String enteredUsername = mainxml.Username.getText().toString().trim();
        String enteredPassword = mainxml.password111.getText().toString().trim();

        if (TextUtils.isEmpty(enteredUsername) || TextUtils.isEmpty(enteredPassword)) {
            Toast.makeText(LoginActivity.this, "All Fields Are Required", Toast.LENGTH_SHORT).show();
            return;
        }

        loginViewModel.callApiOne();

        loginViewModel.getArticleResponseLiveData().observe(this, new Observer<List<Users>>() {
            @Override
            public void onChanged(List<Users> userList) {
                // Check if the entered credentials match any user in the list
                boolean credentialsMatch = false;

                for (Users users : userList) {
                    if (users.getUsername().equals(enteredUsername) && users.getPassword().equals(enteredPassword)) {
                        credentialsMatch = true;
                        matchingUser = users;
                        break;
                    }
                }

                if (credentialsMatch) {
                    // Credentials match, login successful
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY_USERNAME, enteredUsername);
                    editor.putString(KEY_PASSWORD, enteredPassword);
                    editor.putInt("USER_ID", matchingUser.getId());
                    editor.putBoolean(KEY_IS_LOGGED_IN, true);
                    editor.apply();
                    dayrectMainActivity();
                } else {
                    // Credentials do not match any user, handle unsuccessful login
                    Toast.makeText(LoginActivity.this, "Incorrect username or password. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);

    }

    public void signin(View view) {

        String savedUsername = sharedPreferences.getString(KEY_USERNAME, "");
        String savedPassword = sharedPreferences.getString(KEY_PASSWORD, "");

        String Useername = mainxml.Username.getText().toString();
        String Password = mainxml.password111.getText().toString();

        if (TextUtils.isEmpty(Useername) || TextUtils.isEmpty(Password)) {
            Toast.makeText(this, "Enter both Username and Password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Useername.equals(savedUsername) && Password.equals(savedPassword)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLoggedIn", true);
            editor.apply();

            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Incorrect username or password. Please try again.", Toast.LENGTH_SHORT).show();
            // Add code to handle unsuccessful login (e.g., clear the entered password field)
        }
    }
}