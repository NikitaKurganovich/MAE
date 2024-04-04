package com.example.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar


class NoteEditingFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.actionBar?.setHomeButtonEnabled(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_note_editing, container, false)

        val toolBar = view.findViewById<MaterialToolbar>(R.id.toolBar)

        toolBar?.setNavigationIcon(R.drawable.baseline_arrow_back_24)
        toolBar?.setNavigationOnClickListener {
            Toast.makeText(view.context, "toolbar", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}