package com.example.notes.repository

import com.example.notes.dao.NotesDao
import com.example.notes.models.NoteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NoteRepository(private val notesDao: NotesDao) {

    suspend fun insertNewStatisticData(noteEntity: NoteEntity) {
        withContext(Dispatchers.IO) {
            notesDao.insertNote(noteEntity)
        }
    }

}