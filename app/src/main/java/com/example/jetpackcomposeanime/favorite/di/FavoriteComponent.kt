package com.example.jetpackcomposeanime.favorite.di

import android.content.Context
import com.example.jetpackcomposeanime.core.data.source.local.entity.FavoriteEntity
import com.example.jetpackcomposeanime.di.FavoriteModuleDependencies
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [FavoriteModuleDependencies::class]
)
interface FavoriteComponent {
    fun inject(favoriteEntity: FavoriteEntity)

    @Component.Builder
    interface Builder{
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favoriteModuleDependencies: FavoriteModuleDependencies): Builder
        fun build(): FavoriteComponent
    }
}