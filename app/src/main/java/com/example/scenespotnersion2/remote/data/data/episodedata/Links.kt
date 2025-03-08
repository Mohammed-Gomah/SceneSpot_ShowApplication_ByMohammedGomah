package com.example.scenespotnersion2.main.details.data.episodesepisodedata


import com.example.scenespotnersion2.remote.data.data.episodedata.Self
import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("self")
    val self: Self? = null,
    @SerializedName("show")
    val show: Show? = null
)