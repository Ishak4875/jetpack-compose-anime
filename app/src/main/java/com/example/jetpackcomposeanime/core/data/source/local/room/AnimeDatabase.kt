package com.example.jetpackcomposeanime.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jetpackcomposeanime.core.data.source.local.entity.AnimeEntity
import com.example.jetpackcomposeanime.core.data.source.local.entity.FavoriteEntity
import com.example.jetpackcomposeanime.core.data.source.local.entity.RemoteKeysAnimeEntity
import com.example.jetpackcomposeanime.core.data.source.local.entity.RemoteKeysSearchEntity
import com.example.jetpackcomposeanime.core.data.source.local.entity.SearchAnimeEntity
import com.example.jetpackcomposeanime.core.data.source.local.entity.TrendingEntity

@Database(
    entities = [AnimeEntity::class,
        TrendingEntity::class,
        FavoriteEntity::class,
        SearchAnimeEntity::class,
        RemoteKeysAnimeEntity::class,
        RemoteKeysSearchEntity::class],
    version = 3,
    exportSchema = false
)
abstract class AnimeDatabase : RoomDatabase() {
    abstract fun animeDao(): AnimeDao
    abstract fun remoteKeysAnimeDao(): RemoteKeysAnimeDao
}