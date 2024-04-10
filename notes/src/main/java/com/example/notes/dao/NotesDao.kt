package com.example.notes.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.notes.models.Note

@Dao
interface NotesDao {
    @Insert(entity = Note::class)
    fun insertNote(entity: Note)

    @Query("SELECT * FROM notes")
    fun getAllNotes(): MutableList<Note>

    @Delete
    fun deleteNote(note: Note)

    @Update
    fun updateNote(note: Note)
}