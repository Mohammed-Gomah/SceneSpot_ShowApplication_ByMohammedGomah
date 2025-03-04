package com.example.scenespotnersion2.remote.repository

import com.example.scenespotnersion2.remote.network.MoviesApiService
import com.example.scenespotnersion2.remote.network.RetrofitModule
import com.example.scenespotnersion2.remote.network.SeriesApiService

class MainRepository {
    val moviesApiService: MoviesApiService by lazy {
        RetrofitModule.moviesRetrofit.create(MoviesApiService::class.java)
    }

    val seriesApiService: SeriesApiService by lazy {
        RetrofitModule.seriesRetrofit.create(SeriesApiService::class.java)
    }

}