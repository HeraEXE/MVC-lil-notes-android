package com.hera.lil_notes.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hera.lil_notes.R
import com.hera.lil_notes.data.model.Model

class MainAdapter(private val listener: Listener) : ListAdapter<Model, MainAdapter.ViewHolder>(DiffCallback()) {

    interface Listener {

        fun onItemClick(model: Model)
    }

    class DiffCallback : DiffUtil.ItemCallback<Model>() {

        override fun areItemsTheSame(oldItem: Model, newItem: Model): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Model, newItem: Model): Boolean {
            return oldItem == newItem
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val cardViewName: CardView = view.findViewById(R.id.cardViewName)
        private val textName: TextView = view.findViewById(R.id.textName)

        fun initView(model: Model) {
            textName.text = model.name
            cardViewName.setOnClickListener { listener.onItemClick(model) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_name, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: Model = currentList[position]
        holder.initView(model)
    }
}