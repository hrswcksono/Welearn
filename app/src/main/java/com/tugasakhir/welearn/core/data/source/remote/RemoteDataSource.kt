package com.tugasakhir.welearn.core.data.source.remote

import android.util.Log
import com.tugasakhir.welearn.core.data.source.remote.network.ApiService
import com.tugasakhir.welearn.core.data.source.remote.response.*
import com.tugasakhir.welearn.core.utils.Constants.Companion.FCM_BASE_URL
import com.tugasakhir.welearn.domain.model.PushNotification
import com.tugasakhir.welearn.domain.model.PushNotificationStart
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
                } else if (response.error != null) {

                }
            } catch (e: Exception){
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO) as Flow<Message>

    fun detailUser(token: String) =
        flow {
            try {
                val response = apiService.detail(token = "Bearer ${token}")
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
        jenis_kelamin: String
    ) = flow {
            try {
                val response = apiService.register(username, password, email, name, jenis_kelamin)
                emit(response)
            } catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun logoutUser(token: String) =
        flow {
            try {
                val response = apiService.logout(token = "Bearer ${token}")
                emit(response)
            } catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun soalAngkaMultiplayer(level: Int,token: String) =
        flow {
            try {
                val response = apiService.getSoalAngkaRandom(level,token = "Bearer ${token}")
                emit(response)
            } catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun soalHurufMultiplayer(level: Int, token: String) =
        flow {
            try {
                val response = apiService.getSoalHurufRandom(level, token = "Bearer ${token}")
                emit(response)
            } catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

//    fun soalAngkaByID(id: Int, token: String) =
//        flow {
//            try {
//                val response = apiService.getSoalAngkabyID(id, token = "Bearer ${token}")
//                emit(response.message)
//            } catch (e: Exception) {
//                Log.e("error", e.toString())
//            }
//        }.flowOn(Dispatchers.IO) as Flow<SMessage>
//
//    fun soalHurufByID(id: Int, token: String) =
//        flow {
//            try {
//                val response = apiService.getSoalHurufbyID(id, token = "Bearer ${token}")
//                emit(response.message)
//            } catch (e: Exception) {
//                Log.e("error", e.toString())
//            }
//        }.flowOn(Dispatchers.IO) as Flow<SMessage>

    fun soalByID(id: Int, token: String) =
        flow {
            try {
            val response = apiService.getSoalByID(id, token = "Bearer ${token}")
            emit(response.message)
            } catch (e: Exception) {
            Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO) as Flow<SMessage>

    fun randAngka(level: Int, token: String) =
        flow {
            try {
                val response = apiService.getRandomAngka(level, token = "Bearer ${token}")
                emit(response.message)
            } catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun randHuruf(level: Int, token: String) =
        flow {
            try {
                val response = apiService.getRandomHuruf(level, token = "Bearer ${token}")
                emit(response.message)
            } catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)


    fun scoreAngkaUser(token: String) =
        flow {
            try {
                val response = apiService.scoreAngkaUser(token = "Bearer ${token}")
                emit(response.message)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO) as Flow<ScoreMessage>

    fun scoreHurufUser(token: String) =
        flow {
            try {
                val response = apiService.scoreHurufUser(token = "Bearer ${token}")
                emit(response.message)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO) as Flow<ScoreMessage>

    fun highScoreAngka(token: String) =
        flow {
            try {
                val response = apiService.getHighScoreAngka(token = "Bearer ${token}")
                emit(response.message)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun highScoreHuruf(token: String) =
        flow {
            try {
                val response = apiService.getHighScoreHuruf(token = "Bearer ${token}")
                emit(response.message)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun levelSoal(id_level: Int, token: String) =
        flow {
            try {
                val response = apiService.getLevel(id_level,token = "Bearer ${token}")
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

    fun pushNotificationStart(body: PushNotificationStart) =
        flow {
            try {
                val response = apiService.postStartGame(FCM_BASE_URL, body)
                emit(response)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun predictAngka(id_soal: Int, image: ArrayList<String>, token: String) =
        flow{
            try {
                val response = apiService.predictAngka(id_soal, image, token = "Bearer ${token}")
                emit(response)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun predictHuruf(id_soal: Int, image: ArrayList<String>, token: String) =
        flow{
            try {
                val response = apiService.predictHuruf(id_soal, image, token = "Bearer ${token}")
                emit(response)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun testPredict(input: String, token: String) =
        flow{
            try {
                val response = apiService.test(input, token = "Bearer ${token}")
                emit(response)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun makeRoomGame(token: String) =
        flow{
            try {
                val response = apiService.makeRoom(token = "Bearer ${token}")
                emit(response)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun joinGame(id_game: String, token: String) =
        flow{
            try {
                val response = apiService.joinGame(id_game.toInt(), token = "Bearer ${token}")
                emit(response)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun endGame(id_game: String, token: String) =
        flow{
            try {
                val response = apiService.endGame(id_game, token = "Bearer ${token}")
                emit(response)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun scoreMulti(id: Int ,token: String) =
        flow{
            try {
                val response = apiService.scoreMulti(id, token = "Bearer ${token}")
                emit(response.message)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun predictAngkaMulti(id_game: Int, id_jenis: Int , image: ArrayList<String>, token: String) =
        flow{
            try {
                val response = apiService.predictAngkaMulti(id_game, id_jenis, image , token = "Bearer ${token}")
                emit(response)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun predictHurufMulti(id_game: Int, id_soal: Int, duration: Int,token: String) =
        flow{
            try {
                val response = apiService.predictHurufMulti(id_game, id_soal, duration, token = "Bearer ${token}")
                emit(response)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun getJoinedGame(token: String) =
        flow{
            try {
                val response = apiService.joinedUser(token = "Bearer ${token}")
                emit(response.message)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)


}