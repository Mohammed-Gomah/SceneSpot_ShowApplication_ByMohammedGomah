package com.example.scenespotnersion2.remote.data


import com.google.gson.annotations.SerializedName

data class WebChannel(
    @SerializedName("country")
    val country: Country? = Country(),
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("officialSite")
    val officialSite: String? = ""
)