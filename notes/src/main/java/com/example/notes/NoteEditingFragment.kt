package com.example.notes

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.notes.databinding.FragmentNoteEditingBinding
import com.example.notes.db.Dependencies
import com.example.notes.vm.NoteEditingVM
import com.google.android.material.appbar.MaterialToolbar


class NoteEditingFragment : Fragment() {

    private lateinit var binding: FragmentNoteEditingBinding
    private lateinit var toolBar: MaterialToolbar
    private val vm by lazy { NoteEditingVM(Dependencies.noteRepository) }

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
        binding = FragmentNoteEditingBinding.inflate(inflater)
        setUpToolBar()

        return binding.root
    }

    private fun setUpToolBar() {
        toolBar = binding.toolBar
        toolBar.setNavigationIcon(R.drawable.baseline_arrow_back_24)
        toolBar.setNavigationOnClickListener {
            Toast.makeText(binding.root.context, "toolbar", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.popBackStack()
            vm.insertNote()
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