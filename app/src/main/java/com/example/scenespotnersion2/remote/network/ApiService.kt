package com.example.scenespotnersion2.remote.network

import com.example.scenespotnersion2.remote.data.MovieDB
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("movie/order/byPopularity/")
    suspend fun getMoviesOrderedByPopularity():Response<MovieDB>

    @GET("movie/id/{movieId}/")
    suspend fun getMovieById(@Path("movieId") movieId : String) : Response<MovieDB>
}