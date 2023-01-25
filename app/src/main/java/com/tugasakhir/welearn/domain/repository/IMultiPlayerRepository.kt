package com.tugasakhir.welearn.domain.repository

import com.tugasakhir.welearn.data.source.remote.response.PushNotificationResponse
import com.tugasakhir.welearn.domain.entity.*
import kotlinx.coroutines.flow.Flow

interface IMultiPlayerRepository {
    fun getIDSoalMultiplayer(jenis: Int, level: Int): Flow<IDSoalMultiEntity>
    fun soalByID(id: Int): Flow<SoalEntity>
    fun pushNotification(body: PushNotification): Flow<PushNotificationResponse>
    fun makeRoomGame(id_jenis: Int, id_level: Int): Flow<String>
    fun joinGame(idGame: String): Flow<String>
    fun endGame(idGame: String): Flow<String>
    fun scoreMulti(idGame: Int): Flow<List<ScoreMultiEntity>>
    fun predictHurufMulti(idGame: Int,idSoal: Int, score: Int, duration: Int): Flow<String>
    fun predictAngkaMulti(idGame: Int,idSoal: Int,score: Int , duration: Int): Flow<String>
    fun getJoinedGame(): Flow<List<UserJoinEntity>>
    fun getUserParticipant(idGame: Int): Flow<List<UserPaticipantEntity>>
}