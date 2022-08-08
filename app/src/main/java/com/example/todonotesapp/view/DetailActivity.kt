package com.example.todonotesapp.view

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.todonotesapp.utils.AppConstants
import com.example.todonotesapp.R

class DetailActivity:AppCompatActivity() {
    lateinit var textViewTitle:TextView
    lateinit var textViewDescription:TextView

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_detail)

        bindViews()
        setupIntentData()
    }

    private fun bindViews() {
        textViewTitle = findViewById(R.id.textViewTitle)
        textViewDescription = findViewById(R.id.textViewDescription)
    }

    private fun setupIntentData() {
        val intent = intent;
        val title = intent.getStringExtra(AppConstants.TITLE)
        val description = intent.getStringExtra(AppConstants.DESCRIPTION)
        textViewTitle.text = title
        textViewDescription.text = description


    }

}