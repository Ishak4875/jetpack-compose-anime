package com.example.jetpackcomposeanime.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jetpackcomposeanime.core.data.source.local.entity.RemoteKeysAnimeEntity

@Dao
interface RemoteKeysAnimeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAnime(remoteKey: List<RemoteKeysAnimeEntity>)

    @Query("SELECT * FROM remote_keys_anime WHERE id = :id")
    suspend fun getRemoteKeysAnimeId(id: String): RemoteKeysAnimeEntity?

    @Query("DELETE FROM remote_keys_anime")
    suspend fun deleteRemoteKeys()
}