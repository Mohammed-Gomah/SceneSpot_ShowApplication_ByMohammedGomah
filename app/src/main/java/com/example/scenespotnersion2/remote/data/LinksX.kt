package com.example.scenespotnersion2.remote.data


import com.google.gson.annotations.SerializedName

data class LinksX(
    @SerializedName("nextepisode")
    val nextepisode: Nextepisode? = null,
    @SerializedName("previousepisode")
    val previousepisode: Previousepisode? = null,
    @SerializedName("self")
    val self: Self? = null
)