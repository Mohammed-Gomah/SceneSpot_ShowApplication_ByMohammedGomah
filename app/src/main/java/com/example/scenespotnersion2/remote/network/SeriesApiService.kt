package com.example.scenespotnersion2.remote.network

import com.example.scenespotnersion2.main.details.data.episodedata.EpisodeDB
import com.example.scenespotnersion2.remote.data.CastDB
import com.example.scenespotnersion2.remote.data.SeasonDB
import com.example.scenespotnersion2.remote.data.SeriesDB
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SeriesApiService {

    @GET("shows")
    suspend fun fetchAllShows(): Response<SeriesDB>

    @GET("shows/{showId}/seasons")
    suspend fun fetchShowSeasonsById(@Path("showId") showId: Int): Response<SeasonDB>

    @GET("seasons/{showId}/episodes")
    suspend fun fetchShowSeasonEpisodesListById(@Path("showId") showId: Int): Response<EpisodeDB>

    @GET("shows/{showId}/cast")
    suspend fun fetchCastByShowId(@Path("showId") showId: Int): Response<CastDB>

    @GET("shows?")
    suspend fun searchSeriesByName(@Query("q") showName : String) : Response<SeriesDB>

}