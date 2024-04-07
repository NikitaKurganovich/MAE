package com.example.notes.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.models.NoteEntity
import com.example.notes.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteEditingVM(private val noteRepository: NoteRepository) : ViewModel() {


    fun insertNote() {
        viewModelScope.launch {
            noteRepository.insertNewStatisticData(noteEntity = NoteEntity(0, "s", "s"))
        }
    }
}