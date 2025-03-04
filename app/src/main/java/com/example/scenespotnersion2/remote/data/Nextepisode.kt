package com.example.scenespotnersion2.remote.data


import com.google.gson.annotations.SerializedName

data class Nextepisode(
    @SerializedName("href")
    val href: String? = null,
    @SerializedName("name")
    val name: String? = null
)