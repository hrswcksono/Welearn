package com.tugasakhir.welearn.core.data.source.remote

import android.util.Log
import com.tugasakhir.welearn.core.data.source.remote.network.ApiService
import com.tugasakhir.welearn.core.data.source.remote.response.*
import com.tugasakhir.welearn.core.utils.Constants.Companion.FCM_BASE_URL
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

    fun soalAngkaMultiplayer(level: Int,token: String): Flow<SMessage> {
        return flow {
            try {
                val response = apiService.getSoalAngkaRandom(level,token = "Bearer ${token}")
                emit(response.message)
            } catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO) as Flow<SMessage>
    }

    fun soalHurufMultiplayer(level: Int, token: String): Flow<SMessage> {
        return flow {
            try {
                val response = apiService.getSoalHurufRandom(level, token = "Bearer ${token}")
                emit(response.message)
            } catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO) as Flow<SMessage>
    }

    fun soalAngkaByID(id: Int, token: String): Flow<SMessage> {
        return flow {
            try {
                val response = apiService.getSoalAngkabyID(id, token)
                emit(response.message)
            } catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO) as Flow<SMessage>
    }

    fun soalHurufByID(id: Int, token: String): Flow<SMessage> {
        return flow {
            try {
                val response = apiService.getSoalHurufbyID(id, token)
                emit(response.message)
            } catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO) as Flow<SMessage>
    }

    fun randAngka(level: Int, token: String): Flow<List<MessageItem>> {
        return flow {
            try {
                val response = apiService.getRandomAngka(level, token = "Bearer ${token}")
                emit(response.message)
            } catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun randHuruf(level: Int, token: String): Flow<List<MessageItem>> {
        return flow {
            try {
                val response = apiService.getRandomHuruf(level, token = "Bearer ${token}")
                emit(response.message)
            } catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun highScoreAngka(token: String): Flow<List<HMessageItem>> {
        return flow {
            try {
                val response = apiService.getHighScoreAngka(token = "Bearer ${token}")
                emit(response.message)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun highScoreHuruf(token: String): Flow<List<HMessageItem>> {
        return flow {
            try {
                val response = apiService.getHighScoreHuruf(token = "Bearer ${token}")
                emit(response.message)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun pushNotification(body: PushNotification): Flow<PushNotificationResponse> {
        return flow {
            try {
                val response = apiService.postNotification(FCM_BASE_URL, body)
                emit(response)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}