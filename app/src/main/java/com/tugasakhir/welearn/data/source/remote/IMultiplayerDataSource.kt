package com.tugasakhir.welearn.data.source.remote

import com.tugasakhir.welearn.data.source.remote.response.*
import com.tugasakhir.welearn.domain.entity.PushNotification
import kotlinx.coroutines.flow.Flow

interface IMultiplayerDataSource {
    fun getIDSoalMulti(jenis: Int ,level: Int, tokenUser: String): Flow<SimpleResponse>
    fun getSoalByID(id: Int, tokenUser: String): Flow<SoalResponseMessage>
    fun pushNotification(body: PushNotification): Flow<PushNotificationResponse>
    fun makeRoom(id_jenis: Int, id_level: Int, tokenUser: String): Flow<MakeRoomResponse>
    fun joinGame(idRoom: Int, tokenUser: String): Flow<JoinGameResponse>
    fun gameAlreadyEnd(idGame: String, tokenUser: String): Flow<GameAlreadyEndResponse>
    fun forceEndGame(idGame: String, tokenUser: String): Flow<ForceEndGameResponse>
    fun getListScoreMulti(id: Int, tokenUser: String): Flow<List<ScoreMultiItem>>
    fun savePredictAngkaMulti(idGame: Int, idSoal: Int,score: Int , duration: Int, tokenUser: String): Flow<SavePredictMultiResponse>
    fun savePredictHurufMulti(idGame: Int, idSoal: Int,score: Int , duration: Int, tokenUser: String): Flow<SavePredictMultiResponse>
    fun getListUserJoin(tokenUser: String): Flow<List<JoinedGameResponse>>
    fun getListUserParticipant(id: Int, tokenUser: String): Flow<List<UserParticipatedResponse>>
}