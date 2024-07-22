package com.example.jetpackcomposeanime.core.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.jetpackcomposeanime.core.data.source.local.LocalDataSource
import com.example.jetpackcomposeanime.core.data.source.local.entity.AnimeEntity
import com.example.jetpackcomposeanime.core.data.source.local.entity.RemoteKeysAnimeEntity
import com.example.jetpackcomposeanime.core.data.source.local.room.AnimeDatabase
import com.example.jetpackcomposeanime.core.data.source.remote.RemoteDataSource
import com.example.jetpackcomposeanime.core.data.source.remote.network.ApiResponse
import com.example.jetpackcomposeanime.core.utils.DataMapper
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalPagingApi::class)
class AnimeRemoteMediator(
    private val animeDatabase: AnimeDatabase,
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : RemoteMediator<Int, AnimeEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AnimeEntity>
    ): MediatorResult {
        try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeysAnime = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeysAnime?.nextKey?.minus(1) ?: 0
                }

                LoadType.PREPEND -> {
                    val remoteKeysAnime = getRemoteKeyForFirstItem(state)
                    remoteKeysAnime?.prevKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeysAnime != null)
                }

                LoadType.APPEND -> {
                    val remoteKeysAnime = getRemoteKeyForLastItem(state)
                    remoteKeysAnime?.nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeysAnime != null)
                }
            }

            val apiResponse = remoteDataSource.getAllAnime(page, state.config.pageSize).first()

            return when (apiResponse) {
                is ApiResponse.Success -> {
                    animeDatabase.withTransaction {
                        if (loadType == LoadType.REFRESH) {
                            localDataSource.clearAllAnime()
                            localDataSource.clearRemoteKeys()
                        }

                        val nextKey = if (apiResponse.data.isEmpty()) null else page + 1
                        val prevKey = if (page == 0) null else page - 1

                        val keys = apiResponse.data.map {
                            RemoteKeysAnimeEntity(id = it.id, prevKey, nextKey)
                        }

                        localDataSource.insertRemoveKeys(keys)
                        localDataSource.insertAnime(
                            DataMapper.mapResponsesToAnimeEntities(
                                apiResponse.data
                            )
                        )
                    }
                    MediatorResult.Success(endOfPaginationReached = apiResponse.data.isEmpty())
                }

                is ApiResponse.Empty -> MediatorResult.Success(endOfPaginationReached = true)

                is ApiResponse.Error -> MediatorResult.Error(Throwable(apiResponse.errorMessage))
            }
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, AnimeEntity>): RemoteKeysAnimeEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                localDataSource.getRemoteKeysAnimeId(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, AnimeEntity>): RemoteKeysAnimeEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { anime ->
            localDataSource.getRemoteKeysAnimeId(anime.id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, AnimeEntity>): RemoteKeysAnimeEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { anime ->
            localDataSource.getRemoteKeysAnimeId(anime.id)
        }
    }
}