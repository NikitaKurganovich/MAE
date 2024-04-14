package com.example.notes.repository

import com.example.notes.dao.NotesDao
import com.example.notes.models.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NoteRepository(private val notesDao: NotesDao) {

    suspend fun insertNewStatisticData(note: Note): Long {
        return  withContext(Dispatchers.IO) {
            notesDao.insertNote(note)
        }
    }

    suspend fun getAllNotes(): MutableList<Note> {
        return withContext(Dispatchers.IO) {
            notesDao.getAllNotes()
        }
    }

    suspend fun updateNote(note: Note) {
        withContext(Dispatchers.IO) {
            notesDao.updateNote(note)
        }
    }

    suspend fun deleteNote(id: Int) {
        withContext(Dispatchers.IO) {
            notesDao.deleteNote(id)
        }
    }

}