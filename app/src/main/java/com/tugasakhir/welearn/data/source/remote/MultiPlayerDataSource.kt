package com.tugasakhir.welearn.data.source.remote

import android.util.Log
import com.tugasakhir.welearn.utils.Constants
import com.tugasakhir.welearn.data.source.remote.network.MultiPlayerClient
import com.tugasakhir.welearn.data.source.remote.response.*
import com.tugasakhir.welearn.domain.entity.PushNotification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MultiPlayerDataSource constructor(private val apiService: MultiPlayerClient) : IMultiplayerDataSource {

    override fun getIDSoalMulti(
        jenis: Int,
        level: Int,
        tokenUser: String
    ) =
    flow {
        try {
            val response = apiService.getIDSoalMulti(jenis ,level,token = "Bearer $tokenUser")
            emit(response)
        } catch (e: Exception) {
            Log.e("error", e.toString())
        }
    }.flowOn(Dispatchers.IO)

    override fun getListScoreMulti(id: Int, tokenUser: String) =
        flow{
            try {
                val response = apiService.getListScoreMulti(id, token = "Bearer $tokenUser")
                emit(response.data)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    override fun getSoalByID(id: Int, tokenUser: String) =
        flow {
            try {
                val response = apiService.getSoalByID(id, token = "Bearer $tokenUser")
                emit(response.message)
            } catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO) as Flow<SoalResponseMessage>

    override fun pushNotification(body: PushNotification) =
        flow {
            try {
                val response = apiService.postNotification(Constants.FCM_BASE_URL, body)
                emit(response)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    override fun makeRoom(id_jenis: Int, id_level: Int, tokenUser: String) =
        flow{
            try {
                val response = apiService.makeRoom(id_jenis, id_level,token = "Bearer $tokenUser")
                emit(response)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    override fun joinGame(idRoom: Int, tokenUser: String) =
        flow{
            try {
                val response = apiService.joinGame(idRoom, token = "Bearer $tokenUser")
                emit(response)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    override fun gameAlreadyEnd(idGame: String, tokenUser: String) =
        flow{
            try {
                val response = apiService.gameAlreadyEnd(idGame, token = "Bearer $tokenUser")
                emit(response)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    override fun forceEndGame(idGame: String, tokenUser: String)=
        flow{
            try {
                val response = apiService.forceEndGame(idGame, token = "Bearer $tokenUser")
                emit(response)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    override fun savePredictAngkaMulti(
        idGame: Int,
        idSoal: Int,
        score: Int,
        duration: Int,
        tokenUser: String
    ) =
        flow{
            try {
                val response = apiService.savePredictHurufMulti(idGame, idSoal,score, duration, token = "Bearer $tokenUser")
                emit(response)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    override fun savePredictHurufMulti(
        idGame: Int,
        idSoal: Int,
        score: Int,
        duration: Int,
        tokenUser: String
    ) =
        flow{
            try {
                val response = apiService.savePredictAngkaMulti(idGame, idSoal,score, duration, token = "Bearer $tokenUser")
                emit(response)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    override fun getListUserJoin(tokenUser: String) =
        flow{
            try {
                val response = apiService.getListUserJoin(token = "Bearer $tokenUser")
                emit(response.data)
            }catch (e: Exception) {
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    override fun getListUserParticipant(
        id: Int,
        tokenUser: String
    ) =
        flow {
            try {
                val response = apiService.getListUserParticipant(id,token = "Bearer $tokenUser")
                emit(response.message)
            }catch (e: Exception) {
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)

}