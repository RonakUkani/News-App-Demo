package com.newsappdemo.api

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.newsappdemo.R
import com.newsappdemo.data.AppResponse
import com.newsappdemo.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class CommonRepository constructor(var context: Context, private val apiService: ApiService) : BaseDataSource(context) {

    fun getNewsList(
        page: String,
        viewModelScope: CoroutineScope,
        newsListSuccess: MutableLiveData<AppResponse>,
        newsListFailure: MutableLiveData<String>, ) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                apiService.getNewsList(Constants.KEY_COUNTRY,
                    Constants.KEY_CATEGORY,
                    context.getString(R.string.apiKey),
                    page,
                    Constants.KEY_DEFAULT_PAGE_SIZE
                )
            }.fold(
                {
                    newsListSuccess.postValue(it)
                }, {
                    handleError(it) { error ->
                        newsListFailure.postValue(error)
                    }
                })
        }
    }

}