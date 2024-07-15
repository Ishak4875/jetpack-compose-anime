package com.example.jetpackcomposeanime.core.data.source.remote

import android.util.Log
import com.example.jetpackcomposeanime.core.data.source.remote.network.ApiResponse
import com.example.jetpackcomposeanime.core.data.source.remote.network.ApiService
import com.example.jetpackcomposeanime.core.data.source.remote.response.DataResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun getAllAnime(): Flow<ApiResponse<List<DataResponse>>> {
        return flow {
            try {
                val response = apiService.getAnime()
                val dataArray = response.data
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.data))
                } else{
                    emit(ApiResponse.Empty)
                }
            }catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource",e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getTrending(): Flow<ApiResponse<List<DataResponse>>> {
        return flow {
            try {
                val response = apiService.getTrending()
                val dataArray = response.data
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.data))
                } else{
                    emit(ApiResponse.Empty)
                }
            }catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource",e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}