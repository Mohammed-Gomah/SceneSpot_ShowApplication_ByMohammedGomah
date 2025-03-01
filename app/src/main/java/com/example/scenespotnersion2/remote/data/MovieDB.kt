package com.example.scenespotnersion2.remote.data


import com.google.gson.annotations.SerializedName

data class MovieDB(
    @SerializedName("results")
    val movieResponse: List<MovieResponse>? = null
)