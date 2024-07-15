package com.example.jetpackcomposeanime.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jetpackcomposeanime.core.data.source.local.entity.AnimeEntity
import com.example.jetpackcomposeanime.core.data.source.local.entity.FavoriteEntity
import com.example.jetpackcomposeanime.core.data.source.local.entity.RemoteKeysAnimeEntity
import com.example.jetpackcomposeanime.core.data.source.local.entity.TrendingEntity

@Database(
    entities = [AnimeEntity::class, TrendingEntity::class, FavoriteEntity::class, RemoteKeysAnimeEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AnimeDatabase : RoomDatabase() {
    abstract fun animeDao(): AnimeDao
    abstract fun remoteKeysAnimeDao(): RemoteKeysAnimeDao
}