package com.tugasakhir.welearn.domain.repository

import com.tugasakhir.welearn.core.data.source.remote.response.PushNotificationResponse
import com.tugasakhir.welearn.domain.model.*
import kotlinx.coroutines.flow.Flow

interface IWelearnRepository {
    fun loginUser(username: String, password: String): Flow<Login>
    fun detailUser(): Flow<User>
    fun registerUser(username: String, password: String, email: String, name: String, jenis_kelamin: String): Flow<String>
    fun logoutUser() : Flow<String>
    fun randAngka(level: Int): Flow<List<Soal>>
    fun randHuruf(level: Int): Flow<List<Soal>>
    fun soalAngkaMultiplayer(level: Int): Flow<String>
    fun soalHurufMultiplayer(level: Int): Flow<String>
    fun soalByID(id: Int): Flow<Soal>
    fun scoreAngkaUser(): Flow<Score>
    fun scoreHurufUser(): Flow<Score>
    fun highScoreAngka(): Flow<List<UserScore>>
    fun highScoreHuruf(): Flow<List<UserScore>>
    fun pushNotification(body: PushNotification): Flow<PushNotificationResponse>
    fun pushStartNotification(body: PushNotificationStart): Flow<PushNotificationResponse>
    fun predictAngka(id_soal: Int, image: ArrayList<String>): Flow<ResultPredict>
    fun predictHuruf(id_soal: Int, image: ArrayList<String>): Flow<ResultPredict>
    fun predictTest(input: String): Flow<ResultPredict>
    fun makeRoomGame(id_jenis: Int,): Flow<String>
    fun joinGame(id_game: String,): Flow<String>
    fun endGame(id_game: String,): Flow<String>
    fun scoreMulti(id_game: Int,): Flow<List<ScoreMulti>>
    fun predictHurufMulti(id_game: Int,id_soal: Int, image: ArrayList<String>, duration: Int ,): Flow<String>
    fun predictAngkaMulti(id_game: Int,id_soal: Int,image: ArrayList<String> , duration: Int,): Flow<String>
    fun getJoinedGame(): Flow<List<UserJoin>>
    fun getLevel(id_level: Int): Flow<List<Level>>
}