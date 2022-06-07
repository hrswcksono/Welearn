package com.tugasakhir.welearn.domain.repository

import com.tugasakhir.welearn.core.data.source.remote.response.PushNotificationResponse
import com.tugasakhir.welearn.domain.model.*
import kotlinx.coroutines.flow.Flow

interface IWelearnRepository {
    fun loginUser(username: String, password: String): Flow<Login>
    fun detailUser(token: String): Flow<User>
    fun registerUser(username: String,
                     password: String,
                     email: String,
                     name: String,
                     jenis_kelamin: String): Flow<String>
    fun logoutUser(token: String) : Flow<String>
    fun randAngka(level: Int, token: String): Flow<List<Soal>>
    fun randHuruf(level: Int, token: String): Flow<List<Soal>>
    fun soalAngkaMultiplayer(level: Int, token: String): Flow<String>
    fun soalHurufMultiplayer(level: Int, token: String): Flow<Soal>
    fun soalAngkaByID(id:Int, token: String): Flow<Soal>
    fun soalHurufByID(id: Int, token: String): Flow<Soal>
    fun scoreAngkaUser(token: String): Flow<Score>
    fun scoreHurufUser(token: String): Flow<Score>
    fun highScoreAngka(token: String): Flow<List<UserScore>>
    fun highScoreHuruf(token: String): Flow<List<UserScore>>
    fun pushNotification(body: PushNotification): Flow<PushNotificationResponse>
    fun pushStartNotification(body: PushNotificationStart): Flow<PushNotificationResponse>
    fun predictAngka(id_soal: String, image: ArrayList<String>, token: String): Flow<ResultPredict>
    fun predictHuruf(id_soal: String, image: ArrayList<String>, token: String): Flow<ResultPredict>
    fun predictTest(input: String, token: String): Flow<ResultPredict>
    fun makeRoomGame(token: String): Flow<String>
    fun joinGame(id_game: String,token: String): Flow<String>
    fun endGame(id_game: String,token: String): Flow<String>
    fun scoreMulti(id_game: Int,token: String): Flow<List<ScoreMulti>>
    fun predictHurufMulti(id_game: Int, id_jenis: Int, image: ArrayList<String> ,token: String): Flow<String>
    fun predictAngkaMulti(id_game: Int, id_jenis: Int, image: ArrayList<String> ,token: String): Flow<String>
}