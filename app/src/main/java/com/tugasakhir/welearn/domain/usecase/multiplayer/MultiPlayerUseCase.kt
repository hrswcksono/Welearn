package com.tugasakhir.welearn.domain.usecase.multiplayer

import com.tugasakhir.welearn.data.source.remote.response.PushNotificationResponse
import com.tugasakhir.welearn.domain.entity.*
import kotlinx.coroutines.flow.Flow

interface MultiPlayerUseCase {
    fun makeRoomGame(idJenis: Int, idLevel: Int, authToken: String): Flow<String>
    fun joinGame(idGame: String, authToken: String): Flow<String>
    fun endGame(idGame: String, authToken: String): Flow<String>
    fun predictHurufMulti(idGame: Int,idSoal: Int,score: Int , duration: Int, authToken: String): Flow<String>
    fun predictAngkaMulti(idGame: Int,idSoal: Int,score: Int , duration: Int, authToken: String): Flow<String>
    fun userJoinedGame(authToken: String): Flow<List<UserJoinMulti>>
    fun getUserParticipant(idGame: Int, authToken: String): Flow<List<UserPaticipantMulti>>
    fun pushNotification(body: PushNotification): Flow<PushNotificationResponse>
    fun getIDSoalMultiplayer(jenis: Int,level: Int, authToken: String): Flow<IDSoalMulti>
    fun getSoalByID(id: Int, authToken: String): Flow<Soal>
    fun scoreMulti(idGame: Int, authToken: String): Flow<List<ScoreMulti>>
}