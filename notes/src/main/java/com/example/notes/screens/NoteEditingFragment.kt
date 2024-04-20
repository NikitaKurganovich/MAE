package com.example.notes.screens

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.notes.R
import com.example.notes.database.Dependencies
import com.example.notes.databinding.FragmentNoteEditingBinding
import com.example.notes.vm.MainVM
import com.example.notes.vm.MainVmFactory
import com.example.notes.vm.ShowMessage
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


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
        setUpEditTexts()

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
                    R.string.empty_note_deleted,
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

    private fun setUpEditTexts() {
        val formatter = DateTimeFormatter.ofPattern("dd MMM  yyyy       HH:mm")

        vm.selectedNote?.let {
            binding.titleTV.setText(it.title)
            binding.textTV.setText(it.description)
            binding.lastModified.text = it.lastChanges.format(formatter)
        } ?: run {
            val date = LocalDateTime.now().format(formatter)
            binding.lastModified.text = date
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
                    R.string.note_deleted,
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