package com.newsappdemo.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.newsappdemo.R
import com.newsappdemo.api.ApiService
import com.newsappdemo.api.CommonRepository
import com.newsappdemo.viewmodel.NewsListViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {
    viewModel {
        NewsListViewModel(get())
    }
}

val repositoryModule = module {
    factory {
        CommonRepository(get(), get())
    }
}

val apiModule = module {
    fun provideUseApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    single { provideUseApi(get()) }
}


val retrofitModule = module {

    fun provideGson(): Gson {
        return GsonBuilder().setVersion(1.0).create()
    }

    fun provideHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        return okHttpClientBuilder.build()
    }

    fun provideRetrofit(factory: Gson, client: OkHttpClient,context : Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(context.getString(R.string.apiUrl))
            .addConverterFactory(GsonConverterFactory.create(factory))
            .client(client)
            .build()
    }

    single { provideGson() }
    single { provideHttpClient() }
    single { provideRetrofit(get(), get(), get()) }
}