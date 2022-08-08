package com.example.todonotesapp.clicklistener

import com.example.todonotesapp.db.Notes

interface ItemClickListener {
    fun onClick(notes:Notes)
    fun onUpdate(notes: Notes)
}