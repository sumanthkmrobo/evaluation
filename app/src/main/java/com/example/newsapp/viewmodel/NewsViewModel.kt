package com.example.newsapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.newsapp.model.data.Article
import com.example.newsapp.model.data.News
import com.example.newsapp.model.network.NewsApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class NewsViewModel(application: Application) : AndroidViewModel(application){
    private val apiService =
        NewsApiService(
            application.applicationContext
        )
    private val disposable = CompositeDisposable()
    val News = MutableLiveData<News>()
    val allNews = MutableLiveData<News>()
    val bookMarksNews = MutableLiveData<List<Article>>()
    val loadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun getNews() {
        loading.value = true
        disposable.add(
            apiService.getNews()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<News>() {
                    override fun onSuccess(t: News) {
                        News.value = t
                        loadError.value = false
                        loading.value = false
                    }
                    override fun onError(e: Throwable) {
                        loadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }
                })
        )
    }

    fun getAllNews() {
        loading.value = true
        disposable.add(
            apiService.getAllNews()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<News>() {
                    override fun onSuccess(t: News) {
                        allNews.value = t
                        loadError.value = false
                        loading.value = false
                    }
                    override fun onError(e: Throwable) {
                        loadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
