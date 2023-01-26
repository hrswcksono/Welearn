package com.tugasakhir.welearn.domain.repository

import com.tugasakhir.welearn.data.source.remote.response.PushNotificationResponse
import com.tugasakhir.welearn.domain.entity.*
import kotlinx.coroutines.flow.Flow

interface IMultiPlayerRepository {
    fun getIDSoalMultiplayer(jenis: Int, level: Int, authToken: String): Flow<IDSoalMultiEntity>
    fun soalByID(id: Int, authToken: String): Flow<SoalEntity>
    fun pushNotification(body: PushNotification): Flow<PushNotificationResponse>
    fun makeRoomGame(id_jenis: Int, id_level: Int, authToken: String): Flow<String>
    fun joinGame(idGame: String, authToken: String): Flow<String>
    fun endGame(idGame: String, authToken: String): Flow<String>
    fun scoreMulti(idGame: Int, authToken: String): Flow<List<ScoreMultiEntity>>
    fun predictHurufMulti(idGame: Int,idSoal: Int, score: Int, duration: Int, authToken: String): Flow<String>
    fun predictAngkaMulti(idGame: Int,idSoal: Int,score: Int , duration: Int, authToken: String): Flow<String>
    fun getJoinedGame(authToken: String): Flow<List<UserJoinEntity>>
    fun getUserParticipant(idGame: Int, authToken: String): Flow<List<UserPaticipantEntity>>
}