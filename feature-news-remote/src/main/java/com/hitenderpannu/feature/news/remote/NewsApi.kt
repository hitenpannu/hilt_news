package com.hitenderpannu.feature.news.remote

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("/v2/top-headlines")
    fun getTopHeadlines(@Query("country") country: String): Single<NewsSearchApiResponse>

}
