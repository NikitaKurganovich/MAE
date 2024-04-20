package com.example.notes.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.database.Dependencies
import com.example.notes.database.entities.Note
import com.example.notes.databinding.FragmentShowNotesBinding
import com.example.notes.screens.recyclerViewAdapters.NoteDetailsLookup
import com.example.notes.screens.recyclerViewAdapters.NoteKeyProvider
import com.example.notes.screens.recyclerViewAdapters.NotesListAdapter
import com.example.notes.screens.recyclerViewAdapters.OnNoteClickCallBack
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
        setUpToolBar()
        setUpRecyclerViewSelectionTracker()
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

    private fun setUpRecyclerViewSelectionTracker() {
        tracker = SelectionTracker.Builder(
            "mySelection",
            binding.recyclerView,
            NoteKeyProvider(adapter),
            NoteDetailsLookup(binding.recyclerView),
            StorageStrategy.createParcelableStorage(Note::class.java)
        ).withSelectionPredicate(SelectionPredicates.createSelectAnything()).build()

        adapter.tracker = tracker

        tracker?.addObserver(object : SelectionTracker.SelectionObserver<Note>() {
            override fun onSelectionChanged() {
                super.onSelectionChanged()
                val materialToolbar = binding.materialToolbar
                val items = tracker!!.selection.size()
                val menuButton = binding.menu

                if (items > 0) {
                    menuButton.visibility = View.VISIBLE
                    materialToolbar.title = items.toString()

                    materialToolbar.setNavigationIcon(R.drawable.baseline_close_24)
                    materialToolbar.setNavigationOnClickListener {
                        tracker!!.clearSelection()
                    }
                } else {
                    menuButton.visibility = View.INVISIBLE
                    materialToolbar.title =
                        ContextCompat.getString(requireContext(), R.string.app_name)
                    materialToolbar.navigationIcon = null
                }
            }
        })
    }

    private fun setUpToolBar() {
        binding.menu.setOnClickListener {
            val popup = PopupMenu(this.context, it)
            val menuInflater = popup.menuInflater
            menuInflater.inflate(R.menu.toolbar_menu_show_fragment, popup.menu)
            popup.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.deleteItem -> {

                        vm.deleteSelectedNotes(tracker?.selection!!)
                        tracker?.clearSelection()
                        return@setOnMenuItemClickListener true
                    }

                    else -> return@setOnMenuItemClickListener false
                }
            }
            popup.show()
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
        vm.setUpNote(position)
        requireActivity().supportFragmentManager
            .beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .replace(R.id.container, fragment)
            .setReorderingAllowed(true)
            .addToBackStack(null)
            .commit()
    }
}
