package com.example.scenespotnersion2.remote.data


import com.google.gson.annotations.SerializedName

data class SeriesDBItem(
    @SerializedName("averageRuntime")
    val averageRuntime: Int? = 0,
    @SerializedName("dvdCountry")
    val dvdCountry: DvdCountry? = DvdCountry(),
    @SerializedName("ended")
    val ended: String? = "",
    @SerializedName("externals")
    val externals: Externals? = Externals(),
    @SerializedName("genres")
    val genres: List<String>? = listOf(),
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("image")
    val image: Image? = Image(),
    @SerializedName("language")
    val language: String? = "",
    @SerializedName("_links")
    val links: LinksX? = LinksX(),
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("network")
    val network: Network? = Network(),
    @SerializedName("officialSite")
    val officialSite: String? = "",
    @SerializedName("premiered")
    val premiered: String? = "",
    @SerializedName("rating")
    val rating: Rating? = Rating(),
    @SerializedName("runtime")
    val runtime: Int? = 0,
    @SerializedName("schedule")
    val schedule: Schedule? = Schedule(),
    @SerializedName("status")
    val status: String? = "",
    @SerializedName("summary")
    val summary: String? = "",
    @SerializedName("type")
    val type: String? = "",
    @SerializedName("updated")
    val updated: Int? = 0,
    @SerializedName("url")
    val url: String? = "",
    @SerializedName("webChannel")
    val webChannel: WebChannel? = WebChannel(),
    @SerializedName("weight")
    val weight: Int? = 0
)