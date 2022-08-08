package com.example.todonotesapp.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.todonotesapp.R
import com.example.todonotesapp.utils.SharePrefConst

class SplashActivity: AppCompatActivity() {
    lateinit var sharedPreferences:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setupSharedPreferences()
        checkLoginStatus()
    }

    private fun setupSharedPreferences() {
        sharedPreferences = getSharedPreferences(SharePrefConst.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    private fun checkLoginStatus() {
        val isLoggedIn = sharedPreferences.getBoolean(SharePrefConst.IS_LOGGED_IN, false)
        if (isLoggedIn!!) {
            val intent = Intent(this@SplashActivity, MyNotesActivity::class.java)
            Log.d("SplashActivity", "Logged In")
            startActivity(intent)
        } else {
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            Log.d("SplashActivity", "Not Logged In")
            startActivity(intent)
        }
    }


}