package com.example.jetpackcomposeanime.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.jetpackcomposeanime.core.domain.usecase.AnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val animeUseCase: AnimeUseCase) : ViewModel() {
    val favorite = animeUseCase.getFavorite().asLiveData()
}