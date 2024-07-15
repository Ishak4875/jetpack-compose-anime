package com.example.jetpackcomposeanime.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jetpackcomposeanime.core.data.source.local.entity.AnimeEntity
import com.example.jetpackcomposeanime.core.data.source.local.entity.FavoriteEntity
import com.example.jetpackcomposeanime.core.data.source.local.entity.TrendingEntity
import com.example.jetpackcomposeanime.core.domain.model.Anime
import kotlinx.coroutines.flow.Flow


@Dao
interface AnimeDao {
    @Query("SELECT * FROM anime")
    fun getAllAnime(): Flow<List<AnimeEntity>>

    @Query("SELECT * FROM trending")
    fun getAllTrending(): Flow<List<TrendingEntity>>

    @Query("SELECT * FROM favorite")
    fun getFavorite(): Flow<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnime(anime: List<AnimeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrending(trending: List<TrendingEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM favorite WHERE id = :id")
    fun deleteFavorite(id: String)

    @Query("SELECT EXISTS(SELECT * FROM favorite WHERE id = :id)")
    fun isFavorite(id: String): Flow<Boolean>
}