package com.example.newsapp.model.network

import com.example.newsapp.model.data.News
import io.reactivex.Single
import retrofit2.http.GET

interface NewsApi {

    @GET("v2/top-headlines?country=in&apiKey=97d7e1d408d54cfaa61d35291570c7f5")
    fun getNews(): Single<News>

    @GET("v2/everything?q=bitcoin&apiKey=97d7e1d408d54cfaa61d35291570c7f5")
    fun getAllNews(): Single<News>
}
