package com.example.scenespotnersion2.remote.data


import com.google.gson.annotations.SerializedName

data class PopularityDB(
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("links")
    val links: Links? = null,
    @SerializedName("results")
    val results: List<Result?>? = null
)