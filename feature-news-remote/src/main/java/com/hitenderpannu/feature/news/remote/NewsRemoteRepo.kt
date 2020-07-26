package com.hitenderpannu.feature.news.remote

import com.hitenderpannu.feature.news.entity.NewsResult
import io.reactivex.rxjava3.core.Single

interface NewsRemoteRepo {
    fun getTopHeadlines(country: String): Single<NewsResult>
}
