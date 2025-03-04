package com.example.scenespotnersion2.remote.data


import com.google.gson.annotations.SerializedName

data class Externals(
    @SerializedName("imdb")
    val imdb: String? = null,
    @SerializedName("thetvdb")
    val thetvdb: Int? = null,
    @SerializedName("tvrage")
    val tvrage: Int? = null
)