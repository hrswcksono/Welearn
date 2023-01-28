package com.tugasakhir.welearn.domain.repository

import com.tugasakhir.welearn.data.source.remote.response.PushNotificationResponse
import com.tugasakhir.welearn.domain.entity.*
import kotlinx.coroutines.flow.Flow

interface IMultiPlayerRepository {
    fun getIDSoalMultiplayer(jenis: Int, level: Int, authToken: String): Flow<IDSoalMulti>
    fun soalByID(id: Int, authToken: String): Flow<Soal>
    fun pushNotification(body: PushNotification): Flow<PushNotificationResponse>
    fun makeRoomGame(id_jenis: Int, id_level: Int, authToken: String): Flow<Room>
    fun joinGame(idRoom: Int, authToken: String): Flow<JoinGame>
    fun gameAlreadyEnd(idGame: String, authToken: String): Flow<String>
    fun forceEndGame(idGame: String, authToken: String): Flow<String>
    fun scoreMulti(idGame: Int, authToken: String): Flow<List<ScoreMulti>>
    fun predictHurufMulti(idGame: Int,idSoal: Int, score: Int, duration: Int, authToken: String): Flow<SavePredictMulti>
    fun predictAngkaMulti(idGame: Int,idSoal: Int,score: Int , duration: Int, authToken: String): Flow<SavePredictMulti>
    fun getJoinedGame(authToken: String): Flow<List<UserJoinMulti>>
    fun getUserParticipant(idGame: Int, authToken: String): Flow<List<UserPaticipantMulti>>
}