package com.example.todonotesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText editTextFullName, editTextUserName;
    Button buttonLogin;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonLogin = findViewById(R.id.buttonLogin);
        editTextFullName = findViewById(R.id.editTextName);
        editTextUserName = findViewById(R.id.editTextUserName);
        setupSharedPreferences();

        View.OnClickListener clickAction = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log.d("Login","onClick");
                String fullName = editTextFullName.getText().toString();
                String userName = editTextUserName.getText().toString();

                if (!TextUtils.isEmpty(fullName) && !TextUtils.isEmpty(userName)) {
                    Intent intent = new Intent(LoginActivity.this, MyNotesActivity.class);
                    intent.putExtra(AppConstants.FULL_NAME, fullName);
                    startActivity(intent);

                    //Login
                    saveLoginStatus();
                    saveFullName(fullName);
                } else {
                    Toast.makeText(LoginActivity.this,"Please add username and fullname",Toast.LENGTH_SHORT).show();
                }
            }
        };
        buttonLogin.setOnClickListener(clickAction);



    }

    private void saveFullName(String fullName) {
        editor = sharedPreferences.edit();
        editor.putString(SharePrefConst.FULL_NAME, fullName);
        editor.apply();
    }

    private void saveLoginStatus() {
        // Open the editor
        editor = sharedPreferences.edit();
        //Write down in the editor
        editor.putBoolean(SharePrefConst.IS_LOGGED_IN, true);
        //Apply the changes
        editor.apply();
    }

    private void setupSharedPreferences() {

        sharedPreferences = getSharedPreferences(SharePrefConst.SHARED_PREFERENCE_NAME,MODE_PRIVATE);

    }




}