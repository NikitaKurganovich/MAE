package com.example.notes.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.selection.Selection
import com.example.notes.database.entities.Note
import com.example.notes.repositories.NoteRepository
import kotlinx.coroutines.launch
import java.time.LocalDateTime

interface ShowMessage {
    fun showSnackbar()
}

class MainVM(private val noteRepository: NoteRepository) : ViewModel() {

    private var _notesList: MutableLiveData<MutableList<Note>> = MutableLiveData()
    val notesList get() = _notesList

    private var listPosition: Int? = null
    private var isNoteDeleted = false
    var selectedNote: Note? = null

    init {
        viewModelScope.launch {
            _notesList.value = noteRepository.getAllNotes().asReversed()
        }
    }

    private fun insertNote(title: String, text: String) {
        if (title.isNotEmpty() || text.isNotEmpty()) {
            viewModelScope.launch {
                val newNoteId =
                    noteRepository.insertNewStatisticData(Note(0, title, text, LocalDateTime.now()))
                val updatedNotesList = _notesList.value?.toMutableList()
                updatedNotesList?.add(0, Note(newNoteId.toInt(), title, text, LocalDateTime.now()))
                _notesList.value = updatedNotesList!!
            }
        }
    }

    fun updateOrCreateNote(title: String, text: String, showMessageListener: ShowMessage) {
        if (!isNoteDeleted) {
            if (selectedNote == null) insertNote(title, text)
            else if (title.isEmpty() && text.isEmpty()) {
                showMessageListener.showSnackbar()
                deleteNote()
            } else updateNote(title, text)
        }
        isNoteDeleted = false
    }

    /** The noteRepository.updateNote(updatedNote) method should be at the bottom, the _notesList update can be taken out of the coroutineScope **/
    private fun updateNote(title: String, text: String) {
        selectedNote?.let { note ->
            if (title.isNotEmpty() || text.isNotEmpty()) {
                val updatedNote = Note(
                    note.id,
                    title,
                    text,
                    LocalDateTime.now()
                )
                viewModelScope.launch {
                    listPosition?.let { listPosition ->
                        _notesList.value?.get(listPosition)?.title = title
                        _notesList.value?.get(listPosition)?.description = text
                    }
                    noteRepository.updateNote(updatedNote)
                }
            }
        }
    }

    private fun deleteNote() {
        listPosition?.let { listPosition ->
            val id = notesList.value!![listPosition].id
            _notesList.value?.removeAt(listPosition)
            viewModelScope.launch {
                noteRepository.deleteNote(id)
            }
        }
    }

    fun deleteNoteFromPopupMenu(showMessageListener: ShowMessage) {
        listPosition?.let {
            deleteNote()
            isNoteDeleted = true
            showMessageListener.showSnackbar()
        }
    }

    fun deleteSelectedNotes(selectedNotes: Selection<Note>) {
        val newList = notesList.value!!
        selectedNotes.forEach { note ->
            newList.removeIf { it.id == note.id }
            viewModelScope.launch {
                noteRepository.deleteNote(note.id)
            }
        }
        _notesList.value = newList
    }

    fun setUpNote(position: Int?) {
        position?.let {
            selectedNote = notesList.value!![it]
            listPosition = it
        } ?: run {
            selectedNote = null
            listPosition = null
        }
    }
}