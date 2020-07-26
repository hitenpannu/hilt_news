package com.hitenderpannu.feature.news.remote

import com.hitenderpannu.feature.news.entity.NewsArticle

data class NewsSearchApiResponse(
    val status: String,
    val totalResultCount: Int = 0,
    val articles: List<NewsArticle> = emptyList(),
    val code: String,
    val message: String
)
