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
    suspend fun getAllAnime(page: Int, pageSize: Int): Flow<ApiResponse<List<DataResponse>>> {
        return flow {
            try {
                val response = apiService.getAnime(page, pageSize)
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

    suspend fun searchAnime(page: Int, pageSize: Int, name: String): Flow<ApiResponse<List<DataResponse>>>{
        return flow {
            try {
                val response = apiService.searchAnime(page,pageSize,name)
                val dataArray = response.data
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.data))
                }else{
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