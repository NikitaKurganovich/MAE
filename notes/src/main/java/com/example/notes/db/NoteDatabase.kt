package com.example.notes.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.notes.dao.NotesDao
import com.example.notes.models.Note

@Database(version = 2, entities = [Note::class], exportSchema = true)
@TypeConverters(Converters::class)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun getNotesDao(): NotesDao
}
