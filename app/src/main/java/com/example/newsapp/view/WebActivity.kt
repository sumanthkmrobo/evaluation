package com.example.newsapp.view

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.R


class WebActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        if(intent!=null){
            val webView = findViewById<View>(R.id.webView) as WebView

            val webSettings = webView.settings
            webSettings.javaScriptEnabled = true

            webView.loadUrl(intent.getStringExtra("url")!!)
        }
    }
}
