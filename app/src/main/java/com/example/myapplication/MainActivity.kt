package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val audioHelper = AudioHandler(this.applicationContext)
        val viewModel = ViewModelProvider(this)[MyViewModel::class.java]

        val adapter = HomeSurahAdapter(audioHelper) {
            viewModel.updateList(it)
        }

        val rv = findViewById<RecyclerView>(R.id.surah_of_shiekh_rv)
        rv.adapter = adapter

        viewModel.list.observe(this) {
            adapter.submitList(it)
        }

    }
}