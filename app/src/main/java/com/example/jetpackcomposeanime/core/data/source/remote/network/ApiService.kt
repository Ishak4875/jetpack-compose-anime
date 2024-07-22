package com.example.jetpackcomposeanime.core.data.source.remote.network

import com.example.jetpackcomposeanime.core.data.source.remote.response.ListAnimeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("anime")
    suspend fun getAnime(
        @Query("page[offset]") page: Int,
        @Query("page[limit]") size: Int
    ): ListAnimeResponse

    @GET("anime")
    suspend fun searchAnime(
        @Query("page[offset]") page: Int,
        @Query("page[limit]") size: Int,
        @Query("filter[text]") name: String
    ): ListAnimeResponse

    @GET("trending/anime")
    suspend fun getTrending(): ListAnimeResponse
}