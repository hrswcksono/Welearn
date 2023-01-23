package com.tugasakhir.welearn.domain.repository

import com.tugasakhir.welearn.data.source.remote.response.PushNotificationResponse
import com.tugasakhir.welearn.domain.entity.PushNotification
import com.tugasakhir.welearn.domain.entity.ScoreMultiEntity
import com.tugasakhir.welearn.domain.entity.UserJoinEntity
import com.tugasakhir.welearn.domain.entity.UserPaticipantEntity
import kotlinx.coroutines.flow.Flow

interface IMultiPlayerRepository {
    fun makeRoomGame(id_jenis: Int, id_level: Int): Flow<String>
    fun joinGame(idGame: String): Flow<String>
    fun endGame(idGame: String): Flow<String>
    fun predictHurufMulti(idGame: Int,idSoal: Int, score: Int, duration: Int): Flow<String>
    fun predictAngkaMulti(idGame: Int,idSoal: Int,score: Int , duration: Int): Flow<String>
    fun getJoinedGame(): Flow<List<UserJoinEntity>>
    fun pushNotification(body: PushNotification): Flow<PushNotificationResponse>
    fun getUserParticipant(idGame: Int): Flow<List<UserPaticipantEntity>>
}