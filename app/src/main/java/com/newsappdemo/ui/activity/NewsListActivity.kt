package com.newsappdemo.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.newsappdemo.R
import com.newsappdemo.data.AppResponse
import com.newsappdemo.ui.adapter.NewsListAdapter
import com.newsappdemo.utils.PaginationScrollListener
import com.newsappdemo.utils.showToast
import com.newsappdemo.utils.viewModelProvider
import com.newsappdemo.viewmodel.NewsListViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_news_list.*
import javax.inject.Inject

class NewsListActivity : DaggerAppCompatActivity() {
    private lateinit var newsListViewModel: NewsListViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var pageNumber = 1
    private var newsList: MutableList<AppResponse.Article> = mutableListOf()
    private val adapter = NewsListAdapter(newsList) {}
    private var isLoading = false
    private var isLastPage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_list)
        newsListViewModel = viewModelProvider(viewModelFactory)
        setAdapter()
        newsListViewModel.getNewsList(pageNumber.toString())
        observeData()
    }

    private fun setAdapter() {
        recyclerViewNews.adapter = adapter
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerViewNews.layoutManager = linearLayoutManager

        recyclerViewNews.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {
            override fun loadMoreItems() {
                isLoading = true
                pageNumber += 1
                newsListViewModel.getNewsList(pageNumber.toString())
                isLoading = false
            }

            override var isLastPage: Boolean = this@NewsListActivity.isLastPage

            override var isLoading: Boolean = this@NewsListActivity.isLoading

        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeData() {
        newsListViewModel.newsListSuccess.observe(this, Observer {
            if (pageNumber > 1){
                this@NewsListActivity.isLoading = false
            }
            if (it.status.equals("ok")) {
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
            }else{
                isLastPage = true
            }
            adapter.notifyDataSetChanged()
        })

        newsListViewModel.newsListFailure.observe(this, Observer {
            if (pageNumber > 1){
                this@NewsListActivity.isLoading = false
            }
            isLastPage = true
        })
    }
}