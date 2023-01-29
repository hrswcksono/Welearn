package com.tugasakhir.welearn.domain.repository

import com.tugasakhir.welearn.data.Resource
import com.tugasakhir.welearn.data.source.remote.response.PushNotificationResponse
import com.tugasakhir.welearn.domain.entity.*
import kotlinx.coroutines.flow.Flow

interface IMultiPlayerRepository {
    fun getIDSoalMulti(jenis: Int, level: Int, authToken: String): Flow<Resource<IDSoalMulti>>
    fun getSoalByID(id: Int, authToken: String): Flow<Resource<Soal>>
    fun pushNotification(body: PushNotification): Flow<Resource<String>>
    fun makeRoom(id_jenis: Int, id_level: Int, authToken: String): Flow<Resource<Room>>
    fun joinGame(idRoom: Int, authToken: String): Flow<Resource<JoinGame>>
    fun gameAlreadyEnd(idGame: String, authToken: String): Flow<Resource<String>>
    fun forceEndGame(idGame: String, authToken: String): Flow<Resource<String>>
    fun getListScoreMulti(idGame: Int, authToken: String): Flow<Resource<List<ScoreMulti>>>
    fun savePredictHurufMulti(idGame: Int,idSoal: Int, score: Int, duration: Int, authToken: String): Flow<Resource<SavePredictMulti>>
    fun savePredictAngkaMulti(idGame: Int,idSoal: Int,score: Int , duration: Int, authToken: String): Flow<Resource<SavePredictMulti>>
    fun getListUserJoin(authToken: String): Flow<Resource<List<UserJoinMulti>>>
    fun getListUserParticipant(idGame: Int, authToken: String): Flow<Resource<List<UserPaticipantMulti>>>
}