package com.example.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.databinding.FragmentShowNotesBinding
import com.example.notes.models.Note

class ShowNotesFragment : Fragment() {

    private lateinit var binding: FragmentShowNotesBinding
    private lateinit var adapter: NotesListAdapter
    private lateinit var notesRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_show_notes, container, false)

        setUpRecyclerView(container)

        return binding.root
    }

    private fun setUpRecyclerView(container: ViewGroup?){
        adapter = NotesListAdapter(
            mutableListOf(
                Note("Выращивание золотого дождя", "ssesfsf"),
                Note("ss", "ss"),
                Note("ss", "ss"),
                Note("ss", "ss"),
                Note("ss", "ss"),
                Note("ss", "ss"),
                Note("ss", "ss"),
                Note("ss", "ss"),
                Note("ss", "ss"),
                Note("ss", "ss"),
                Note("ss", "ss"),
                Note("ss", "ss"),
                Note("ss", "ss"),
                Note("ss", "ss"),
                Note("ss", "ss"),
                Note("ss", "ss"),
                Note("ss", "ss")
            ), object : OnNoteClickCallBack {
                override fun onNoteClick(position: Int) {
                    val fragment = NoteEditingFragment()
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .setTransition( FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.container, fragment)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit()
                }
            })

        notesRecyclerView = binding.recyclerView
        notesRecyclerView.adapter = adapter
        notesRecyclerView.layoutManager = LinearLayoutManager(container?.context)
    }

}