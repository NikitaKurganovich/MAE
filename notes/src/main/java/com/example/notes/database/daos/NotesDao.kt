package com.example.notes.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.notes.database.entities.Note

@Dao
interface NotesDao {
    @Insert(entity = Note::class)
    fun insertNote(entity: Note): Long

    @Query("SELECT * FROM notes")
    fun getAllNotes(): MutableList<Note>

    @Query("DELETE FROM notes where id = :id")
    fun deleteNote(id: Int)

    @Update
    fun updateNote(note: Note)
}