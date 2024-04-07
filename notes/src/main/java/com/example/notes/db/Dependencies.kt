package com.example.notes.db

import android.content.Context
import androidx.room.Room
import com.example.notes.repository.NoteRepository

object Dependencies {
    private lateinit var applicationContext: Context

    fun init(context: Context) {
        applicationContext = context
    }

    private val appDatabase: NoteDatabase by lazy {
        Room.databaseBuilder(applicationContext, NoteDatabase::class.java, "notes.db")
            .build()
    }

    val noteRepository: NoteRepository by lazy { NoteRepository(appDatabase.getNotesDao()) }
}