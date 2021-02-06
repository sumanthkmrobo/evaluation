package com.example.newsapp.model.network

import android.content.Context
import com.example.newsapp.model.data.News
import io.reactivex.Single
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NewsApiService(context: Context) {
    private val url = "https://newsapi.org/"
    private val myCache = Cache(context.cacheDir, (10 * 1024 * 1024).toLong())

    private fun loadApi(duration: Long): NewsApi {
        val okHttpClient = OkHttpClient.Builder()
            .cache(myCache)
            .addInterceptor { chain ->
                val request = chain.request()
                request.newBuilder()
                    .header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=" + 60 * duration
                    )
                    .build()
                chain.proceed(request)
            }
            .build()
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(NewsApi::class.java)

    }

    fun getNews(): Single<News> {
        return loadApi(480).getNews()
    }

    fun getAllNews(): Single<News> {
        return loadApi(480).getAllNews()
    }

}
