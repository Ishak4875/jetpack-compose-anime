package com.example.jetpackcomposeanime.core.utils

import com.example.jetpackcomposeanime.core.data.source.local.entity.AnimeEntity
import com.example.jetpackcomposeanime.core.data.source.local.entity.FavoriteEntity
import com.example.jetpackcomposeanime.core.data.source.local.entity.TrendingEntity
import com.example.jetpackcomposeanime.core.data.source.remote.response.DataResponse
import com.example.jetpackcomposeanime.core.domain.model.Anime

object DataMapper {
    fun mapResponsesToAnimeEntities(input: List<DataResponse>): List<AnimeEntity> {
        val animeList = ArrayList<AnimeEntity>()
        input.map {
            val anime = AnimeEntity(
                id = it.id,
                synopsis = it.attributes.synopsis ?: "No Synopsis Available",
                title = it.attributes.title ?: "No Title Available",
                averageRating = it.attributes.averageRating ?: "0.0",
                startDate = it.attributes.startDate ?: "Unknown",
                endDate = it.attributes.endDate ?: "Unknown",
                ratingRank = it.attributes.ratingRank ?: 0,
                status = it.attributes.status ?: "Unknown",
                posterImage = it.attributes.posterImage?.posterImage ?: "default_poster_image_url",
                coverImage = it.attributes.coverImage?.coverImage ?: "default_cover_image_url",
                episodeCount = it.attributes.episodeCount ?: 0
            )
            animeList.add(anime)
        }
        return animeList
    }

    fun mapResponsesToTrendingEntities(input: List<DataResponse>): List<TrendingEntity> {
        val animeList = ArrayList<TrendingEntity>()
        input.map {
            val anime = TrendingEntity(
                id = it.id,
                synopsis = it.attributes.synopsis ?: "No Synopsis Available",
                title = it.attributes.title ?: "No Title Available",
                averageRating = it.attributes.averageRating ?: "0.0",
                startDate = it.attributes.startDate ?: "Unknown",
                endDate = it.attributes.endDate ?: "Unknown",
                ratingRank = it.attributes.ratingRank ?: 0,
                status = it.attributes.status ?: "Unknown",
                posterImage = it.attributes.posterImage?.posterImage ?: "default_poster_image_url",
                coverImage = it.attributes.coverImage?.coverImage ?: "default_cover_image_url",
                episodeCount = it.attributes.episodeCount ?: 0
            )
            animeList.add(anime)
        }
        return animeList
    }

    fun mapAnimeEntitiesToDomain(input: List<AnimeEntity>): List<Anime> =
        input.map {
            Anime(
                id = it.id,
                synopsis = it.synopsis,
                title = it.title,
                averageRating = it.averageRating,
                startDate = it.startDate,
                endDate = it.endDate,
                ratingRank = it.ratingRank,
                status = it.status,
                coverImage = it.coverImage,
                posterImage = it.posterImage,
                episodeCount = it.episodeCount
            )
        }

    fun mapFavoriteEntitiesToDomain(input: List<FavoriteEntity>): List<Anime> =
        input.map {
            Anime(
                id = it.id,
                synopsis = it.synopsis,
                title = it.title,
                averageRating = it.averageRating,
                startDate = it.startDate,
                endDate = it.endDate,
                ratingRank = it.ratingRank,
                status = it.status,
                coverImage = it.coverImage,
                posterImage = it.posterImage,
                episodeCount = it.episodeCount
            )
        }

    fun mapTrendingEntitiesToDomain(input: List<TrendingEntity>): List<Anime> =
        input.map {
            Anime(
                id = it.id,
                synopsis = it.synopsis,
                title = it.title,
                averageRating = it.averageRating,
                startDate = it.startDate,
                endDate = it.endDate,
                ratingRank = it.ratingRank,
                status = it.status,
                coverImage = it.coverImage,
                posterImage = it.posterImage,
                episodeCount = it.episodeCount
            )
        }

    fun mapAnimeEntityToDomain(input: AnimeEntity): Anime {
        return Anime(
            id = input.id,
            synopsis = input.synopsis,
            title = input.title,
            averageRating = input.averageRating,
            startDate = input.startDate,
            endDate = input.endDate,
            ratingRank = input.ratingRank,
            status = input.status,
            coverImage = input.coverImage,
            posterImage = input.posterImage,
            episodeCount = input.episodeCount
        )
    }

    fun mapDomainToAnimeEntity(input: Anime) = AnimeEntity(
        id = input.id,
        synopsis = input.synopsis,
        title = input.title,
        averageRating = input.averageRating,
        startDate = input.startDate,
        endDate = input.endDate,
        ratingRank = input.ratingRank,
        status = input.status,
        coverImage = input.coverImage,
        posterImage = input.posterImage,
        episodeCount = input.episodeCount
    )

    fun mapDomainToFavoriteEntity(input: Anime) = FavoriteEntity(
        id = input.id,
        synopsis = input.synopsis,
        title = input.title,
        averageRating = input.averageRating,
        startDate = input.startDate,
        endDate = input.endDate,
        ratingRank = input.ratingRank,
        status = input.status,
        coverImage = input.coverImage,
        posterImage = input.posterImage,
        episodeCount = input.episodeCount
    )

    fun mapDomainToTrendingEntity(input: Anime) = AnimeEntity(
        id = input.id,
        synopsis = input.synopsis,
        title = input.title,
        averageRating = input.averageRating,
        startDate = input.startDate,
        endDate = input.endDate,
        ratingRank = input.ratingRank,
        status = input.status,
        coverImage = input.coverImage,
        posterImage = input.posterImage,
        episodeCount = input.episodeCount
    )
}