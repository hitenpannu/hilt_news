package com.hitenderpannu.network

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyAppender : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val newRequestUrl = request.url.newBuilder()
            .addQueryParameter("apiKey", BuildConfig.API_KEY)
            .build()

        val newRequest = request.newBuilder().url(newRequestUrl).build()

        return chain.proceed(newRequest)
    }
}
