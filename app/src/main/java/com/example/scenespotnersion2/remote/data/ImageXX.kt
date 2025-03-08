package com.example.scenespotnersion2.remote.data


import com.google.gson.annotations.SerializedName

data class ImageXX(
    @SerializedName("medium")
    val medium: String? = null,
    @SerializedName("original")
    val original: String? = null
)