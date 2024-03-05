package com.example.ecommerce.Activity;

import static com.example.ecommerce.Activity.LoginActivity.KEY_USERNAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import android.widget.Toast;

import com.example.ecommerce.Fragment.CartFragment;

import com.example.ecommerce.Fragment.HomeFragment;
import com.example.ecommerce.Fragment.ProfileFragment;
import com.example.ecommerce.R;
import com.example.ecommerce.databinding.ActivityMainBinding;
import com.example.ecommerce.databinding.ActivityRegisterBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityMainBinding mainxml;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainxml= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainxml.getRoot());

        sharedPreferences = getSharedPreferences(LoginActivity.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        Toolbar toolbar = findViewById(R.id.toolbar); //Ignore red line errors
        setSupportActionBar(toolbar);
        mainxml.navView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mainxml.drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        mainxml.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            mainxml.navView.setCheckedItem(R.id.Home);
        }

        ///Bottom navigatio

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        mainxml.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment temp = null;

                switch (item.getItemId()) {
                    case R.id.Home:
                        temp = new HomeFragment();
                        break;
                    case R.id.Setting:
                        temp = new CartFragment();
                        break;
                    case R.id.Profile:
                        temp = new ProfileFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, temp).commit();
                return true;
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case R.id.Setting:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CartFragment()).commit();
                break;
            case R.id.Profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                break;
            case R.id.nav_logout:
                Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show();
                logoutUser();
                break;

        }
        mainxml.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logoutUser() {
        // Clear SharedPreferences to logout user
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Redirect to LoginActivity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Close MainActivity
    }

    @Override
    public void onBackPressed() {
        if (mainxml.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            mainxml.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    public void home(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
        mainxml.drawerLayout.closeDrawer(GravityCompat.START);

    }

}

