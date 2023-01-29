package com.tugasakhir.welearn.domain.usecase.multiplayer

import com.tugasakhir.welearn.data.source.remote.response.PushNotificationResponse
import com.tugasakhir.welearn.domain.entity.*
import kotlinx.coroutines.flow.Flow

interface MultiPlayerUseCase {
    fun makeRoom(idJenis: Int, idLevel: Int, authToken: String): Flow<Room>
    fun joinGame(idRoom: Int, authToken: String): Flow<JoinGame>
    fun gameAlreadyEnd(idGame: String, authToken: String): Flow<String>
    fun forcedEndGame(idGame: String, authToken: String): Flow<String>
    fun savePredictHurufMulti(idGame: Int,idSoal: Int,score: Int , duration: Int, authToken: String): Flow<SavePredictMulti>
    fun savePredictAngkaMulti(idGame: Int,idSoal: Int,score: Int , duration: Int, authToken: String): Flow<SavePredictMulti>
    fun getListUserJoin(authToken: String): Flow<List<UserJoinMulti>>
    fun getListUserParticipant(idGame: Int, authToken: String): Flow<List<UserPaticipantMulti>>
    fun pushNotification(body: PushNotification): Flow<PushNotificationResponse>
    fun getIDSoalMulti(jenis: Int,level: Int, authToken: String): Flow<IDSoalMulti>
    fun getSoalByID(id: Int, authToken: String): Flow<Soal>
    fun getListScoreMulti(idGame: Int, authToken: String): Flow<List<ScoreMulti>>
}