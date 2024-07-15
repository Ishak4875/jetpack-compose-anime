package com.example.jetpackcomposeanime.core.data.source.remote.network

import com.example.jetpackcomposeanime.core.data.source.remote.response.ListAnimeResponse
import retrofit2.http.GET

interface ApiService {
    @GET("anime")
    suspend fun getAnime(): ListAnimeResponse

    @GET("trending/anime")
    suspend fun getTrending(): ListAnimeResponse
}