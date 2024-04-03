package com.example.notes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.databinding.FragmentShowNotesBinding
import com.example.notes.models.Note

class ShowNotesFragment : Fragment() {

    private lateinit var binding: FragmentShowNotesBinding
    private lateinit var adapter: NotesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_show_notes, container, false)
        adapter = NotesListAdapter(mutableListOf(Note("ss","ss"), Note("ss","ss")))

        val rc = binding.recyclerView
        rc.adapter = adapter
        rc.layoutManager = LinearLayoutManager(container?.context)
        Log.d("fragmentOnCreateView","fr")

        return binding.root
    }

}