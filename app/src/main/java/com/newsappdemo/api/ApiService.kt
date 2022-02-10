package com.newsappdemo.api

import com.newsappdemo.data.AppResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(ApiEndpoints.TOP_HEADLINES)
    suspend fun getNewsList(
        @Query(ApiParameters.COUNTRY) country: String,
        @Query(ApiParameters.CATEGORY) category: String,
        @Query(ApiParameters.API_KEY) apiKey: String,
        @Query(ApiParameters.PAGE) page: String,
        @Query(ApiParameters.PAGE_SIZE) pageSize: String
    ): AppResponse
}