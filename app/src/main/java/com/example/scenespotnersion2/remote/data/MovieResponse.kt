package com.example.scenespotnersion2.remote.data


import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("banner")
    val banner: String? = null,
    @SerializedName("content_rating")
    val contentRating: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("gen")
    val gen: List<Gen?>? = null,
    @SerializedName("image_url")
    val imageUrl: String? = null,
    @SerializedName("imdb_id")
    val imdbId: String? = null,
    @SerializedName("keywords")
    val keywords: List<Keyword?>? = null,
    @SerializedName("more_like_this")
    val moreLikeThis: MoreLikeThis? = null,
    @SerializedName("movie_length")
    val movieLength: Int? = null,
    @SerializedName("plot")
    val plot: String? = null,
    @SerializedName("popularity")
    val popularity: Int? = null,
    @SerializedName("rating")
    val rating: Double? = null,
    @SerializedName("release")
    val release: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("trailer")
    val trailer: String? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("year")
    val year: Int? = null
)