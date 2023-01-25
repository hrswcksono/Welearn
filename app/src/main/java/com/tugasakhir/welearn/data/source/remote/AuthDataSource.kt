package com.tugasakhir.welearn.data.source.remote

import android.util.Log
import com.tugasakhir.welearn.data.source.remote.network.AuthApi
import com.tugasakhir.welearn.data.source.remote.response.DMessage
import com.tugasakhir.welearn.data.source.remote.response.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AuthDataSource(private val apiService: AuthApi) {
    fun loginUser(username: String, password: String) =
        flow {
            try {
                val response = apiService.login(username, password)
                if (response.success != null) {
                    emit(response.message)
                }
            } catch (e: Exception){
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO) as Flow<Message>

    fun detailUser(tokenUser: String) =
        flow {
            try {
                val response = apiService.detail(token = "Bearer $tokenUser")
                if (response.success != null) {
                    emit(response.message)
                }
            } catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO) as Flow<DMessage>

    fun registerUser(
        username: String,
        password: String,
        email: String,
        name: String,
        jenisKelamin: String
    ) = flow {
        try {
            val response = apiService.register(username, password, email, name, jenisKelamin)
            emit(response)
        } catch (e: Exception) {
            Log.e("error", e.toString())
        }
    }.flowOn(Dispatchers.IO)

    fun logoutUser(tokenUser: String) =
        flow {
            try {
                val response = apiService.logout(token = "Bearer $tokenUser")
                emit(response)
            } catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)
}