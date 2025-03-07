package com.example.scenespotnersion2.remote.repository

import com.example.scenespotnersion2.remote.network.MovieMiniDatabaseService
import com.example.scenespotnersion2.remote.network.RetrofitModule
import com.example.scenespotnersion2.remote.network.SeriesApiService

class MainRepository {
    val moviesApiService: MovieMiniDatabaseService by lazy {
        RetrofitModule.moviesRetrofit.create(MovieMiniDatabaseService::class.java)
    }

    val seriesApiService: SeriesApiService by lazy {
        RetrofitModule.seriesRetrofit.create(SeriesApiService::class.java)
    }

}