package com.newsappdemo.di

import com.newsappdemo.ui.activity.NewsDetailActivity
import com.newsappdemo.ui.activity.NewsListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [NewsListModule::class])
    internal abstract fun newsListActivity(): NewsListActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [NewsListModule::class])
    internal abstract fun newsDetailActivity(): NewsDetailActivity

}