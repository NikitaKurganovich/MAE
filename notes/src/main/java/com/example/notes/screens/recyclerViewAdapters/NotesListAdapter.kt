package com.example.notes.screens.recyclerViewAdapters

import android.annotation.SuppressLint
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.database.entities.Note
import com.google.android.material.card.MaterialCardView

interface OnNoteClickCallBack {
    fun onNoteClick(position: Int)
}

class NotesListAdapter(
    private var notes: MutableList<Note>,
    private val listener: OnNoteClickCallBack
) :
    RecyclerView.Adapter<NotesListAdapter.NoteViewHolder>() {

    var tracker: SelectionTracker<Note>? = null

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val descriptionText: TextView = itemView.findViewById(R.id.descriptionTextView)
        val title: TextView = itemView.findViewById(R.id.titleTextView)
        val note: MaterialCardView = itemView.findViewById(R.id.noteCardView)

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Note> =
            object : ItemDetailsLookup.ItemDetails<Note>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): Note = notes[position]
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.notes_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.descriptionText.text = notes[position].description
        holder.title.text = notes[position].title
        holder.note.setOnClickListener {
            listener.onNoteClick(position)
        }

        tracker.let {
            val (strokeColorId, strokeWidthDp) = if (tracker!!.isSelected(notes[position])) {
                Pair(R.color.md_theme_dark_tertiary, 3)
            } else {
                Pair(R.color.md_theme_dark_outline, 1)
            }

            holder.note.strokeColor = ContextCompat.getColor(holder.itemView.context, strokeColorId)
            holder.note.strokeWidth = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                strokeWidthDp.toFloat(),
                holder.note.context.resources.displayMetrics
            ).toInt()
        }

        Log.d("tracker Selection",tracker!!.selection.size().toString())

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapter(newList: MutableList<Note>) {
        notes = newList
        notifyDataSetChanged()
    }

    fun getPosition(id: Int) = notes.indexOfFirst { it.id == id }
    fun getItem(position: Int) = notes[position]

}