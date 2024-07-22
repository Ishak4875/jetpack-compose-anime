package com.example.jetpackcomposeanime.core.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys_search")
data class RemoteKeysSearchEntity (
    @PrimaryKey val id: String,
    val prevKey: Int?,
    val nextKey: Int?
)