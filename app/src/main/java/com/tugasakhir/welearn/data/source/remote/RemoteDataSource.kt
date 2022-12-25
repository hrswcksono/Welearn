package com.tugasakhir.welearn.data.source.remote

import android.util.Log
import com.tugasakhir.welearn.data.source.remote.network.ApiResponse
import com.tugasakhir.welearn.data.source.remote.network.ApiService
import com.tugasakhir.welearn.data.source.remote.response.*
import com.tugasakhir.welearn.core.utils.Constants.Companion.FCM_BASE_URL
import com.tugasakhir.welearn.domain.entity.PushNotification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.Exception

class RemoteDataSource (private val apiService: ApiService) {

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

    fun soalMultiplayer(jenis: Int ,level: Int, tokenUser: String) =
        flow {
            try {
                val response = apiService.getIDSoalMulti(jenis ,level,token = "Bearer $tokenUser")
                emit(response)
            } catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun soalByID(id: Int, tokenUser: String) =
        flow {
            try {
            val response = apiService.getSoalByID(id, token = "Bearer $tokenUser")
            emit(response.message)
            } catch (e: Exception) {
            Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO) as Flow<SoalResponseMessage>

    fun scoreUser(id: Int, tokenUser: String) =
        flow {
            try {
                val response = apiService.scoreUser(id, token = "Bearer $tokenUser")
                emit(response.message)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO) as Flow<ScoreResponse>

    fun highScore(id: Int, tokenUser: String) =
        flow {
            try {
                val response = apiService.getHighScore(id ,token = "Bearer $tokenUser")
                emit(response.message)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun pushNotification(body: PushNotification) =
        flow {
            try {
                val response = apiService.postNotification(FCM_BASE_URL, body)
                emit(response)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun predictAngka(idSoal: Int, score: Int, tokenUser: String) =
        flow{
            try {
                val response = apiService.predictAngka(idSoal, score, token = "Bearer $tokenUser")
                emit(response)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun predictHuruf(idSoal: Int, score: Int, tokenUser: String) =
        flow{
            try {
                val response = apiService.predictHuruf(idSoal, score, token = "Bearer $tokenUser")
                emit(response)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun makeRoomGame(id_jenis: Int, id_level: Int, tokenUser: String) =
        flow{
            try {
                val response = apiService.makeRoom(id_jenis, id_level,token = "Bearer $tokenUser")
                emit(response)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun joinGame(idGame: String, tokenUser: String) =
        flow{
            try {
                val response = apiService.joinGame(idGame.toInt(), token = "Bearer $tokenUser")
                emit(response)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun endGame(idGame: String, tokenUser: String) =
        flow{
            try {
                val response = apiService.endGame(idGame, token = "Bearer $tokenUser")
                emit(response)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun scoreMulti(id: Int, tokenUser: String) =
        flow{
            try {
                val response = apiService.scoreMulti(id, token = "Bearer $tokenUser")
                emit(response.message)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun predictAngkaMulti(idGame: Int, idSoal: Int,score: Int , duration: Int, tokenUser: String) =
        flow{
            try {
                val response = apiService.predictAngkaMulti(idGame, idSoal,score, duration, token = "Bearer $tokenUser")
                emit(response)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun predictHurufMulti(idGame: Int, idSoal: Int,score: Int , duration: Int, tokenUser: String) =
        flow{
            try {
                val response = apiService.predictHurufMulti(idGame, idSoal,score, duration, token = "Bearer $tokenUser")
                emit(response)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun getJoinedGame(tokenUser: String) =
        flow{
            try {
                val response = apiService.joinedUser(token = "Bearer $tokenUser")
                emit(response.message)
            }catch (e: Exception) {
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun levelSoal(level: Int, tokenUser: String) =
        flow {
            try {
                val response = apiService.getLevel(level,token = "Bearer $tokenUser")
                emit(response.message)
            }catch (e: Exception) {
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun randSoalSingle(jenis : Int,level: Int, tokenUser: String) =
        flow {
            try {
                val response = apiService.getRandomSoal(jenis, level, token = "Bearer $tokenUser")
                emit(response.message)
            } catch (e: Exception) {
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun userParticipant(id: Int, tokenUser: String) =
        flow {
            try {
                val response = apiService.getUserParticipant(id,token = "Bearer $tokenUser")
                val data = response.message
                if (data != null) {
                    if (data.isNotEmpty()){
                        emit(ApiResponse.Success(response.message))
                    }else{
                        emit(ApiResponse.Empty)
                    }
                }
            }catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
}