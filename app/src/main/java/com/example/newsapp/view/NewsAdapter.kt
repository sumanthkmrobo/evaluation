package com.example.newsapp.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.newsapp.R
import com.example.newsapp.model.data.Article
import com.example.newsapp.viewmodel.NewsViewModel

class NewsAdapter() :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    private val mNewsList: ArrayList<Article> = ArrayList()
    private lateinit var viewModel: NewsViewModel

    class NewsViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        lateinit var newsItemTitle: TextView
        lateinit var newsItemSource: TextView
        lateinit var newsItemContent: TextView
        lateinit var newsItemImage: ImageView
        lateinit var newsItemBookMark: ImageView

        init {
            bind()
        }

        fun bind() {
            newsItemTitle = itemView.findViewById(R.id.newsItemTitle)
            newsItemContent = itemView.findViewById(R.id.newsItemContent)
            newsItemSource = itemView.findViewById(R.id.newsItemSource)
            newsItemImage = itemView.findViewById(R.id.newsItemImage)
            newsItemBookMark = itemView.findViewById(R.id.newsItemBookMark)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_news_list, parent, false)
        return NewsViewHolder(view)
    }

    override fun getItemCount() = mNewsList.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.newsItemTitle.text = mNewsList.get(position).title
        holder.newsItemContent.text = mNewsList.get(position).description
        holder.newsItemSource.text = mNewsList.get(position).source.name
        Glide.with(holder.itemView.context)
            .load(mNewsList.get(position).urlToImage)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.newsItemImage)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, WebActivity::class.java)
            intent.putExtra("url",mNewsList.get(position).url)
            holder.itemView.context.startActivity(intent)
        }
    }

    fun addItems(newsList: List<Article>) {
        mNewsList.clear()
        mNewsList.addAll(newsList)
        notifyDataSetChanged()
    }
}
