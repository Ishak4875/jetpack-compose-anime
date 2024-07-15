package com.example.jetpackcomposeanime.core.data

import android.util.Log
import androidx.lifecycle.asLiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.jetpackcomposeanime.core.data.source.local.LocalDataSource
import com.example.jetpackcomposeanime.core.data.source.local.entity.FavoriteEntity
import com.example.jetpackcomposeanime.core.data.source.local.room.AnimeDatabase
import com.example.jetpackcomposeanime.core.data.source.remote.RemoteDataSource
import com.example.jetpackcomposeanime.core.data.source.remote.network.ApiResponse
import com.example.jetpackcomposeanime.core.data.source.remote.response.DataResponse
import com.example.jetpackcomposeanime.core.domain.model.Anime
import com.example.jetpackcomposeanime.core.domain.repository.IAnimeRepository
import com.example.jetpackcomposeanime.core.utils.AppExecutors
import com.example.jetpackcomposeanime.core.utils.DataMapper
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnimeRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors,
    private val animeDatabase: AnimeDatabase
) : IAnimeRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getAllAnime(): Flow<PagingData<Anime>> {
        val pagingSourceFactory = { localDataSource.getAllAnimePagingSource() }

        return Pager(
            config = PagingConfig(
                pageSize = 20, // adjust page size as needed
                enablePlaceholders = false
            ),
            remoteMediator = AnimeRemoteMediator(
                animeDatabase,
                localDataSource,
                remoteDataSource

            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map { animeEntity ->
                DataMapper.mapAnimeEntityToDomain(animeEntity)
            }
        }
    }


    override fun getTrending(): Flow<Resource<List<Anime>>> =
        object : NetworkBoundResource<List<Anime>, List<DataResponse>>() {
            override fun loadFromDB(): Flow<List<Anime>> {
                return localDataSource.getAlTrending().map {
                    DataMapper.mapTrendingEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Anime>?): Boolean = true

            override suspend fun createCall(): Flow<ApiResponse<List<DataResponse>>> =
                remoteDataSource.getTrending()

            override suspend fun saveCallResult(data: List<DataResponse>) {
                val trendingList = DataMapper.mapResponsesToTrendingEntities(data)
                localDataSource.insertTrending(trendingList)
            }

        }.asFlow()

    override fun getFavorite(): Flow<List<Anime>> {
        return localDataSource.getFavorite().map {
            DataMapper.mapFavoriteEntitiesToDomain(it)
        }
    }


    @OptIn(DelicateCoroutinesApi::class)
    override fun setFavoriteAnime(anime: Anime, state: Boolean) {
        val favoriteEntity = DataMapper.mapDomainToFavoriteEntity(anime)
        appExecutors.diskIO().execute {
            if (!state) {
                GlobalScope.launch {
                    localDataSource.insertFavorite(favoriteEntity)
                }
            } else {
                localDataSource.deleteFavorite(favoriteEntity.id)
            }
        }
    }

    override fun checkFavorite(id: String): Flow<Boolean> {
        return localDataSource.checkFavorite(id)
    }
}