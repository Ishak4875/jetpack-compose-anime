package com.example.jetpackcomposeanime.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListAnimeResponse(
    @field:SerializedName("data")
    val data: List<DataResponse>
)

data class DataResponse(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("attributes")
    val attributes: Attributes
)

data class Attributes(
    @field:SerializedName("synopsis")
    val synopsis: String,

    @field:SerializedName("canonicalTitle")
    val title: String,

    @field:SerializedName("averageRating")
    val averageRating: String,

    @field:SerializedName("startDate")
    val startDate: String,

    @field:SerializedName("endDate")
    val endDate: String,

    @field:SerializedName("ratingRank")
    val ratingRank: Int,

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("posterImage")
    val posterImage: PosterImage,

    @field:SerializedName("coverImage")
    val coverImage: CoverImage,

    @field: SerializedName("episodeCount")
    val episodeCount: Int
)

data class Titles(
    @field:SerializedName("en")
    val title: String,
)

data class CoverImage(
    @field:SerializedName("large")
    val coverImage: String,
)

data class PosterImage(
    @field:SerializedName("large")
    val posterImage: String,
)