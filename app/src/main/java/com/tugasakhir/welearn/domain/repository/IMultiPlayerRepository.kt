package com.tugasakhir.welearn.domain.repository

import com.tugasakhir.welearn.data.source.remote.response.PushNotificationResponse
import com.tugasakhir.welearn.domain.entity.*
import kotlinx.coroutines.flow.Flow

interface IMultiPlayerRepository {
    fun getIDSoalMulti(jenis: Int, level: Int, authToken: String): Flow<IDSoalMulti>
    fun getSoalByID(id: Int, authToken: String): Flow<Soal>
    fun pushNotification(body: PushNotification): Flow<PushNotificationResponse>
    fun makeRoom(id_jenis: Int, id_level: Int, authToken: String): Flow<Room>
    fun joinGame(idRoom: Int, authToken: String): Flow<JoinGame>
    fun gameAlreadyEnd(idGame: String, authToken: String): Flow<String>
    fun forceEndGame(idGame: String, authToken: String): Flow<String>
    fun getListScoreMulti(idGame: Int, authToken: String): Flow<List<ScoreMulti>>
    fun savePredictHurufMulti(idGame: Int,idSoal: Int, score: Int, duration: Int, authToken: String): Flow<SavePredictMulti>
    fun savePredictAngkaMulti(idGame: Int,idSoal: Int,score: Int , duration: Int, authToken: String): Flow<SavePredictMulti>
    fun getListUserJoin(authToken: String): Flow<List<UserJoinMulti>>
    fun getListUserParticipant(idGame: Int, authToken: String): Flow<List<UserPaticipantMulti>>
}