package com.example.scenespotnersion2.remote.data


import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("image")
    val image: ImageXX? = ImageXX(),
    @SerializedName("_links")
    val links: LinksXXX? = LinksXXX(),
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("url")
    val url: String? = ""
)