package com.example.jetpackcomposeanime.search.di

import android.content.Context
import com.example.jetpackcomposeanime.core.data.source.local.entity.SearchAnimeEntity
import com.example.jetpackcomposeanime.di.SearchAnimeModuleDependencies
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [SearchAnimeModuleDependencies::class]
)
interface SearchComponent {
    fun inject(searchAnimeEntity: SearchAnimeEntity)

    @Component.Builder
    interface Builder{
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(searchAnimeModuleDependencies: SearchAnimeModuleDependencies): Builder
        fun build(): SearchComponent
    }
}