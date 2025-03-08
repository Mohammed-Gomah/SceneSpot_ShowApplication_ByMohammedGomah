package com.example.scenespotnersion2.remote.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LinksXX(
    @SerializedName("self")
    val self: SelfX? = SelfX()
): Parcelable