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
                     jenisKelamin: String): Flow<String>
    fun userLogout(): Flow<String>
    fun angkaRandom(level: Int): Flow<List<Soal>>
    fun hurufRandom(level: Int): Flow<List<Soal>>
    fun soalMultiplayerAngka(level: Int): Flow<String>
    fun soalMultiplayerHuruf(level: Int): Flow<String>
    fun getSoalByID(id: Int): Flow<Soal>
    fun userAngkaScore(): Flow<Score>
    fun userHurufScore(): Flow<Score>
    fun angkaHighScore(): Flow<List<RankingScore>>
    fun hurufHighScore(): Flow<List<RankingScore>>
    fun pushNotification(body: PushNotification): Flow<PushNotificationResponse>
//    fun pushNotificationStart(body: PushNotificationStart): Flow<PushNotificationResponse>
    fun angkaPredict(idSoal: Int, image: ArrayList<String>): Flow<ResultPredict>
    fun hurufPredict(idSoal: Int, image: ArrayList<String>): Flow<ResultPredict>
    fun testPredict(input: String): Flow<ResultPredict>
    fun makeRoomGame(idJenis: Int): Flow<String>
    fun joinGame(idGame: String): Flow<String>
    fun endGame(idGame: String): Flow<String>
    fun scoreMulti(idGame: Int): Flow<List<ScoreMulti>>
    fun predictHurufMulti(idGame: Int,idSoal: Int,image: ArrayList<String> , duration: Int): Flow<String>
    fun predictAngkaMulti(idGame: Int,idSoal: Int,image: ArrayList<String> , duration: Int): Flow<String>
    fun userJoinedGame(): Flow<List<UserJoin>>
    fun getLevel(idLevel: Int): Flow<List<Level>>
}