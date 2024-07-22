package com.example.jetpackcomposeanime.core.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.jetpackcomposeanime.core.data.source.local.LocalDataSource
import com.example.jetpackcomposeanime.core.data.source.local.entity.RemoteKeysSearchEntity
import com.example.jetpackcomposeanime.core.data.source.local.entity.SearchAnimeEntity
import com.example.jetpackcomposeanime.core.data.source.local.room.AnimeDatabase
import com.example.jetpackcomposeanime.core.data.source.remote.RemoteDataSource
import com.example.jetpackcomposeanime.core.data.source.remote.network.ApiResponse
import com.example.jetpackcomposeanime.core.utils.DataMapper
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalPagingApi::class)
class SearchAnimeMediator(
    private val animeDatabase: AnimeDatabase,
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val searchAnime: String
) : RemoteMediator<Int, SearchAnimeEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SearchAnimeEntity>
    ): MediatorResult {
        try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeysSearchAnime = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeysSearchAnime?.nextKey?.minus(1) ?: 0
                }

                LoadType.PREPEND -> {
                    val remoteKeysSearchAnime = getRemoteKeyForFirstItem(state)
                    remoteKeysSearchAnime?.prevKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeysSearchAnime != null)
                }

                LoadType.APPEND -> {
                    val remoteKeysSearchAnime = getRemoteKeyForLastItem(state)
                    remoteKeysSearchAnime?.nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeysSearchAnime != null)
                }
            }

            val apiResponse =
                remoteDataSource.searchAnime(page, state.config.pageSize, searchAnime).first()

            return when (apiResponse) {
                is ApiResponse.Success -> {
                    animeDatabase.withTransaction {
                        if (loadType == LoadType.REFRESH) {
                            localDataSource.clearAllSearchAnime()
                            localDataSource.clearRemoteKeysSearch()
                        }

                        val nextKey = if (apiResponse.data.isEmpty()) null else page + 1
                        val prevKey = if (page == 0) null else page - 1

                        val keys = apiResponse.data.map {
                            RemoteKeysSearchEntity(id = it.id, prevKey, nextKey)
                        }

                        localDataSource.insertRemoveKeysSearch(keys)
                        localDataSource.insertSearchingAnime(
                            DataMapper.mapResponsesToSearchingAnimeEntities(
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

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, SearchAnimeEntity>): RemoteKeysSearchEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                localDataSource.getRemoteKeysSearchId(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, SearchAnimeEntity>): RemoteKeysSearchEntity? {
        return state.pages.firstOrNull() { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { anime ->
                localDataSource.getRemoteKeysSearchId(anime.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, SearchAnimeEntity>): RemoteKeysSearchEntity? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { anime ->
            localDataSource.getRemoteKeysSearchId(anime.id)
        }
    }
}