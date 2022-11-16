package com.tugasakhir.welearn.domain.usecase

import com.tugasakhir.welearn.data.Resource
import com.tugasakhir.welearn.data.source.remote.response.PushNotificationResponse
import com.tugasakhir.welearn.domain.entity.*
import kotlinx.coroutines.flow.Flow

interface WelearnUseCase {
    fun userLogin(username: String, password: String) : Flow<LoginEntity>
    fun userDetail() : Flow<ProfileEntity>
    fun userRegister(username: String, password: String, email: String, name: String, jenisKelamin: String): Flow<String>
    fun userLogout(): Flow<String>
    fun getIDSoalMultiplayer(jenis: Int,level: Int): Flow<String>
    fun getSoalByID(id: Int): Flow<SoalEntity>
    fun userScore(id: Int): Flow<ScoreEntity>
    fun getHighScore(id: Int): Flow<List<RankingScoreEntity>>
    fun pushNotification(body: PushNotification): Flow<PushNotificationResponse>
    fun angkaPredict(idSoal: Int, image: ArrayList<String>): Flow<ResultPredictEntity>
    fun hurufPredict(idSoal: Int, image: ArrayList<String>): Flow<ResultPredictEntity>
    fun makeRoomGame(idJenis: Int): Flow<String>
    fun joinGame(idGame: String): Flow<String>
    fun endGame(idGame: String): Flow<String>
    fun scoreMulti(idGame: Int): Flow<List<ScoreMultiEntity>>
    fun predictHurufMulti(idGame: Int,idSoal: Int,image: ArrayList<String> , duration: Int): Flow<String>
    fun predictAngkaMulti(idGame: Int,idSoal: Int,image: ArrayList<String> , duration: Int): Flow<String>
    fun userJoinedGame(): Flow<List<UserJoinEntity>>

    fun getSoalRandomSinglePlayer(jenis: Int, level: Int): Flow<List<SoalEntity>>
    fun getLevel(idLevel: Int): Flow<List<LevelEntity>>
    fun getUserParticipant(idGame: Int): Flow<Resource<List<UserPaticipantEntity>>>
}