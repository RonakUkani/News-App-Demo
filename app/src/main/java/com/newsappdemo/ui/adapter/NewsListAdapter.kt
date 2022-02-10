package com.newsappdemo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.newsappdemo.R
import com.newsappdemo.data.AppResponse
import com.newsappdemo.utils.Constants
import com.newsappdemo.utils.loadImage
import kotlinx.android.synthetic.main.row_popular_news.view.*
import kotlinx.android.synthetic.main.row_top_news.view.*


class NewsListAdapter(private var newsList: MutableList<AppResponse.Article>, var callback: (AppResponse.Article) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == Constants.TOP_NEWS){
            TopNewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_top_news, parent, false))
        }else{
            PopularNewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_popular_news, parent, false))
        }
    }

    override fun getItemCount(): Int = newsList.size

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
        }
        holder.itemView.setOnClickListener {
            callback(newsList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(newsList[position].newsEnum == AppResponse.Article.NewsEnum.TOP_NEWS) {
            Constants.TOP_NEWS
        }else{
            Constants.POPULAR_NEWS
        }
    }


    inner class TopNewsViewHolder(v: View) : RecyclerView.ViewHolder(v)
    inner class PopularNewsViewHolder(v: View) : RecyclerView.ViewHolder(v)

}