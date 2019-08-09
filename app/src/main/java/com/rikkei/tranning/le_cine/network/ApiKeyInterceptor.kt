package com.rikkei.tranning.le_cine.network

import com.rikkei.tranning.le_cine.util.TMDB_API_KEY

import javax.inject.Inject
import javax.inject.Singleton

import okhttp3.Interceptor
import okhttp3.Response

@Singleton
class ApiKeyInterceptor
@Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url()
        val url = originalUrl.newBuilder()
            .addQueryParameter("api_key", TMDB_API_KEY)
            .build()

        val requestBuilder = originalRequest.newBuilder().url(url)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}
