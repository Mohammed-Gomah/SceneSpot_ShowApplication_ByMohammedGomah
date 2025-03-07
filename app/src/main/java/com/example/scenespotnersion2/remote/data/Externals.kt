package com.example.scenespotnersion2.remote.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize

data class Externals(
    @SerializedName("imdb")
    val imdb: String? = null,
    @SerializedName("thetvdb")
    val thetvdb: Int? = null,
    @SerializedName("tvrage")
    val tvrage: Int? = null
) : Parcelable