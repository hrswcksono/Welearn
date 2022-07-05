package com.tugasakhir.welearn.domain.usecase

import com.tugasakhir.welearn.core.data.source.remote.response.PushNotificationResponse
import com.tugasakhir.welearn.domain.model.*
import kotlinx.coroutines.flow.Flow

interface WelearnUseCase {
    fun userLogin(username: String, password: String) : Flow<Login>
    fun userDetail() : Flow<User>
    fun userRegister(username: String, password: String, email: String, name: String, jenisKelamin: String): Flow<String>
    fun userLogout(): Flow<String>
    fun getSoalRandomSinglePlayer(jenis: Int, level: Int): Flow<List<Soal>>
    fun getIDSoalMultiplayer(jenis: Int,level: Int): Flow<String>
    fun getSoalByID(id: Int): Flow<Soal>
    fun userScore(id: Int): Flow<Score>
    fun getHighScore(id: Int): Flow<List<RankingScore>>
    fun pushNotification(body: PushNotification): Flow<PushNotificationResponse>
    fun angkaPredict(idSoal: Int, image: ArrayList<String>): Flow<ResultPredict>
    fun hurufPredict(idSoal: Int, image: ArrayList<String>): Flow<ResultPredict>
    fun makeRoomGame(idJenis: Int): Flow<String>
    fun joinGame(idGame: String): Flow<String>
    fun endGame(idGame: String): Flow<String>
    fun scoreMulti(idGame: Int): Flow<List<ScoreMulti>>
    fun predictHurufMulti(idGame: Int,idSoal: Int,image: ArrayList<String> , duration: Int): Flow<String>
    fun predictAngkaMulti(idGame: Int,idSoal: Int,image: ArrayList<String> , duration: Int): Flow<String>
    fun userJoinedGame(): Flow<List<UserJoin>>
    fun getLevel(idLevel: Int): Flow<List<Level>>
}