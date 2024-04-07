package com.example.notes.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.notes.models.NoteEntity

@Dao
interface NotesDao {
    @Insert(entity = NoteEntity::class)
    fun insertNote(entity: NoteEntity)

    @Query("SELECT * FROM notes")
    fun getAllNotes(): MutableList<NoteEntity>

    @Delete
    fun deleteNote(note: NoteEntity)

    @Update
    fun updateNote(note: NoteEntity)
}