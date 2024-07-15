package com.example.jetpackcomposeanime.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.jetpackcomposeanime.core.domain.usecase.AnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val animeUseCase: AnimeUseCase) : ViewModel() {
    val anime = animeUseCase.getAllAnime().asLiveData()
    val trending = animeUseCase.getTrending().asLiveData()
}