package com.example.notes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        Log.d("FragmentLifeCycle", "ON CREATE")
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("FragmentLifeCycle", "ON CREATE VIEW")

        val factory = MainVmFactory(Dependencies.noteRepository)
        vm = ViewModelProvider(requireActivity(), factory)[MainVM::class.java]
        Log.d("ViewModel object link:", vm.toString())

        vm.showMessageListener = this

        binding = FragmentNoteEditingBinding.inflate(inflater)

        setUpToolBar()
        setUpEditTexts(vm.currentRvPosition)

        return binding.root
    }

    override fun onStop() {
        Log.d("FragmentLifeCycle", "onStop")

        val title = binding.titleTV.text.toString()
        val text = binding.textTV.text.toString()

        vm.updateOrCreateNote(title, text)

        super.onStop()
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
        Log.d("setUpEditText","method called")
        if (position != null) {
            binding.titleTV.setText(vm.notesList.value!![position].title)
            binding.textTV.setText(vm.notesList.value!![position].description)
        }
    }

    override fun showSnackbar() {
        Log.d("deleteNoteM","showSnack")
        Snackbar.make(requireView(), R.string.NoteDeleted, Snackbar.LENGTH_SHORT).show()
    }

}