package com.tugasakhir.welearn.core.data.source.remote

import android.util.Log
import com.tugasakhir.welearn.core.data.source.remote.network.ApiService
import com.tugasakhir.welearn.core.data.source.remote.response.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.RequestBody
import java.lang.Exception

class RemoteDataSource (private val apiService: ApiService) {
    fun loginUser(body: RequestBody): Flow<LoginResponse> {
        return flow {
            try {
                val response = apiService.login(body)
                emit(response)
            } catch (e: Exception){
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}