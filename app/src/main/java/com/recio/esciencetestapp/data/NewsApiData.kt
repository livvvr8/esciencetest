package com.recio.esciencetestapp.data

import com.google.gson.annotations.SerializedName

data class NewsApiData(
    @SerializedName("articles") val articles: List<NewsHeadline>,
    @SerializedName("status") val status: String?,
    @SerializedName("totalResults") val totalResults: Int
)