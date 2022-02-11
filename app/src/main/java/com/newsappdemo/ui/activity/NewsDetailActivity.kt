package com.newsappdemo.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
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
import kotlinx.android.synthetic.main.activity_news_detail_activity.*
import kotlinx.android.synthetic.main.layout_news_detail_toolbar.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsDetailActivity : AppCompatActivity() {
    private val newsListViewModel by viewModel<NewsListViewModel>()
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
        bindData()

        /**Here bind recyclerview Adapter for bind news list*/
        setAdapter()

        /**Here call the api for getting news list*/
        callNewsListApi()

        /**Here observing api's live-data from viewmodel*/
        observeData()
    }

    private fun bindData() {
        textViewUrl.text = article.url
        imageviewBackArrow.setOnClickListener(this::onClick)

        /**Here we hold article object from previous screen and this is the our news detail
         * so puted in newsList and bind as a first item on recyclerview*/
        article.newsEnum = AppResponse.Article.NewsEnum.NEWS_DETAIL
        newsList.add(article)
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

        /**Here adding pagination in recyclerview*/
        pagination  = object : PaginationScrollListener(linearLayoutManager) {
            override fun loadMoreItems() {
                isLoading = true
                adapter.addLoading()
                callNewsListApi()
            }
            override var isLastPage: Boolean = this@NewsDetailActivity.isLastPage
            override var isLoading: Boolean = this@NewsDetailActivity.isLoading
        }
        recyclerViewNewsDetail.addOnScrollListener(pagination)
    }

    private fun callNewsListApi() {
        newsListViewModel.getNewsList(pageNumber.toString())
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun observeData() {
        /**API Success live data*/
        newsListViewModel.newsListSuccess.observe(this, Observer {
            if (pagination.isLoading){
                pagination.isLoading = false
                adapter.removeLoading()
            }

            if (it.status.equals("ok")) {
                pageNumber += 1
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

        /**API Failure live data*/
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