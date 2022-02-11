package com.newsappdemo.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.newsappdemo.R
import com.newsappdemo.data.AppResponse
import com.newsappdemo.ui.adapter.NewsListAdapter
import com.newsappdemo.utils.Constants
import com.newsappdemo.utils.PaginationScrollListener
import com.newsappdemo.utils.showToast
import com.newsappdemo.utils.viewModelProvider
import com.newsappdemo.viewmodel.NewsListViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_news_detail_activity.*
import kotlinx.android.synthetic.main.layout_news_detail_toolbar.*
import javax.inject.Inject

class NewsDetailActivity : DaggerAppCompatActivity() {
    private lateinit var newsListViewModel: NewsListViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var pageNumber = 1
    private var newsList: MutableList<AppResponse.Article> = mutableListOf()
    private val adapter = NewsListAdapter(newsList) {}
    private var isLoading = false
    private var isLastPage = false
    lateinit var pagination : PaginationScrollListener
    private val article : AppResponse.Article by lazy {
        return@lazy if (intent?.hasExtra(Constants.KEY_NEWS_DATA)!!){
            intent?.getParcelableExtra(Constants.KEY_NEWS_DATA)!!
        }else{
            AppResponse.Article()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail_activity)
        newsListViewModel = viewModelProvider(viewModelFactory)
        textViewUrl.text = article.url
        imageviewBackArrow.setOnClickListener(this::onClick)

        article.newsEnum = AppResponse.Article.NewsEnum.NEWS_DETAIL
        newsList.add(article)
        setAdapter()

        newsListViewModel.getNewsList(pageNumber.toString())
        observeData()
    }

    private fun onClick(view: View) {
        when (view.id) {
            R.id.imageviewBackArrow -> {
                finish()
            }
        }
    }

    private fun setAdapter() {
        recyclerViewNewsDetail.adapter = adapter
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerViewNewsDetail.layoutManager = linearLayoutManager
        pagination  = object : PaginationScrollListener(linearLayoutManager) {
            override fun loadMoreItems() {
                isLoading = true
                adapter.addLoading()
                newsListViewModel.getNewsList(pageNumber.toString())
            }
            override var isLastPage: Boolean = this@NewsDetailActivity.isLastPage
            override var isLoading: Boolean = this@NewsDetailActivity.isLoading
        }
        recyclerViewNewsDetail.addOnScrollListener(pagination)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeData() {
        newsListViewModel.newsListSuccess.observe(this, Observer {
            if (pagination.isLoading){
                pagination.isLoading = false
                adapter.removeLoading()
            }
            if (it.status.equals("ok")) {
                if (it.articles.size > 1 && pageNumber == 1) {
                    it.articles[0].isShowTitle = true
                }
                newsList.addAll(it?.articles!!)
            } else {
                pagination.isLoading = false
                adapter.removeLoading()
                pagination.isLastPage = true
            }
            adapter.notifyDataSetChanged()
        })

        newsListViewModel.newsListFailure.observe(this, Observer {
            if (pageNumber > 1 && pagination.isLoading){
                pagination.isLoading = false
                adapter.removeLoading()
            } else {
                showToast(it)
            }
            pagination.isLastPage = true
        })
    }


}