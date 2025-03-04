package com.example.scenespotnersion2.remote.data


import com.google.gson.annotations.SerializedName

data class Rating(
    @SerializedName("average")
    val average: Double? = null
)