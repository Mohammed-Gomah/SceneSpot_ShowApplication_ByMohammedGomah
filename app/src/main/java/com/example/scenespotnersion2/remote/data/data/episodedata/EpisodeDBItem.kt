package com.example.scenespotnersion2.remote.data.data.episodedata


import com.example.scenespotnersion2.main.details.data.episodesepisodedata.Image
import com.example.scenespotnersion2.main.details.data.episodesepisodedata.Links
import com.example.scenespotnersion2.main.details.data.episodesepisodedata.Rating
import com.google.gson.annotations.SerializedName

data class EpisodeDBItem(
    @SerializedName("airdate")
    val airdate: String? = null,
    @SerializedName("airstamp")
    val airstamp: String? = null,
    @SerializedName("airtime")
    val airtime: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("image")
    val image: Image? = null,
    @SerializedName("_links")
    val links: Links? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("number")
    val number: Int? = null,
    @SerializedName("rating")
    val rating: Rating? = null,
    @SerializedName("runtime")
    val runtime: Int? = null,
    @SerializedName("season")
    val season: Int? = null,
    @SerializedName("summary")
    val summary: String? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("url")
    val url: String? = null
)