package com.example.todonotesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setupSharedPreferences();
        checkLoginStatus();

    }

    private void setupSharedPreferences() {
        sharedPreferences = getSharedPreferences(SharePrefConst.SHARED_PREFERENCE_NAME, MODE_PRIVATE);

    }

    private void checkLoginStatus() {
        // If user is logged in take them to MyNotesActivity
        // Else take him to LoginActivity
        boolean isLoggedIn = sharedPreferences.getBoolean(SharePrefConst.IS_LOGGED_IN,false);
        if (isLoggedIn) {
            //notes activity
            Intent intent = new Intent(SplashActivity.this, MyNotesActivity.class);
            startActivity(intent);
        } else {
            //take it to login activity
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }
}