package com.example.scenespotnersion2.remote.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SeasonDBItem(
    @SerializedName("endDate")
    val endDate: String? = "",
    @SerializedName("episodeOrder")
    val episodeOrder: Int? = 0,
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("image")
    val image: ImageX? = ImageX(),
    @SerializedName("_links")
    val links: LinksXX? = LinksXX(),
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("network")
    val network: NetworkX? = NetworkX(),
    @SerializedName("number")
    val number: Int? = 0,
    @SerializedName("premiereDate")
    val premiereDate: String? = "",
    @SerializedName("summary")
    val summary: String? = "",
    @SerializedName("url")
    val url: String? = "",

) : Parcelable