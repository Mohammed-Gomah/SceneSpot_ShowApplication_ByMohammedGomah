package com.example.scenespotnersion2.remote.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitModule {
    private val client = OkHttpClient.Builder()
        .addInterceptor(ApiInterceptor()) // إضافة الـ Interceptor
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://moviesminidatabase.p.rapidapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
}