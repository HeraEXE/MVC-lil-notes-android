package com.hera.lil_notes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.hera.lil_notes.R
import com.hera.lil_notes.data.repository.Repository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    private lateinit var repository: Repository

    private lateinit var recyclerView: RecyclerView
    private lateinit var editTextName: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recycler)
        editTextName = findViewById(R.id.editTextName)
        saveButton = findViewById(R.id.saveButton)
    }
}