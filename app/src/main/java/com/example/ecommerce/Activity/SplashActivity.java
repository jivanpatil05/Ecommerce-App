package com.example.ecommerce.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ecommerce.R;
import com.example.ecommerce.databinding.ActivityMainBinding;
import com.example.ecommerce.databinding.ActivitySplashBinding;
import com.example.ecommerce.databinding.ActivityStartBinding;

public class SplashActivity extends AppCompatActivity {
    ActivitySplashBinding mainxml;

    private static final int SPLASH_TIMEOUT = 3000;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mainxml= ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(mainxml.getRoot());

        sharedPreferences = getSharedPreferences(LoginActivity.SHARED_PREF_NAME, MODE_PRIVATE);

        // Delayed execution to show splash screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isLoggedIn()) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, StartActivity.class));
                }
                finish();
            }
        }, SPLASH_TIMEOUT);
    }
    private boolean isLoggedIn() {
        String storedUsername = sharedPreferences.getString(LoginActivity.KEY_USERNAME, "");
        return !TextUtils.isEmpty(storedUsername);
    }
}