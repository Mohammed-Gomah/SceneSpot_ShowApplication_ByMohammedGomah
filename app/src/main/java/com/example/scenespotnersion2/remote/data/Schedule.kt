package com.example.scenespotnersion2.remote.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Schedule(
    @SerializedName("days")
    val days: List<String?>? = null,
    @SerializedName("time")
    val time: String? = null
) : Parcelable