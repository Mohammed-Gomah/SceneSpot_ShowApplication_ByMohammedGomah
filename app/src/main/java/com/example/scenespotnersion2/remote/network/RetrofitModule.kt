package com.example.scenespotnersion2.remote.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitModule {

    private const val SERIES_BASE_URL = "https://api.tvmaze.com/"
    private const val MOVIES_BASE_URL = "https://moviesminidatabase.p.rapidapi.com/"

    private val client = OkHttpClient.Builder()
        .addInterceptor(ApiInterceptor()) // إضافة الـ Interceptor
        .build()

    val moviesRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(MOVIES_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
    }

    val seriesRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(SERIES_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}