package com.example.scenespotnersion2.remote.data


import com.google.gson.annotations.SerializedName

data class Person(
    @SerializedName("birthday")
    val birthday: String? = "",
    @SerializedName("country")
    val country: CountryXX? = CountryXX(),
    @SerializedName("deathday")
    val deathday: Any? = Any(),
    @SerializedName("gender")
    val gender: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("image")
    val image: ImageXX? = ImageXX(),
    @SerializedName("_links")
    val links: LinksXXX? = LinksXXX(),
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("updated")
    val updated: Int? = 0,
    @SerializedName("url")
    val url: String? = ""
)