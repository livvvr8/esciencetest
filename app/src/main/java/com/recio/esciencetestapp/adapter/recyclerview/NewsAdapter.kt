package com.recio.esciencetestapp.adapter.recyclerview

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recio.esciencetestapp.activities.news.NewsActivity
import com.recio.esciencetestapp.adapter.viewholder.NewsViewHolder
import com.recio.esciencetestapp.data.NewsHeadline
import com.recio.esciencetestapp.databinding.ItemNewsBinding
import com.squareup.picasso.Picasso

class NewsAdapter(
    private val newsHeadlineList: MutableList<NewsHeadline>,
    val context: Context,
) :
    RecyclerView.Adapter<NewsViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(ItemNewsBinding.inflate(
            LayoutInflater.from(context), viewGroup, false).root)
    }

    init {
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        val newsHeadline: NewsHeadline = newsHeadlineList[position]

        holder.newsTitle.text = newsHeadline.title
        holder.newsSource.text = newsHeadline.source.name

        val newsImage = newsHeadline.urlToImage?:""
        if (newsImage.isNotEmpty()) {
            Picasso.get().load(newsHeadline.urlToImage).into(holder.newsImage)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, NewsActivity::class.java)
            intent.putExtra("newsArticle", newsHeadline)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return newsHeadlineList.size
    }

}