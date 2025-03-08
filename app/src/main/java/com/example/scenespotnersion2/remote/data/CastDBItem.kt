package com.example.scenespotnersion2.remote.data


import com.google.gson.annotations.SerializedName

data class CastDBItem(
    @SerializedName("character")
    val character: Character? = Character(),
    @SerializedName("person")
    val person: Person? = Person(),
    @SerializedName("self")
    val self: Boolean? = false,
    @SerializedName("image")
    val image: Image? = Image(),
    @SerializedName("voice")
    val voice: Boolean? = false
)