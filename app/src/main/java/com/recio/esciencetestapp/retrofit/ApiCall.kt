package com.recio.esciencetestapp.retrofit

import com.recio.esciencetestapp.data.NewsApiData
import retrofit2.Call
import retrofit2.http.*

interface ApiCall {
    @GET("/v2/top-headlines")
    fun getNews(@Query("country") country : String, @Query("category") category : String, @Query("apiKey") key : String) : Call<NewsApiData>
}