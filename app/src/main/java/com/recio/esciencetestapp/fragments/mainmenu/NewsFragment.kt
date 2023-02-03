package com.recio.esciencetestapp.fragments.mainmenu

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.recio.esciencetestapp.R
import com.recio.esciencetestapp.adapter.recyclerview.NewsAdapter
import com.recio.esciencetestapp.data.NewsApiData
import com.recio.esciencetestapp.data.NewsHeadline
import com.recio.esciencetestapp.databinding.FragmentNewsBinding
import com.recio.esciencetestapp.fragments.basefragment.BaseFragment
import com.recio.esciencetestapp.retrofit.APIRequest
import com.recio.esciencetestapp.retrofit.ApiCall
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class NewsFragment : BaseFragment<FragmentNewsBinding>(FragmentNewsBinding::inflate) {

    private var newsList: MutableList<NewsHeadline> = mutableListOf()
    lateinit var newsItemAdapter: NewsAdapter

    private val newsCategory: String = "general"
    var generalNews: MutableList<NewsHeadline> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNetworkConnectionStatusSnackBar()

        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerviewNews.layoutManager = LinearLayoutManager(requireContext())

        newsItemAdapter = NewsAdapter(generalNews, requireContext())
        binding.recyclerviewNews.adapter = newsItemAdapter

        requestNews(newsCategory)

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.progressBar.visibility = View.VISIBLE
            binding.textError.visibility = View.GONE

            Handler(Looper.getMainLooper()).postDelayed({
                requestNews(newsCategory)
                binding.progressBar.visibility = View.GONE
            }, 2000)
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun requestNews(category: String) {
        val call = APIRequest.getInstance().create(ApiCall::class.java)
            .getNews("ph", category, getString(R.string.news_api_key))

        call.enqueue(object : Callback<NewsApiData> {
            override fun onResponse(
                call: Call<NewsApiData>,
                response: Response<NewsApiData>
            ) {
                if (response.isSuccessful) {
                    binding.textError.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE

                    val body = response.body()

                    body?.articles?.forEach {
                        generalNews.add(
                            NewsHeadline(
                                it.source,
                                it.author,
                                it.title,
                                it.description,
                                it.url,
                                it.urlToImage,
                                it.publishedAt,
                                it.content
                            )
                        )
                    }
                } else {
                    val jsonObj: JSONObject?
                    try {
                        jsonObj = response.errorBody()?.string()?.let { JSONObject(it) }
                        if (jsonObj != null) {
                            newsList = ArrayList<NewsHeadline>()
                        }
                    } catch (e: JSONException) {
                        binding.textError.visibility = View.VISIBLE
                        displayDialogBoxMessage("JSONException", "" + e.message,
                            getString(R.string.okay))
                    }

                    //delete this later
                    Log.d("Response Body", "" + response.errorBody().toString() + " " + response.code().toString() + " " + response.message().toString())
                    displayDialogBoxMessage(
                        getString(R.string.error),
                        response.errorBody().toString() + " " + response.code().toString(),
                        getString(R.string.close))

                }
            }

            override fun onFailure(call: Call<NewsApiData>, t: Throwable) {
                Log.d("err_msg", "msg" + t.localizedMessage)
                displayDialogBoxMessage(getString(R.string.error), t.localizedMessage, getString(R.string.okay))
                binding.textError.visibility = View.GONE
            }
        })
    }
}