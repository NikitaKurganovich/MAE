package com.example.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.models.Note

interface OnNoteClickCallBack {
    fun onNoteClick(position: Int)
}

class NotesListAdapter(
    private var notes: MutableList<Note>,
    private val listener: OnNoteClickCallBack
) :
    RecyclerView.Adapter<NotesListAdapter.NoteViewHolder>() {

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val descriptionText: TextView = itemView.findViewById(R.id.descriptionTextView)
        val title: TextView = itemView.findViewById(R.id.titleTextView)
        val note: CardView = itemView.findViewById(R.id.noteCardView)
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
    }

    fun updateAdapter(newList: MutableList<Note>){
        notes = newList
        notifyDataSetChanged()
    }

}