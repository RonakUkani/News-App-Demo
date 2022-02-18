package com.newsappdemo.viewmodel

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.newsappdemo.di.apiModule
import com.newsappdemo.di.repositoryModule
import com.newsappdemo.di.retrofitModule
import com.newsappdemo.di.viewModelModule
import io.kotest.core.spec.style.FunSpec
import io.kotest.koin.KoinExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class NewsListViewModelTest : FunSpec(), KoinTest {

    override fun extensions() = listOf(KoinExtension(listOf(viewModelModule, repositoryModule, apiModule, retrofitModule)))
    private val viewModel by inject<NewsListViewModel>()

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Before
    fun before() {
        startKoin {
            extensions()
        }
    }

    @Test
    fun getResult(){
        viewModel.getNewsList("1")
        val result = viewModel.newsListSuccess.getOrAwaitValue().articles
        Log.e("result",result.size.toString())
        assertThat(result!=null).isTrue()
    }

    @After
    fun after(){
        stopKoin()
    }

}