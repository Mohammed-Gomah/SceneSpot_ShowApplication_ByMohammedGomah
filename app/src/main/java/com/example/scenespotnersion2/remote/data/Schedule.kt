package com.example.scenespotnersion2.remote.data


import com.google.gson.annotations.SerializedName

data class Schedule(
    @SerializedName("days")
    val days: List<String?>? = null,
    @SerializedName("time")
    val time: String? = null
)