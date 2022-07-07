package com.tugasakhir.welearn.data

import android.content.SharedPreferences
import android.util.Log
import com.tugasakhir.welearn.core.data.source.remote.network.ApiService
import com.tugasakhir.welearn.core.data.source.remote.response.DMessage
import com.tugasakhir.welearn.core.data.source.remote.response.Message
import com.tugasakhir.welearn.core.data.source.remote.response.SMessage
import com.tugasakhir.welearn.core.data.source.remote.response.ScoreMessage
import com.tugasakhir.welearn.core.utils.Constants
import com.tugasakhir.welearn.domain.entity.PushNotification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FakeRemoteDataSource(private val apiService: ApiService) {

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
        jenisKelamin: String
    ) = flow {
        try {
            val response = apiService.register(username, password, email, name, jenisKelamin)
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

    fun soalMultiplayer(jenis: Int ,level: Int) =
        flow {
            try {
                val response = apiService.getIDSoalMulti(jenis ,level,token = "Bearer $tokenUser")
                emit(response)
            } catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun soalByID(id: Int) =
        flow {
            try {
                val response = apiService.getSoalByID(id, token = "Bearer $tokenUser")
                emit(response.message)
            } catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO) as Flow<SMessage>

    fun randSoalSingle(jenis : Int,level: Int) =
        flow {
            try {
                val response = apiService.getRandomSoal(jenis, level, token = "Bearer $tokenUser")
                emit(response.message)
            } catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun scoreUser(id: Int) =
        flow {
            try {
                val response = apiService.scoreUser(id, token = "Bearer $tokenUser")
                emit(response.message)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO) as Flow<ScoreMessage>

    fun highScore(id: Int) =
        flow {
            try {
                val response = apiService.getHighScore(id ,token = "Bearer $tokenUser")
                emit(response.message)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun levelSoal(level: Int) =
        flow {
            try {
                val response = apiService.getLevel(level,token = "Bearer $tokenUser")
                emit(response.message)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun pushNotification(body: PushNotification) =
        flow {
            try {
                val response = apiService.postNotification(Constants.FCM_BASE_URL, body)
                emit(response)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun predictAngka(idSoal: Int, image: ArrayList<String>) =
        flow{
            try {
                val response = apiService.predictAngka(idSoal, image, token = "Bearer $tokenUser")
                emit(response)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun predictHuruf(idSoal: Int, image: ArrayList<String>) =
        flow{
            try {
                val response = apiService.predictHuruf(idSoal, image, token = "Bearer $tokenUser")
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

    fun joinGame(idGame: String) =
        flow{
            try {
                val response = apiService.joinGame(idGame.toInt(), token = "Bearer $tokenUser")
                emit(response)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun endGame(idGame: String) =
        flow{
            try {
                val response = apiService.endGame(idGame, token = "Bearer $tokenUser")
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

    fun predictAngkaMulti(idGame: Int, idSoal: Int,image: ArrayList<String> , duration: Int) =
        flow{
            try {
                val response = apiService.predictAngkaMulti(idGame, idSoal,image, duration, token = "Bearer $tokenUser")
                emit(response)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun predictHurufMulti(idGame: Int, idSoal: Int,image: ArrayList<String> , duration: Int) =
        flow{
            try {
                val response = apiService.predictHurufMulti(idGame, idSoal,image, duration, token = "Bearer $tokenUser")
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