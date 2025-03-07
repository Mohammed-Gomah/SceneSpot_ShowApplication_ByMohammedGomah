package com.example.scenespotnersion2.remote.network

import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .addHeader("x-rapidapi-key", "832800f91emshe9a962f6e992378p1736dcjsnf9028457f21f")
            .addHeader("x-rapidapi-host", "moviesminidatabase.p.rapidapi.com")
            .build()
        return chain.proceed(newRequest)
    }
}