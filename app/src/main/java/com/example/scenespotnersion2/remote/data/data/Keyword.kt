package com.example.scenespotnersion2.remote.data.data


import com.google.gson.annotations.SerializedName

data class Keyword(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("keyword")
    val keyword: String? = null
)