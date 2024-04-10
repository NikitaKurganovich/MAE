package com.example.notes.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notes.dao.NotesDao
import com.example.notes.models.Note

@Database(version = 1, entities = [Note::class])
abstract class NoteDatabase : RoomDatabase() {
    abstract fun getNotesDao(): NotesDao
}