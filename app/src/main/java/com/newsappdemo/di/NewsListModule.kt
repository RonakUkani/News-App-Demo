package com.newsappdemo.di

import androidx.lifecycle.ViewModel
import com.newsappdemo.viewmodel.NewsListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class NewsListModule {

    @Binds
    @IntoMap
    @ViewModelKey(NewsListViewModel::class)
    internal abstract fun bindViewModel(viewModel: NewsListViewModel): ViewModel

}