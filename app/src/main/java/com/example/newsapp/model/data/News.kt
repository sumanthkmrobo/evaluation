package com.example.newsapp.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class News (
    var articles: List<Article>
)

@Entity
data class Article (
    var source: Source,
    var author: String,
    var title: String,
    var description: String,
    var urlToImage: String,
    var url: String
)

data class Source (
    var name: String
)
