package com.example.notes.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.notes.database.daos.NotesDao
import com.example.notes.database.entities.Note

@Database(version = 2, entities = [Note::class], exportSchema = true)
@TypeConverters(Converters::class)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun getNotesDao(): NotesDao
}
