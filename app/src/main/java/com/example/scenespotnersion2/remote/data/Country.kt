package com.example.scenespotnersion2.remote.data


import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("code")
    val code: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("timezone")
    val timezone: String? = null
)