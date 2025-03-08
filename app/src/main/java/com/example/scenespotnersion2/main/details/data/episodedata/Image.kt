package com.example.scenespotnersion2.main.details.data.episodesepisodedata


import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("medium")
    val medium: String? = null,
    @SerializedName("original")
    val original: String? = null
)