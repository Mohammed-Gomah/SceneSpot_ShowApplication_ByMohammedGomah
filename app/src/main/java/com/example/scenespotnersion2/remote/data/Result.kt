package com.example.scenespotnersion2.remote.data


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("rating")
    val rating: Double? = null,
    @SerializedName("banner")
    val banner: String? = null,
    @SerializedName("imdb_id")
    val imdbId: String? = null,
    @SerializedName("popularity")
    val popularity: Int? = null,
    @SerializedName("title")
    val title: String? = null
)