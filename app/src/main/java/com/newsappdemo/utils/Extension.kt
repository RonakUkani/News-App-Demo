@file:Suppress("DEPRECATION")

package com.newsappdemo.utils

import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide

inline fun <reified VM : ViewModel> AppCompatActivity.viewModelProvider(
    provider: ViewModelProvider.Factory,
) =
    ViewModelProvider(this, provider).get(VM::class.java)

fun ImageView.loadImage(url: String?) {
    try {
        Glide.with(this.context).load(url).placeholder(android.R.drawable.ic_menu_report_image)
            .into(this)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
