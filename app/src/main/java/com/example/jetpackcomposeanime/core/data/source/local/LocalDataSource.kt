package com.example.jetpackcomposeanime.core.data.source.local

import com.example.jetpackcomposeanime.core.data.source.local.entity.AnimeEntity
import com.example.jetpackcomposeanime.core.data.source.local.entity.FavoriteEntity
import com.example.jetpackcomposeanime.core.data.source.local.entity.TrendingEntity
import com.example.jetpackcomposeanime.core.data.source.local.room.AnimeDao
import com.example.jetpackcomposeanime.core.domain.model.Anime
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val animeDao: AnimeDao) {
    fun getAllAnime(): Flow<List<AnimeEntity>> = animeDao.getAllAnime()
    fun getAlTrending(): Flow<List<TrendingEntity>> = animeDao.getAllTrending()
    fun getFavorite(): Flow<List<FavoriteEntity>> = animeDao.getFavorite()
    suspend fun insertAnime(animeList: List<AnimeEntity>) = animeDao.insertAnime(animeList)
    suspend fun insertTrending(trendingList: List<TrendingEntity>) = animeDao.insertTrending(trendingList)
    suspend fun insertFavorite(favorite: FavoriteEntity) = animeDao.insertFavorite(favorite)
    fun deleteFavorite(id: String) = animeDao.deleteFavorite(id)
    fun checkFavorite(id: String) = animeDao.isFavorite(id)
}