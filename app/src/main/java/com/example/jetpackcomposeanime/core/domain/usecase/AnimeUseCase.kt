package com.example.jetpackcomposeanime.core.domain.usecase

import com.example.jetpackcomposeanime.core.data.Resource
import com.example.jetpackcomposeanime.core.domain.model.Anime
import kotlinx.coroutines.flow.Flow

interface AnimeUseCase {
    fun getAllAnime(): Flow<Resource<List<Anime>>>
    fun getFavorite(): Flow<List<Anime>>
    fun getTrending(): Flow<Resource<List<Anime>>>
    fun setFavoriteAnime(anime: Anime, state: Boolean)
    fun checkFavorite(id: String): Flow<Boolean>
}