package com.example.notes.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.models.Note
import com.example.notes.repository.NoteRepository
import kotlinx.coroutines.launch

interface ShowMessage {
    fun showSnackbar()
}

class MainVM(private val noteRepository: NoteRepository) : ViewModel() {

    private var _notesList: MutableLiveData<MutableList<Note>> = MutableLiveData()
    val notesList get() = _notesList

    var currentRvPosition: Int? = null
    private var isNoteDeleted = false

    init {
        viewModelScope.launch {
            _notesList.value = noteRepository.getAllNotes().asReversed()

        }
    }

    private fun insertNote(title: String, text: String) {
        if (title.isNotEmpty() || text.isNotEmpty()) {
            viewModelScope.launch {
                val newNoteId = noteRepository.insertNewStatisticData(Note(0, title, text))
                val updatedNotesList = _notesList.value?.toMutableList()
                updatedNotesList?.add(0, Note(newNoteId.toInt(), title, text))
                _notesList.value = updatedNotesList!!
            }
        }
    }

    fun updateOrCreateNote(title: String, text: String, showMessageListener: ShowMessage) {
        if (!isNoteDeleted) {
            if (currentRvPosition == null) insertNote(title, text)
            else {
                if (title.isEmpty() && text.isEmpty()) {
                    showMessageListener.showSnackbar()
                    deleteNote(currentRvPosition!!)
                } else updateNote(currentRvPosition!!, title, text)
            }
        }
        isNoteDeleted = false
    }

    private fun updateNote(position: Int, title: String, text: String) {

        // Log.d("NotePosition", " position - $position")

        if (title.isNotEmpty() || text.isNotEmpty()) {
            val updatedNote = Note(
                notesList.value!![position].id,
                title,
                text
            )
            viewModelScope.launch {
                noteRepository.updateNote(updatedNote)
            }
            _notesList.value!![position].title = title
            _notesList.value!![position].description = text
        }
    }

    private fun deleteNote(position: Int) {
        val id = notesList.value!![position].id
        _notesList.value?.removeAt(position)
        viewModelScope.launch {
            noteRepository.deleteNote(id)
        }
    }

    fun deleteNoteFromPopupMenu(showMessageListener: ShowMessage) {
        val position = currentRvPosition
        if (position != null) {
            val id = notesList.value!![position].id
            _notesList.value?.removeAt(position)
            viewModelScope.launch {
                noteRepository.deleteNote(id)
            }
        }
        isNoteDeleted = true
        showMessageListener.showSnackbar()
    }

}