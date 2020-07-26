package com.hitenderpannu.feature.news.entity

data class Source(val name: String)

data class NewsArticle(
    val source: Source,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val imageUrl: String,
    val publishedAt: String,
    val content: String
)

sealed class NewsResult {
    data class Success(val articles: List<NewsArticle>) : NewsResult()

    sealed class Failure : NewsResult() {
        data class ApiFailure(val message: String, val errorCode: String) : Failure()
        data class GenericFailure(val error: Throwable) : Failure()
    }
}
