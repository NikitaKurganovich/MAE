package com.example.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.notes.databinding.FragmentNoteEditingBinding
import com.example.notes.db.Dependencies
import com.example.notes.vm.MainVM
import com.example.notes.vm.MainVmFactory
import com.example.notes.vm.ShowMessage
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar


class NoteEditingFragment : Fragment(), ShowMessage {

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

        val factory = MainVmFactory(Dependencies.noteRepository)
        vm = ViewModelProvider(requireActivity(), factory)[MainVM::class.java]

        vm.showMessageListener = this

        binding = FragmentNoteEditingBinding.inflate(inflater)

        setUpToolBar()
        setUpPopUpMenu()
        setUpEditTexts(vm.currentRvPosition)

        return binding.root
    }

    override fun onStop() {
        val title = binding.titleTV.text.toString()
        val text = binding.textTV.text.toString()

        vm.updateOrCreateNote(title, text)

        super.onStop()
    }




    private fun setUpPopUpMenu() {
        binding.menu.setOnClickListener {
            val popup = PopupMenu(this.context, it)
            val inflater: MenuInflater = popup.menuInflater
            inflater.inflate(R.menu.toolbar_menu, popup.menu)

            popup.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.deleteItem ->{
                        Toast.makeText(this.context,"Note has been deleted", Toast.LENGTH_SHORT).show()
                        vm.deleteNoteFromPopUpMenu()
                        requireActivity().supportFragmentManager.popBackStack()
                        return@setOnMenuItemClickListener true
                    }
                    else -> return@setOnMenuItemClickListener false
                }
            }

            popup.show()
        }
    }

    private fun setUpToolBar() {
        toolBar = binding.toolBar
        toolBar.setNavigationIcon(R.drawable.baseline_arrow_back_24)

        toolBar.setNavigationOnClickListener {
            Toast.makeText(binding.root.context, "toolbar", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun setUpEditTexts(position: Int?) {
        if (position != null) {
            binding.titleTV.setText(vm.notesList.value!![position].title)
            binding.textTV.setText(vm.notesList.value!![position].description)
        }
    }

    override fun showSnackbar() {
        Snackbar.make(requireView(), R.string.note_deleted, Snackbar.LENGTH_SHORT).show()
    }

}