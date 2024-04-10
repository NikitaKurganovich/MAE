package com.example.notes.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.models.Note
import com.example.notes.repository.NoteRepository
import kotlinx.coroutines.launch

class MainVM(private val noteRepository: NoteRepository) : ViewModel() {

    private var _notesList: MutableLiveData<MutableList<Note>> = MutableLiveData()
    val notesList get() = _notesList

    init {
        viewModelScope.launch {
            _notesList.value = noteRepository.getAllNotes().asReversed()
        }
    }

    private fun insertNote(title: String, text: String) {
        if (title.isNotEmpty() || text.isNotEmpty())
            viewModelScope.launch {
                noteRepository.insertNewStatisticData(Note(0, title, text))
            }
        _notesList.value?.add(Note(0, title, text))
    }

    fun updateOrCreateNote(id: Int?, title: String, text: String) {
        Log.d("tap card id:",id.toString())
        if (id==null) insertNote(title,text)
        else updateNote(notesList.value?.size!!.minus(id), title, text)
    }

    private fun updateNote(id: Int, title: String, text: String) {
        if (title.isNotEmpty() || text.isNotEmpty()) {
            viewModelScope.launch {
                noteRepository.updateNote(Note(id, title, text))
            }
        }
       // else noteRepository.deleteNote(id)
    }

}