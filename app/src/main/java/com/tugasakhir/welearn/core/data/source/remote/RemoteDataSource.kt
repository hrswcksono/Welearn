package com.tugasakhir.welearn.core.data.source.remote

import android.util.Log
import com.tugasakhir.welearn.core.data.source.remote.network.ApiService
import com.tugasakhir.welearn.core.data.source.remote.response.*
import com.tugasakhir.welearn.core.utils.SharedPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class RemoteDataSource (private val apiService: ApiService) {

    fun loginUser(username: String, password: String): Flow<Message> {
        return flow {
            try {
                val response = apiService.login(username, password)
                if (response.success != null) {
                    emit(response.message)
                } else if (response.error != null) {

                }
            } catch (e: Exception){
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO) as Flow<Message>
    }

    fun detailUser(token: String): Flow<DMessage> {
        return flow {
            try {
                val response = apiService.detail(token = "Bearer ${token}")
                if (response.success != null) {
                    emit(response.message)
                }
            } catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO) as Flow<DMessage>
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



    fun logoutUser(token: String): Flow<LogoutResponse> {
        return flow {
            try {
                val response = apiService.logout(token = "Bearer ${token}")
                emit(response)
            } catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }


}