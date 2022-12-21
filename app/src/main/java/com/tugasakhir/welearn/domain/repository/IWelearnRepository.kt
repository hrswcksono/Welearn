package com.tugasakhir.welearn.domain.repository

import com.tugasakhir.welearn.data.Resource
import com.tugasakhir.welearn.data.source.remote.response.PushNotificationResponse
import com.tugasakhir.welearn.domain.entity.*
import kotlinx.coroutines.flow.Flow

interface IWelearnRepository {
    fun loginUser(username: String, password: String): Flow<LoginEntity>
    fun detailUser(): Flow<ProfileEntity>
    fun registerUser(username: String, password: String, email: String, name: String, jenisKelamin: String): Flow<String>
    fun logoutUser() : Flow<String>
    fun getIDSoalMultiplayer(jenis: Int, level: Int): Flow<String>
    fun soalByID(id: Int): Flow<SoalEntity>
    fun scoreUser(id: Int): Flow<ScoreEntity>
    fun highScore(id: Int): Flow<List<RankingScoreEntity>>
    fun pushNotification(body: PushNotification): Flow<PushNotificationResponse>
    fun predictAngka(idSoal: Int, score: Int): Flow<ResultPredictEntity>
    fun predictHuruf(idSoal: Int, score: Int): Flow<ResultPredictEntity>
    fun makeRoomGame(id_jenis: Int, id_level: Int): Flow<String>
    fun joinGame(idGame: String): Flow<String>
    fun endGame(idGame: String): Flow<String>
    fun scoreMulti(idGame: Int): Flow<List<ScoreMultiEntity>>
    fun predictHurufMulti(idGame: Int,idSoal: Int, score: Int, duration: Int): Flow<String>
    fun predictAngkaMulti(idGame: Int,idSoal: Int,score: Int , duration: Int): Flow<String>
    fun getJoinedGame(): Flow<List<UserJoinEntity>>
    fun getLevel(idLevel: Int): Flow<List<LevelEntity>>
    fun getRandSoalSingle(jenis: Int ,level: Int): Flow<List<SoalEntity>>
    fun getUserParticipant(idGame: Int): Flow<Resource<List<UserPaticipantEntity>>>
}