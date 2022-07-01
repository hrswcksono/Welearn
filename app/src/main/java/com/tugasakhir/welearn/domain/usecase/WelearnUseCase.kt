package com.tugasakhir.welearn.domain.usecase

import com.tugasakhir.welearn.core.data.source.remote.response.PushNotificationResponse
import com.tugasakhir.welearn.domain.model.*
import kotlinx.coroutines.flow.Flow

interface WelearnUseCase {
    fun userLogin(username: String, password: String) : Flow<Login>
    fun userDetail() : Flow<User>
    fun userRegister(username: String,
                     password: String,
                     email: String,
                     name: String,
                     jenis_kelamin: String): Flow<String>
    fun userLogout(): Flow<String>
    fun angkaRandom(level: Int): Flow<List<Soal>>
    fun hurufRandom(level: Int): Flow<List<Soal>>
    fun soalMultiplayerAngka(level: Int): Flow<String>
    fun soalMultiplayerHuruf(level: Int): Flow<String>
    fun getSoalByID(id: Int): Flow<Soal>
    fun userAngkaScore(): Flow<Score>
    fun userHurufScore(): Flow<Score>
    fun angkaHighScore(): Flow<List<UserScore>>
    fun hurufHighScore(): Flow<List<UserScore>>
    fun pushNotification(body: PushNotification): Flow<PushNotificationResponse>
    fun pushNotificationStart(body: PushNotificationStart): Flow<PushNotificationResponse>
    fun angkaPredict(id_soal: Int, image: ArrayList<String>): Flow<ResultPredict>
    fun hurufPredict(id_soal: Int, image: ArrayList<String>): Flow<ResultPredict>
    fun testPredict(input: String): Flow<ResultPredict>
    fun makeRoomGame(id_jenis: Int): Flow<String>
    fun joinGame(id_game: String): Flow<String>
    fun endGame(id_game: String): Flow<String>
    fun scoreMulti(id_game: Int): Flow<List<ScoreMulti>>
    fun predictHurufMulti(id_game: Int,id_soal: Int,image: ArrayList<String> , duration: Int): Flow<String>
    fun predictAngkaMulti(id_game: Int,id_soal: Int,image: ArrayList<String> , duration: Int): Flow<String>
    fun getJoinedGame(): Flow<List<UserJoin>>
    fun getLevel(id_level: Int): Flow<List<Level>>
}