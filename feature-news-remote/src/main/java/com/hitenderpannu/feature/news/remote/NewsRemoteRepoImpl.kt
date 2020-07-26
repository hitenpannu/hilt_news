package com.hitenderpannu.feature.news.remote

import com.hitenderpannu.feature.news.entity.NewsResult
import io.reactivex.rxjava3.core.Single

class NewsRemoteRepoImpl(private val newsApi: NewsApi) : NewsRemoteRepo {

    override fun getTopHeadlines(country: String): Single<NewsResult> {
        return newsApi.getTopHeadlines(country)
            .map { response ->
                if (response.status == "ok") {
                    NewsResult.Success(response.articles)
                } else {
                    NewsResult.Failure.ApiFailure(response.message, response.code)
                }
            }
    }
}
