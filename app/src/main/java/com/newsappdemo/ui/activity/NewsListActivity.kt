package com.newsappdemo.ui.activity

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.newsappdemo.R
import com.newsappdemo.utils.viewModelProvider
import com.newsappdemo.viewmodel.NewsListViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class NewsListActivity : DaggerAppCompatActivity() {
    private lateinit var newsListViewModel: NewsListViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var pageNumber = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_list)
        newsListViewModel = viewModelProvider(viewModelFactory)
        newsListViewModel.getNewsList(pageNumber.toString())
    }
}