package com.example.jetpackcomposeanime.core.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys_anime")
data class RemoteKeysAnimeEntity (
    @PrimaryKey val id: String,
    val prevKey: Int?,
    val nextKey: Int?
)