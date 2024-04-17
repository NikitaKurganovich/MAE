package com.example.notes

import androidx.recyclerview.selection.ItemKeyProvider
import com.example.notes.models.Note

class NoteKeyProvider(private val adapter: NotesListAdapter) : ItemKeyProvider<Note>(SCOPE_CACHED) {
    override fun getKey(position: Int): Note {
        return adapter.getItem(position)
    }

    override fun getPosition(key: Note): Int {
        return adapter.getPosition(key.id)
    }
}