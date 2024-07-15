package com.example.jetpackcomposeanime.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.jetpackcomposeanime.core.domain.model.Anime
import com.example.jetpackcomposeanime.core.domain.usecase.AnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val animeUseCase: AnimeUseCase) : ViewModel() {
    val anime: LiveData<PagingData<Anime>> = animeUseCase.getAllAnime().cachedIn(viewModelScope).asLiveData()
    val trending = animeUseCase.getTrending().asLiveData()
}