package com.newsappdemo.api

import android.content.Context
import com.newsappdemo.R
import org.json.JSONObject
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseDataSource(private val context: Context) {

    fun handleError(it: Throwable, callback: (String) -> Unit = {}) {
        when (it) {
            is HttpException -> {
                if(it.code() == 429){
                    callback.invoke(JSONObject(it.response()?.errorBody()?.string())["message"].toString())
                }
            }
            is ConnectException -> {
                callback.invoke(context.getString(R.string.error_message_unable_to_connect))
            }
            is SocketTimeoutException -> {
                callback.invoke(context.getString(R.string.error_message_unable_to_connect))
            }
            is UnknownHostException -> {
                callback.invoke(context.getString(R.string.error_unknown_host))
            }
            else -> {
                callback.invoke(context.getString(R.string.error_message_something_went_wrong))
            }
        }
    }


}


