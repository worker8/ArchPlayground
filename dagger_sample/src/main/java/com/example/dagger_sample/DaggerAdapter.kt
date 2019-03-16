package com.example.dagger_sample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.dagger_row.view.*

class DaggerAdapter : ListAdapter<String, RecyclerView.ViewHolder>(comparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DaggerViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val text = getItem(position)
        (holder as DaggerViewHolder).bind(text)
    }

    companion object {
        val comparator = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: String, newItem: String) =
                oldItem == newItem
        }
    }

    class DaggerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(text: String) {
            itemView.apply {
                textView.text = text
            }
        }

        companion object {
            fun create(parent: ViewGroup) =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.dagger_row, parent, false)
                    .let { DaggerViewHolder(it) }
        }
    }
}
