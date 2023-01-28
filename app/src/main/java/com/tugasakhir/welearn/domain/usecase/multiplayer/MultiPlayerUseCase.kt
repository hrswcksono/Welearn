package com.tugasakhir.welearn.domain.usecase.multiplayer

import com.tugasakhir.welearn.data.source.remote.response.PushNotificationResponse
import com.tugasakhir.welearn.domain.entity.*
import kotlinx.coroutines.flow.Flow

interface MultiPlayerUseCase {
    fun makeRoomGame(idJenis: Int, idLevel: Int, authToken: String): Flow<Room>
    fun joinGame(idRoom: Int, authToken: String): Flow<JoinGame>
    fun gameAlreadyEnd(idGame: String, authToken: String): Flow<String>
    fun forcedEndGame(idGame: String, authToken: String): Flow<String>
    fun predictHurufMulti(idGame: Int,idSoal: Int,score: Int , duration: Int, authToken: String): Flow<SavePredictMulti>
    fun predictAngkaMulti(idGame: Int,idSoal: Int,score: Int , duration: Int, authToken: String): Flow<SavePredictMulti>
    fun userJoinedGame(authToken: String): Flow<List<UserJoinMulti>>
    fun getUserParticipant(idGame: Int, authToken: String): Flow<List<UserPaticipantMulti>>
    fun pushNotification(body: PushNotification): Flow<PushNotificationResponse>
    fun getIDSoalMultiplayer(jenis: Int,level: Int, authToken: String): Flow<IDSoalMulti>
    fun getSoalByID(id: Int, authToken: String): Flow<Soal>
    fun scoreMulti(idGame: Int, authToken: String): Flow<List<ScoreMulti>>
}