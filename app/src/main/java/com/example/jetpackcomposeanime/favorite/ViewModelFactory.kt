package com.example.jetpackcomposeanime.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackcomposeanime.core.domain.usecase.AnimeUseCase
import com.example.jetpackcomposeanime.detail.DetailAnimeViewModel
import com.example.jetpackcomposeanime.main.MainViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val animeUseCase: AnimeUseCase
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> FavoriteViewModel(
                animeUseCase
            ) as T
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(
                animeUseCase
            ) as T
            modelClass.isAssignableFrom(DetailAnimeViewModel::class.java) -> DetailAnimeViewModel(
                animeUseCase
            ) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class ${modelClass.name}")
        }
}