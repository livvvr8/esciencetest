package com.recio.esciencetestapp.activities.news

import android.os.Bundle
import com.recio.esciencetestapp.activities.baseactivity.BaseActivity
import com.recio.esciencetestapp.data.NewsHeadline
import com.recio.esciencetestapp.databinding.ActivityNewsBinding
import com.squareup.picasso.Picasso

class NewsActivity : BaseActivity<ActivityNewsBinding>() {

    private lateinit var newsHeadline: NewsHeadline

    override fun getActivityBinding(): ActivityNewsBinding {
        return ActivityNewsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showNetworkConnectionStatusSnackBar()

        newsHeadline = intent.getParcelableExtra("newsArticle")!!

        Picasso.get().load(newsHeadline.urlToImage).into(binding.imageUpload)
        binding.title.text = newsHeadline.title
        binding.author.text = newsHeadline.source.name
        binding.date.text = newsHeadline.publishedAt
        binding.description.text = newsHeadline.description

    }
}