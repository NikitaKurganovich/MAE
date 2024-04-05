package com.example.notes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.databinding.FragmentShowNotesBinding
import com.example.notes.models.Note

class ShowNotesFragment : Fragment() {

    private lateinit var binding: FragmentShowNotesBinding
    private lateinit var adapter: NotesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_show_notes, container, false)
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
                    activity?.supportFragmentManager?.beginTransaction()?.setTransition(
                        FragmentTransaction.TRANSIT_FRAGMENT_FADE
                    )
                        ?.replace(R.id.container, fragment, "s")?.addToBackStack(null)?.commit()
                }

            })

        val rc = binding.recyclerView
        rc.adapter = adapter
        rc.layoutManager = LinearLayoutManager(container?.context)
        Log.d("fragmentOnCreateView", "fr")

        return binding.root
    }

}