package com.example.notes.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.notes.R
import com.example.notes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        if (savedInstanceState == null) {
            val fragment = ShowNotesFragment()

            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.container, fragment, "s")
            fragmentTransaction.commit()

            setContentView(binding.root)
        }
    }
}