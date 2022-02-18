package com.newsappdemo

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.newsappdemo.api.ApiService
import com.newsappdemo.di.apiModule
import com.newsappdemo.di.repositoryModule
import com.newsappdemo.di.retrofitModule
import com.newsappdemo.di.viewModelModule
import com.newsappdemo.utils.Constants
import io.kotest.core.spec.style.FunSpec
import io.kotest.koin.KoinExtension
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.inject

@RunWith(AndroidJUnit4::class)
class MyTest : FunSpec(), KoinTest {

    override fun extensions() = listOf(KoinExtension(listOf(viewModelModule, repositoryModule, apiModule, retrofitModule)))

    val service by inject<ApiService>()
    val context by inject<Context>()

    init {
        test("Make a test with Koin") {
        val result =  service.getNewsList(Constants.KEY_COUNTRY,
                Constants.KEY_CATEGORY,
                context.getString(R.string.apiKey),
                "1",
                Constants.KEY_DEFAULT_PAGE_SIZE).articles
            assert(result!=null)
        }
    }


}