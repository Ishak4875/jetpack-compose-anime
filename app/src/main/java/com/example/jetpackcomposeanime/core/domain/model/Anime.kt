package com.example.jetpackcomposeanime.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Anime(
    val id: String,
    val synopsis: String?,
    val title: String?,
    val averageRating: String?,
    val startDate: String?,
    val endDate: String?,
    val ratingRank: Int?,
    val status: String?,
    val posterImage: String?,
    val coverImage: String?,
    val episodeCount: Int?
) : Parcelable