@file:Suppress("DEPRECATION")

package com.newsappdemo.utils

import android.app.Activity
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
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

fun Activity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}