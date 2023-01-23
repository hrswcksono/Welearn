package com.tugasakhir.welearn.domain.usecase.multiplayer

import com.tugasakhir.welearn.data.source.remote.response.PushNotificationResponse
import com.tugasakhir.welearn.domain.entity.*
import kotlinx.coroutines.flow.Flow

interface MultiPlayerUseCase {
    fun makeRoomGame(idJenis: Int, idLevel: Int): Flow<String>
    fun joinGame(idGame: String): Flow<String>
    fun endGame(idGame: String): Flow<String>
    fun predictHurufMulti(idGame: Int,idSoal: Int,score: Int , duration: Int): Flow<String>
    fun predictAngkaMulti(idGame: Int,idSoal: Int,score: Int , duration: Int): Flow<String>
    fun userJoinedGame(): Flow<List<UserJoinEntity>>
    fun getUserParticipant(idGame: Int): Flow<List<UserPaticipantEntity>>
    fun pushNotification(body: PushNotification): Flow<PushNotificationResponse>
}