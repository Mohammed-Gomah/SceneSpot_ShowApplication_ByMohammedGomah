package com.example.scenespotnersion2.remote.network

import com.example.scenespotnersion2.remote.data.SeriesDB
import retrofit2.Response
import retrofit2.http.GET

interface SeriesApiService {

    @GET("shows")
    suspend fun fetchAllShows(): Response<SeriesDB>

}