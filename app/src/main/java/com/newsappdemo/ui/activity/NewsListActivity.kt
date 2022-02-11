package com.newsappdemo.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.newsappdemo.R
import com.newsappdemo.data.AppResponse
import com.newsappdemo.ui.adapter.NewsListAdapter
import com.newsappdemo.utils.Constants
import com.newsappdemo.utils.PaginationScrollListener
import com.newsappdemo.utils.showToast
import com.newsappdemo.viewmodel.NewsListViewModel
import kotlinx.android.synthetic.main.activity_news_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsListActivity : AppCompatActivity() {

    private val newsListViewModel by viewModel<NewsListViewModel>()

    private var pageNumber = 1
    private var newsList: MutableList<AppResponse.Article> = mutableListOf()
    private val adapter = NewsListAdapter(newsList) {
        openNewsDetailScreen(it)
    }
    private var isLoading = false
    private var isLastPage = false
    lateinit var pagination: PaginationScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_list)

        /**Here bind recyclerview Adapter for bind news list*/
        setAdapter()

        /**Here call the api for getting news list*/
        callNewsListApi()

        /**Here observing api's live-data from viewmodel*/
        observeData()
    }

    private fun setAdapter() {
        recyclerViewNews.adapter = adapter
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerViewNews.layoutManager = linearLayoutManager

        /**Here adding pagination in recyclerview*/
        pagination = object : PaginationScrollListener(linearLayoutManager) {
            override fun loadMoreItems() {
                isLoading = true
                adapter.addLoading()
                callNewsListApi()
            }

            override var isLastPage: Boolean = this@NewsListActivity.isLastPage

            override var isLoading: Boolean = this@NewsListActivity.isLoading
        }
        recyclerViewNews.addOnScrollListener(pagination)
    }

    private fun callNewsListApi() {
        newsListViewModel.getNewsList(pageNumber.toString())
    }

    private fun openNewsDetailScreen(article: AppResponse.Article) {
        startActivity(Intent(this, NewsDetailActivity::class.java).putExtra(Constants.KEY_NEWS_DATA,
            article))
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeData() {
        /**API Success live data*/
        newsListViewModel.newsListSuccess.observe(this, Observer {
            if (pagination.isLoading) {
                pagination.isLoading = false
                adapter.removeLoading()
            }

            /**Here is the logic of display Top news and popular news like,
             * Here differentiate API response's like, first record is top news and another is popular news
             * so pass that enum in model and later by forget in adapter with appropriate view*/
            if (it.status.equals("ok")) {
                pageNumber += 1
                if (newsList.isEmpty()) {
                    for (i in 0 until it.articles.size) {
                        if (i == 0) {
                            it.articles[0].isShowTitle = true
                            it.articles[0].newsEnum = AppResponse.Article.NewsEnum.TOP_NEWS
                        } else if (i == 1) {
                            it.articles[1].isShowTitle = true
                        }
                    }
                }
                newsList.addAll(it?.articles!!)
            } else {
                pagination.isLoading = false
                adapter.removeLoading()
                pagination.isLastPage = true
            }
            adapter.notifyDataSetChanged()
        })

        /**API Failure live data*/
        newsListViewModel.newsListFailure.observe(this, Observer {
            if (pageNumber > 1 && pagination.isLoading) {
                pagination.isLoading = false
                adapter.removeLoading()
            } else {
                showToast(it)
            }
            pagination.isLastPage = true
        })
    }
}