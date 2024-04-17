package com.example.notes

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.models.Note

class NoteDetailsLookup(private val recyclerView: RecyclerView) : ItemDetailsLookup<Note>() {
    override fun getItemDetails(e: MotionEvent): ItemDetails<Note>? {
        val view = recyclerView.findChildViewUnder(e.x, e.y)
        return if (view != null) {
            (recyclerView.getChildViewHolder(view) as NotesListAdapter.NoteViewHolder)
                .getItemDetails()
        } else null
    }
}