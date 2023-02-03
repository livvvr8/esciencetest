package com.recio.esciencetestapp.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.recio.esciencetestapp.databinding.ItemNewsBinding

class NewsViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemNewsBinding.bind(view)
    val newsImage = binding.image
    val newsTitle = binding.textTitle
    val newsSource = binding.textSource
}