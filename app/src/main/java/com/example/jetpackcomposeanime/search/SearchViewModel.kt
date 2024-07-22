package com.example.jetpackcomposeanime.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.jetpackcomposeanime.core.domain.usecase.AnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val animeUseCase: AnimeUseCase) : ViewModel() {
    fun searchingAnime(anime: String) =
        animeUseCase.getSearchingAnime(anime).cachedIn(viewModelScope).asLiveData()
}