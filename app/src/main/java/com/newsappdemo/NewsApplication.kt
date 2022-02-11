package com.newsappdemo

import android.app.Application
import com.newsappdemo.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NewsApplication)
            modules(listOf(viewModelModule, repositoryModule, apiModule, retrofitModule))
        }
    }

}