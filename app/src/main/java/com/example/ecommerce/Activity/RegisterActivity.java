package com.example.ecommerce.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecommerce.ModelClass.User.Address;
import com.example.ecommerce.ModelClass.User.Geolocation;
import com.example.ecommerce.ModelClass.User.Name;
import com.example.ecommerce.ModelClass.User.Users;
import com.example.ecommerce.R;
import com.example.ecommerce.Response.UserResponse;
import com.example.ecommerce.ViewModel.NewUserViewModel;
import com.example.ecommerce.databinding.ActivityMainBinding;
import com.example.ecommerce.databinding.ActivityRegisterBinding;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding mainxml;

    private static final String TAG = MainActivity.class.getSimpleName();
    NewUserViewModel newUserViewModel;

    private SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "MyPrefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mainxml=ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(mainxml.getRoot());

        newUserViewModel = new ViewModelProvider(this).get(NewUserViewModel.class);
        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        mainxml.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewUser();
            }
        });

        initViewModel();
    }

    private void createNewUser() {
        String Fname =mainxml.firstname.getText().toString();
        String Lname =mainxml.lastname.getText().toString();
        String Username=mainxml.username.getText().toString();
        String Password=mainxml.password.getText().toString();
        String Email=mainxml.email.getText().toString();
//        String City=mainxml.city.getText().toString();
//        String Street=mainxml.street.getText().toString();
//        int Number= Integer.parseInt(mainxml.number.getText().toString());
//        String Zipcode=mainxml.zipcode.getText().toString();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, Username);
        editor.putString(KEY_PASSWORD, Password);
        editor.apply();
        
        Address address=new Address(new Geolocation("50.3467","-20.1310"),"Sangli","Tandulwadi",415411,"45637865");
        Name name=new Name(Fname,Lname);
        Users users=new Users(address,0,Email,Username,Password,name,"7030773371",0);
        newUserViewModel.callApiOne(users);

    }


    private void initViewModel() {

        newUserViewModel.getCreateUserObserver().observe(this, new Observer<UserResponse>() {
            @Override
            public void onChanged(UserResponse userResponse) {
                if (userResponse == null) {
                    Toast.makeText(RegisterActivity.this, "Failed To Create New User", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(RegisterActivity.this, "Successfully Created New User", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        newUserViewModel.mErrorMessage().observe(RegisterActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(RegisterActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
        Log.d(TAG, "initViewModel: Observers initialized");

    }
}