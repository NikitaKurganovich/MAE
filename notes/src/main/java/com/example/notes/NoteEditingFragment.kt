package com.example.notes

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.notes.databinding.FragmentNoteEditingBinding
import com.example.notes.db.Dependencies
import com.example.notes.vm.MainVM
import com.example.notes.vm.MainVmFactory
import com.example.notes.vm.ShowMessage
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar


class NoteEditingFragment : Fragment() {

    private lateinit var binding: FragmentNoteEditingBinding
    private lateinit var vm: MainVM

    private lateinit var toolBar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Dependencies.init(requireContext().applicationContext)
        activity?.actionBar?.setHomeButtonEnabled(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentNoteEditingBinding.inflate(inflater)

        setUpViewModel()
        setUpEditTexts(vm.currentRvPosition)

        setUpToolBar()
        setUpPopUpMenu()

        return binding.root
    }

    override fun onStop() {
        val title = binding.titleTV.text.toString()
        val text = binding.textTV.text.toString()

        vm.updateOrCreateNote(title, text, object : ShowMessage {
            override fun showSnackbar() {
                Snackbar.make(
                    requireActivity().findViewById(R.id.container),
                    R.string.note_deleted,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        })

        super.onStop()
    }

    private fun setUpViewModel() {
        val factory = MainVmFactory(Dependencies.noteRepository)
        vm = ViewModelProvider(requireActivity(), factory)[MainVM::class.java]
    }

    private fun setUpToolBar() {
        toolBar = binding.toolBar
        toolBar.setNavigationIcon(R.drawable.baseline_arrow_back_24)

        toolBar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun setUpEditTexts(position: Int?) {
        if (position != null) {
            binding.titleTV.setText(vm.notesList.value!![position].title)
            binding.textTV.setText(vm.notesList.value!![position].description)
        }
    }

    private fun setUpPopUpMenu() {
        binding.menu.setOnClickListener {
            val popup = PopupMenu(this.context, it)
            val inflater: MenuInflater = popup.menuInflater
            inflater.inflate(R.menu.toolbar_menu, popup.menu)

            popup.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.deleteItem -> {
                        deleteNote()
                        return@setOnMenuItemClickListener true
                    }

                    R.id.shareItem -> {
                        setUpShareSheet()
                        return@setOnMenuItemClickListener true
                    }

                    else -> return@setOnMenuItemClickListener false
                }
            }
            popup.show()
        }
    }

    private fun deleteNote() {
        vm.deleteNoteFromPopupMenu(object : ShowMessage {
            override fun showSnackbar() {
                Snackbar.make(
                    requireActivity().findViewById(R.id.container),
                    R.string.delete_note,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        })
        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun setUpShareSheet() {
        val shareText = binding.textTV.text
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

}