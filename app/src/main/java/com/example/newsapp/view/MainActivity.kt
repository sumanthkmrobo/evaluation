package com.example.newsapp.view

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.newsapp.R
import com.example.newsapp.model.data.Article
import com.example.newsapp.viewmodel.NewsViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: NewsViewModel
    private lateinit var NewsTitle: TextView
    private lateinit var NewsImage: ImageView
    private lateinit var NewsContent: TextView
    private lateinit var NewsSource: TextView
    private lateinit var newsList: RecyclerView
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NewsTitle = findViewById(R.id.NewsTitle)
        NewsImage = findViewById(R.id.NewsImage)
        NewsContent = findViewById(R.id.NewsContent)
        NewsSource = findViewById(R.id.NewsSource)
        newsList = findViewById(R.id.newsList)

        viewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)
        newsList.apply {
            newsAdapter = NewsAdapter()
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(getApplicationContext())
        }
        viewModel.getNews()
        viewModel.getAllNews()
        observeTopNews()
        observeAllNews()

    }

    private fun observeAllNews() {
        viewModel.allNews.observe(this, Observer { data ->
            newsAdapter.addItems(data.articles)
        })
    }

    private fun observeTopNews() {
        viewModel.News.observe(this, Observer { data ->
            NewsTitle.text = data.articles.get(0).title
            NewsContent.text = data.articles.get(0).description
            NewsSource.text = data.articles.get(0).source.name
            Glide.with(getBaseContext())
                .load(data.articles.get(0).urlToImage)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(NewsImage)

            NewsImage.setOnClickListener {
                val intent = Intent(applicationContext, WebActivity::class.java)
                intent.putExtra("url",data.articles.get(0).url)
                startActivity(intent)
            }
        })
    }
}
