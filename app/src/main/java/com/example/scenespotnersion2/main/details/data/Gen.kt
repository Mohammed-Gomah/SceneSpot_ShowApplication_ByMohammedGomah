package com.example.scenespotnersion2.main.details.data


import com.google.gson.annotations.SerializedName

data class Gen(
    @SerializedName("genre")
    val genre: String? = null,
    @SerializedName("id")
    val id: Int? = null
)