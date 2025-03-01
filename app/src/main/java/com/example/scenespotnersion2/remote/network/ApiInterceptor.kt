package com.example.scenespotnersion2.remote.network

import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .addHeader("x-rapidapi-key", "6514004e8fmsh359f692b2e4eaf3p1a5561jsnaf1ac92df685")
            .addHeader("x-rapidapi-host", "moviesminidatabase.p.rapidapi.com")
            .build()
        return chain.proceed(newRequest)
    }
}