package com.example.todonotesapp.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.todonotesapp.utils.AppConstants
import com.example.todonotesapp.R
import com.example.todonotesapp.utils.SharePrefConst

class LoginActivity:AppCompatActivity() {

    lateinit var editTextFullName:EditText
    lateinit var editTextUserName:EditText
    lateinit var buttonLogin:Button
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        buttonLogin = findViewById(R.id.buttonLogin)
        editTextFullName= findViewById(R.id.editTextName)
        editTextUserName = findViewById(R.id.editTextUserName)
        setupSharedPreferences();

        val clickAction = object : View.OnClickListener{
            override fun onClick(v: View?) {
                val fullName = editTextFullName.text.toString()
                val userName = editTextUserName.text.toString()

                if (fullName.isNotEmpty() && userName.isNotEmpty()){
                    val intent = Intent(this@LoginActivity, MyNotesActivity::class.java)
                    intent.putExtra(AppConstants.FULL_NAME, fullName)
                    startActivity(intent)
                    saveFullName(fullName)
                    saveLoginStatus()

                }
            }

        }

        buttonLogin.setOnClickListener(clickAction)

    }

    private fun saveLoginStatus() {
        editor = sharedPreferences.edit()
        editor.putBoolean(SharePrefConst.IS_LOGGED_IN, true)
        editor.apply()
    }

    private fun saveFullName(fullName: String) {
        editor = sharedPreferences.edit()
        editor.putString(SharePrefConst.FULL_NAME, fullName)
        editor.apply()


    }

    private fun setupSharedPreferences() {
        sharedPreferences = getSharedPreferences(SharePrefConst.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }
}