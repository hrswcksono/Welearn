package com.tugasakhir.welearn.domain.usecase.multiplayer

import com.tugasakhir.welearn.data.Resource
import com.tugasakhir.welearn.data.source.remote.response.PushNotificationResponse
import com.tugasakhir.welearn.domain.entity.*
import kotlinx.coroutines.flow.Flow

interface MultiPlayerUseCase {
    fun makeRoom(idJenis: Int, idLevel: Int, authToken: String): Flow<Resource<Room>>
    fun joinGame(idRoom: Int, authToken: String): Flow<Resource<JoinGame>>
    fun gameAlreadyEnd(idGame: String, authToken: String): Flow<Resource<String>>
    fun forcedEndGame(idGame: String, authToken: String): Flow<Resource<String>>
    fun savePredictHurufMulti(idGame: Int,idSoal: Int,score: Int , duration: Int, authToken: String): Flow<Resource<SavePredictMulti>>
    fun savePredictAngkaMulti(idGame: Int,idSoal: Int,score: Int , duration: Int, authToken: String): Flow<Resource<SavePredictMulti>>
    fun getListUserJoin(authToken: String): Flow<Resource<List<UserJoinMulti>>>
    fun getListUserParticipant(idGame: Int, authToken: String): Flow<Resource<List<UserPaticipantMulti>>>
    fun pushNotification(body: PushNotification): Flow<Resource<String>>
    fun getIDSoalMulti(jenis: Int,level: Int, authToken: String): Flow<Resource<IDSoalMulti>>
    fun getSoalByID(id: Int, authToken: String): Flow<Resource<Soal>>
    fun getListScoreMulti(idGame: Int, authToken: String): Flow<Resource<List<ScoreMulti>>>
}