package com.example.jetpackcomposeanime.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.jetpackcomposeanime.core.domain.model.Anime
import com.example.jetpackcomposeanime.core.domain.usecase.AnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailAnimeViewModel @Inject constructor(private val animeUseCase: AnimeUseCase) :
    ViewModel() {
    fun setFavoriteAnime(anime: Anime, newStatus: Boolean) =
        animeUseCase.setFavoriteAnime(anime, newStatus)

    fun checkFavorite(id: String) = animeUseCase.checkFavorite(id)
}