package com.example.jetpackcomposeanime.di

import com.example.jetpackcomposeanime.core.domain.usecase.AnimeUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface SearchAnimeModuleDependencies {
    fun provideAnimeUseCase(): AnimeUseCase
}