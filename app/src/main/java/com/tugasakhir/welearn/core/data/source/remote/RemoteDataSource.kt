package com.tugasakhir.welearn.core.data.source.remote

import android.util.Log
import com.tugasakhir.welearn.core.data.source.remote.network.ApiService
import com.tugasakhir.welearn.core.data.source.remote.response.DetailResponse
import com.tugasakhir.welearn.core.data.source.remote.response.LoginResponse
import com.tugasakhir.welearn.core.data.source.remote.response.LogoutResponse
import com.tugasakhir.welearn.core.data.source.remote.response.RegisterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.RequestBody
import java.lang.Exception

class RemoteDataSource (private val apiService: ApiService) {

    fun loginUser(username: String, password: String): Flow<LoginResponse> {
        return flow {
            try {
                val response = apiService.login(username, password)
                emit(response)
            } catch (e: Exception){
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun registerUser(
        username: String,
        password: String,
        email: String,
        name: String,
        jenis_kelamin: String
    ): Flow<RegisterResponse> {
        return flow {
            try {
                val response = apiService.register(username, password, email, name, jenis_kelamin)
                emit(response)
            } catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun detailUser(): Flow<DetailResponse> {
        return flow {
            try {
                val response = apiService.detail()
                emit(response)
            } catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun logoutUser(): Flow<LogoutResponse> {
        return flow {
            try {
                val response = apiService.logout()
                emit(response)
            } catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }


}