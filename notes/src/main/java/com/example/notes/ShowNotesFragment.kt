package com.example.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.databinding.FragmentShowNotesBinding
import com.example.notes.db.Dependencies
import com.example.notes.models.Note
import com.example.notes.vm.MainVM
import com.example.notes.vm.MainVmFactory

class ShowNotesFragment : Fragment() {

    private lateinit var binding: FragmentShowNotesBinding
    private lateinit var adapter: NotesListAdapter
    private lateinit var notesRecyclerView: RecyclerView
    private lateinit var vm: MainVM
    private var tracker: SelectionTracker<Note>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Dependencies.init(requireContext().applicationContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_show_notes, container, false)

        setUpViewModel()
        setUpRecyclerView(container)

        tracker = SelectionTracker.Builder(
            "mySelection",
            binding.recyclerView,
            NoteKeyProvider(adapter),
            NoteDetailsLookup(binding.recyclerView),
            StorageStrategy.createParcelableStorage(Note::class.java)
        ).withSelectionPredicate(SelectionPredicates.createSelectAnything()).build()

        adapter.tracker = tracker

        setUpFloatingButton()

        return binding.root
    }

    private fun setUpViewModel() {
        val factory = MainVmFactory(Dependencies.noteRepository)
        vm = ViewModelProvider(requireActivity(), factory)[MainVM::class.java]

        vm.notesList.observe(viewLifecycleOwner) {
            adapter.updateAdapter(it)
        }
    }

    private fun setUpFloatingButton() {
        binding.addNewNoteButton.setOnClickListener {
            openNoteEditingFragment(null)
        }
    }

    private fun setUpRecyclerView(container: ViewGroup?) {
        val notes: MutableList<Note> = vm.notesList.value ?: mutableListOf()

        adapter = NotesListAdapter(
            notes, object : OnNoteClickCallBack {
                override fun onNoteClick(position: Int) {
                    openNoteEditingFragment(position)
                }
            })

        notesRecyclerView = binding.recyclerView
        notesRecyclerView.adapter = adapter
        notesRecyclerView.layoutManager = LinearLayoutManager(container?.context)
    }

    private fun openNoteEditingFragment(position: Int?) {
        val fragment = NoteEditingFragment()
        vm.currentRvPosition = position
        requireActivity().supportFragmentManager
            .beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .replace(R.id.container, fragment)
            .setReorderingAllowed(true)
            .addToBackStack(null)
            .commit()
    }
}
