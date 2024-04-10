package com.example.notes

import android.content.Context
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
import com.google.android.material.appbar.MaterialToolbar


class NoteEditingFragment(private val id: Int?) : Fragment() {

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

        binding = FragmentNoteEditingBinding.inflate(inflater)

        setUpToolBar()
        setUpEditTexts()

        return binding.root
    }

    private fun setUpToolBar() {
        toolBar = binding.toolBar
        toolBar.setNavigationIcon(R.drawable.baseline_arrow_back_24)
        toolBar.setNavigationOnClickListener {
            Toast.makeText(binding.root.context, "toolbar", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun setUpEditTexts() {
        if (id != null) {
            binding.titleTV.setText(vm.notesList.value!![id].title)
            binding.textTV.setText(vm.notesList.value!![id].description)
        }
    }

    override fun onAttach(context: Context) {
        Log.d("FragmentLifeCycle", "onAttach")
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d("FragmentLifeCycle", "onActivityCreated")
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        Log.d("FragmentLifeCycle", "onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d("FragmentLifeCycle", "onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d("FragmentLifeCycle", "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d("FragmentLifeCycle", "onStop")

        val title = binding.titleTV.text.toString()
        val text = binding.textTV.text.toString()

        vm.updateOrCreateNote(id, title, text)

        super.onStop()
    }

    override fun onDestroy() {
        Log.d("FragmentLifeCycle", "onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d("FragmentLifeCycle", "onDetach")
        super.onDetach()
    }

}