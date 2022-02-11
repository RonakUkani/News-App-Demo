package com.newsappdemo.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.newsappdemo.R
import com.newsappdemo.data.AppResponse
import com.newsappdemo.utils.Constants
import com.newsappdemo.utils.loadImage
import kotlinx.android.synthetic.main.row_news_detail.view.*
import kotlinx.android.synthetic.main.row_popular_news.view.*
import kotlinx.android.synthetic.main.row_top_news.view.*


class NewsListAdapter(private var newsList: MutableList<AppResponse.Article>, var callback: (AppResponse.Article) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Constants.TOP_NEWS -> {
                TopNewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_top_news, parent, false))
            }
            Constants.NEWS_DETAIL -> {
                NewsDetailViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_news_detail, parent, false))
            }
            else -> {
                PopularNewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_popular_news, parent, false))
            }
        }
    }

    override fun getItemCount(): Int = newsList.size

    @SuppressLint("SetJavaScriptEnabled")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val news = newsList[position]
        if (holder is TopNewsViewHolder) {
            holder.itemView.let {
                it.imageViewTopNewsHeadline.loadImage(newsList[position].urlToImage)
                it.textViewTopNewsHeadline.text = news.title
                it.textViewTopNewsDescription.text = news.description
                it.textViewTopNewsBrandName.text = news.source?.name
            }
        } else if (holder is PopularNewsViewHolder) {
            holder.itemView.let {
                it.imageViewNewsHeadline.loadImage(newsList[position].urlToImage)
                it.textViewNewsHeadline.text = news.title
                it.textViewNewsDescription.text = news.description
                it.textViewBrandName.text = news.source?.name
                it.textViewPopularNews.isGone = !news.isShowTitle
                it.viewPopularNews.isGone = !news.isShowTitle
            }
        } else if (holder is NewsDetailViewHolder) {
            holder.itemView.let {
                it.imageViewNewsDetailHeadline.loadImage(newsList[position].urlToImage)
                it.textViewNewsDetailHeadline.text = news.title
                it.textViewNewsDetailBrandName.text = news.source?.name
                it.webViewContent.webViewClient = WebViewClient()
                it.webViewContent.settings.javaScriptEnabled = true
                it.webViewContent.settings.allowContentAccess = true
                it.webViewContent.settings.domStorageEnabled = true
                it.webViewContent.loadData(news.content!!,"text/html" , "utf-8")
            }
        }
        holder.itemView.setOnClickListener {
            callback(newsList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (newsList[position].newsEnum) {
            AppResponse.Article.NewsEnum.TOP_NEWS -> {
                Constants.TOP_NEWS
            }
            AppResponse.Article.NewsEnum.NEWS_DETAIL -> {
                Constants.NEWS_DETAIL
            }
            else -> {
                Constants.POPULAR_NEWS
            }
        }
    }


    inner class TopNewsViewHolder(v: View) : RecyclerView.ViewHolder(v)
    inner class PopularNewsViewHolder(v: View) : RecyclerView.ViewHolder(v)
    inner class NewsDetailViewHolder(v: View) : RecyclerView.ViewHolder(v)

}