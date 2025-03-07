package com.example.scenespotnersion2.remote.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LinksX(
    @SerializedName("nextepisode")
    val nextepisode: Nextepisode? = null,
    @SerializedName("previousepisode")
    val previousepisode: Previousepisode? = null,
    @SerializedName("self")
    val self: Self? = null
) : Parcelable