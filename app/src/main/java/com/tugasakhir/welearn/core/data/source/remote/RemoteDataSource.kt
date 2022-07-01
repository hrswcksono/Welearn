package com.tugasakhir.welearn.core.data.source.remote

import android.content.SharedPreferences
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

    companion object {
        var sharedPref: SharedPreferences? = null

        var tokenUser: String?
            get() {
                return sharedPref?.getString("token", "")
            }
            set(value) {
                sharedPref?.edit()?.putString("token", value)?.apply()
            }
    }

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

    fun detailUser() =
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
        jenis_kelamin: String
    ) = flow {
            try {
                val response = apiService.register(username, password, email, name, jenis_kelamin)
                emit(response)
            } catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun logoutUser() =
        flow {
            try {
                val response = apiService.logout(token = "Bearer $tokenUser")
                emit(response)
            } catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun soalAngkaMultiplayer(level: Int) =
        flow {
            try {
                val response = apiService.getSoalAngkaRandom(level,token = "Bearer $tokenUser")
                emit(response)
            } catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun soalHurufMultiplayer(level: Int) =
        flow {
            try {
                val response = apiService.getSoalHurufRandom(level, token = "Bearer $tokenUser")
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

    fun soalByID(id: Int) =
        flow {
            try {
            val response = apiService.getSoalByID(id, token = "Bearer $tokenUser")
            emit(response.message)
            } catch (e: Exception) {
            Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO) as Flow<SMessage>

    fun randAngka(level: Int) =
        flow {
            try {
                val response = apiService.getRandomAngka(level, token = "Bearer $tokenUser")
                emit(response.message)
            } catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun randHuruf(level: Int) =
        flow {
            try {
                val response = apiService.getRandomHuruf(level, token = "Bearer $tokenUser")
                emit(response.message)
            } catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)


    fun scoreAngkaUser() =
        flow {
            try {
                val response = apiService.scoreAngkaUser(token = "Bearer $tokenUser")
                emit(response.message)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO) as Flow<ScoreMessage>

    fun scoreHurufUser() =
        flow {
            try {
                val response = apiService.scoreHurufUser(token = "Bearer $tokenUser")
                emit(response.message)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO) as Flow<ScoreMessage>

    fun highScoreAngka() =
        flow {
            try {
                val response = apiService.getHighScoreAngka(token = "Bearer $tokenUser")
                emit(response.message)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun highScoreHuruf() =
        flow {
            try {
                val response = apiService.getHighScoreHuruf(token = "Bearer $tokenUser")
                emit(response.message)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun levelSoal(id_level: Int) =
        flow {
            try {
                val response = apiService.getLevel(id_level,token = "Bearer $tokenUser")
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

    fun predictAngka(id_soal: Int, image: ArrayList<String>) =
        flow{
            try {
                val response = apiService.predictAngka(id_soal, image, token = "Bearer $tokenUser")
                emit(response)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun predictHuruf(id_soal: Int, image: ArrayList<String>) =
        flow{
            try {
                val response = apiService.predictHuruf(id_soal, image, token = "Bearer $tokenUser")
                emit(response)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun testPredict(input: String) =
        flow{
            try {
                val response = apiService.test(input, token = "Bearer $tokenUser")
                emit(response)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun makeRoomGame(id_jenis: Int) =
        flow{
            try {
                val response = apiService.makeRoom(id_jenis,token = "Bearer $tokenUser")
                emit(response)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun joinGame(id_game: String) =
        flow{
            try {
                val response = apiService.joinGame(id_game.toInt(), token = "Bearer $tokenUser")
                emit(response)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun endGame(id_game: String) =
        flow{
            try {
                val response = apiService.endGame(id_game, token = "Bearer $tokenUser")
                emit(response)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun scoreMulti(id: Int) =
        flow{
            try {
                val response = apiService.scoreMulti(id, token = "Bearer $tokenUser")
                emit(response.message)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun predictAngkaMulti(id_game: Int, id_soal: Int,image: ArrayList<String> , duration: Int) =
        flow{
            try {
                val response = apiService.predictAngkaMulti(id_game, id_soal,image, duration, token = "Bearer $tokenUser")
                emit(response)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun predictHurufMulti(id_game: Int, id_soal: Int,image: ArrayList<String> , duration: Int) =
        flow{
            try {
                val response = apiService.predictHurufMulti(id_game, id_soal,image, duration, token = "Bearer $tokenUser")
                emit(response)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun getJoinedGame() =
        flow{
            try {
                val response = apiService.joinedUser(token = "Bearer $tokenUser")
                emit(response.message)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)


}