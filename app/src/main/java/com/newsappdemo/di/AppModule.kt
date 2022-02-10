package com.newsappdemo.di

import android.content.Context
import com.newsappdemo.NewsApplication
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideContext(application: NewsApplication): Context {
        return application.applicationContext
    }

}