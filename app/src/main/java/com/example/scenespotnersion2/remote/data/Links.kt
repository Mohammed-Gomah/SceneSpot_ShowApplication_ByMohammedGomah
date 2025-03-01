package com.example.scenespotnersion2.remote.data


import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("next")
    val next: String? = null,
    @SerializedName("previous")
    val previous: Any? = null
)