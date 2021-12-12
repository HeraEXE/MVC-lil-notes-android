package com.hera.lil_notes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Display
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hera.lil_notes.R
import com.hera.lil_notes.data.model.Model
import com.hera.lil_notes.data.repository.Repository
import com.hera.lil_notes.ext.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainAdapter.Listener {

    @Inject
    lateinit var repository: Repository

    private val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    private val mainAdapter: MainAdapter = MainAdapter(this)

    private lateinit var recyclerView: RecyclerView
    private lateinit var editTextName: EditText
    private lateinit var saveButton: Button
    private lateinit var textNoModels: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        getAllModels()
    }

    private fun initView() {
        recyclerView = findViewById(R.id.recycler)
        editTextName = findViewById(R.id.editTextName)
        saveButton = findViewById(R.id.saveButton)
        textNoModels = findViewById(R.id.textNoModels)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mainAdapter
        }
        saveButton.setOnClickListener { onSave() }
    }

    private fun onSave() {
        val name: String = editTextName.text.toString()
        if (name.isEmpty()) {
            Toast.makeText(this, "Oh Oh", Toast.LENGTH_SHORT).show()
            return
        }
        val model = Model(name)
        addModel(model)
        editTextName.text = null
        editTextName.hideKeyboard()
    }

    private fun addModel(model: Model) {
        val disposable: Disposable = repository.addModel(model)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { getAllModels() }
        compositeDisposable.add(disposable)
    }

    private fun deleteModel(model: Model) {
        val disposable: Disposable = repository.deleteModel(model)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { getAllModels() }
        compositeDisposable.add(disposable)
    }

    private fun getAllModels() {
        val disposable: Disposable = repository.getAllModels()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    if (it.isEmpty()) {
                        textNoModels.visibility = View.VISIBLE
                    } else {
                        textNoModels.visibility = View.GONE
                    }
                    mainAdapter.submitList(it)
                },
                onError = {
                    it.printStackTrace()
                }
            )
        compositeDisposable.add(disposable)
    }

    override fun onItemClick(model: Model) {
        deleteModel(model)
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }
}