package com.example.todonotesapp.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todonotesapp.NotesApp
import com.example.todonotesapp.utils.AppConstants
import com.example.todonotesapp.R
import com.example.todonotesapp.utils.SharePrefConst
import com.example.todonotesapp.adapter.NotesAdapter
import com.example.todonotesapp.clicklistener.ItemClickListener
import com.example.todonotesapp.db.Notes
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MyNotesActivity:AppCompatActivity() {
    var fullName:String = ""
    lateinit var fabAddNotes: FloatingActionButton
    lateinit var sharedPreferences: SharedPreferences
    lateinit var recyclerViewNotes: RecyclerView
    var notesList = ArrayList<Notes>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_notes)
        setupSharedPreferences()
        bindViews()
        getIntentData()
        getDataFromDb()
        if (supportActionBar != null) {
            supportActionBar?.title = fullName
        }
        fabAddNotes.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                setupDialogBox()
            }

        })
        setupRecyclerView()



    }


    private fun getDataFromDb() {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        val listOfNotes = notesDao.getAll()
        notesList.addAll(listOfNotes)
    }

    private fun setupDialogBox() {
        val view = LayoutInflater.from(this@MyNotesActivity).inflate(R.layout.add_notes_dialog_layout, null)
        val editTextTitle = view.findViewById<EditText>(R.id.editTextTitle)
        val editTextDescription = view.findViewById<EditText>(R.id.editTextDescription)
        val buttonSubmit = view.findViewById<Button>(R.id.button_submit)
        val dialog = AlertDialog.Builder(this)
            .setView(view).setCancelable(false).create()
        buttonSubmit.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val title  = editTextTitle.text.toString()
                val description = editTextDescription.text.toString()
                if (title.isNotEmpty() && description.isNotEmpty()) {
                    val notes = Notes(title = title, description = description)
                    notesList.add(notes)

                    addNotesToDb(notes)
                } else {
                    Toast.makeText(this@MyNotesActivity,"Title or Description can't be empty", Toast.LENGTH_SHORT).show()

                }
               // setupRecyclerView()
                dialog.hide()

            }

        })
        dialog.show()


    }

    private fun addNotesToDb(notes: Notes) {
        // insertion to database
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        notesDao.insert(notes)
    }

    private fun setupRecyclerView() {
        val itemClickListener = object : ItemClickListener{
            override fun onClick(notes: Notes) {
             val  intent = Intent(this@MyNotesActivity, DetailActivity::class.java)
                intent.putExtra(AppConstants.TITLE, notes.title)
                intent.putExtra(AppConstants.DESCRIPTION, notes.description)
                startActivity(intent)
            }

            override fun onUpdate(notes: Notes) {

            }

        }

        val notesAdapter = NotesAdapter(notesList, itemClickListener)
        val linearLayoutManager = LinearLayoutManager(this@MyNotesActivity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerViewNotes.layoutManager = linearLayoutManager
        recyclerViewNotes.adapter = notesAdapter

    }

    private fun getIntentData() {
        val intent = intent
        if (intent.hasExtra(AppConstants.FULL_NAME)) {
            fullName = intent.getStringExtra(AppConstants.FULL_NAME).toString()
        }
        if (fullName.isEmpty()) {
            fullName = sharedPreferences.getString(SharePrefConst.FULL_NAME,"").toString()

        }

    }

    private fun setupSharedPreferences() {
        sharedPreferences = getSharedPreferences(SharePrefConst.SHARED_PREFERENCE_NAME, MODE_PRIVATE)

    }

    private fun bindViews() {
        fabAddNotes = findViewById(R.id.fabAddNotes)
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes)

    }
}