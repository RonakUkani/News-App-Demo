package com.newsappdemo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newsappdemo.api.CommonRepository
import com.newsappdemo.data.AppResponse
import javax.inject.Inject

class NewsListViewModel @Inject constructor(private val commonRepository: CommonRepository) :
    ViewModel() {
    val newsListSuccess = MutableLiveData<AppResponse>()
    val newsListFailure = MutableLiveData<String>()

    fun getNewsList(page: String) {
        commonRepository.getNewsList(page, viewModelScope, newsListSuccess, newsListFailure)
    }
}
