package com.example.jetpackcomposeanime.core.domain.usecase

import com.example.jetpackcomposeanime.core.data.Resource
import com.example.jetpackcomposeanime.core.domain.model.Anime
import com.example.jetpackcomposeanime.core.domain.repository.IAnimeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AnimeInteractor @Inject constructor(private val animeRepository: IAnimeRepository) :
    AnimeUseCase {
    override fun getAllAnime(): Flow<Resource<List<Anime>>> = animeRepository.getAllAnime()

    override fun getFavorite(): Flow<List<Anime>> = animeRepository.getFavorite()

    override fun getTrending(): Flow<Resource<List<Anime>>> = animeRepository.getTrending()

    override fun setFavoriteAnime(anime: Anime, state: Boolean) =
        animeRepository.setFavoriteAnime(anime, state)

    override fun checkFavorite(id: String): Flow<Boolean> = animeRepository.checkFavorite(id)
}