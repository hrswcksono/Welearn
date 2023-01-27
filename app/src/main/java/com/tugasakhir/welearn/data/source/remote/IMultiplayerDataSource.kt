package com.tugasakhir.welearn.data.source.remote

import com.tugasakhir.welearn.data.source.remote.response.*
import com.tugasakhir.welearn.domain.entity.PushNotification
import kotlinx.coroutines.flow.Flow

interface IMultiplayerDataSource {
    fun soalMultiplayer(jenis: Int ,level: Int, tokenUser: String): Flow<SimpleResponse>
    fun soalByID(id: Int, tokenUser: String): Flow<SoalResponseMessage>
    fun pushNotification(body: PushNotification): Flow<PushNotificationResponse>
    fun makeRoomGame(id_jenis: Int, id_level: Int, tokenUser: String): Flow<MakeRoomResponse>
    fun joinGame(idRoom: Int, tokenUser: String): Flow<JoinGameResponse>
    fun endGame(idGame: String, tokenUser: String): Flow<SimpleResponse>
    fun scoreMulti(id: Int, tokenUser: String): Flow<List<ScoreMultiplayerResponse>>
    fun predictAngkaMulti(idGame: Int, idSoal: Int,score: Int , duration: Int, tokenUser: String): Flow<SavePredictMultiResponse>
    fun predictHurufMulti(idGame: Int, idSoal: Int,score: Int , duration: Int, tokenUser: String): Flow<SavePredictMultiResponse>
    fun getJoinedGame(tokenUser: String): Flow<List<JoinedGameResponse>>
    fun userParticipant(id: Int, tokenUser: String): Flow<List<UserParticipatedResponse>>
}