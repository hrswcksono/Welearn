package com.tugasakhir.welearn.data.source.remote

import com.tugasakhir.welearn.data.source.remote.network.ApiResponse
import com.tugasakhir.welearn.data.source.remote.response.*
import com.tugasakhir.welearn.domain.entity.PushNotification
import kotlinx.coroutines.flow.Flow

interface IMultiplayerDataSource {
    fun getIDSoalMulti(jenis: Int ,level: Int, tokenUser: String): Flow<ApiResponse<IDSoalMultiResponse>>
    fun getSoalByID(id: Int, tokenUser: String): Flow<ApiResponse<SoalResponseMessage>>
    fun pushNotification(body: PushNotification): Flow<ApiResponse<PushNotificationResponse>>
    fun makeRoom(id_jenis: Int, id_level: Int, tokenUser: String): Flow<ApiResponse<MakeRoomResponse>>
    fun joinGame(idRoom: Int, tokenUser: String): Flow<ApiResponse<JoinGameResponse>>
    fun gameAlreadyEnd(idGame: String, tokenUser: String): Flow<ApiResponse<GameAlreadyEndResponse>>
    fun forceEndGame(idGame: String, tokenUser: String): Flow<ApiResponse<ForceEndGameResponse>>
    fun getListScoreMulti(id: Int, tokenUser: String): Flow<ApiResponse<List<ScoreMultiResponse>>>
    fun savePredictAngkaMulti(idGame: Int, idSoal: Int,score: Int , duration: Int, tokenUser: String): Flow<ApiResponse<SavePredictMultiResponse>>
    fun savePredictHurufMulti(idGame: Int, idSoal: Int,score: Int , duration: Int, tokenUser: String): Flow<ApiResponse<SavePredictMultiResponse>>
    fun getListUserJoin(tokenUser: String): Flow<ApiResponse<List<JoinedGameResponse>>>
    fun getListUserParticipant(id: Int, tokenUser: String): Flow<ApiResponse<List<UserParticipatedResponse>>>
    fun startGame(id: Int, tokenUser: String): Flow<ApiResponse<StartGameResponse>>
}