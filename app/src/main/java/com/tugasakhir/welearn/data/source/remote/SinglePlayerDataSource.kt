package com.tugasakhir.welearn.data.source.remote

import android.util.Log
import com.tugasakhir.welearn.data.source.remote.network.SinglePlayerClient
import com.tugasakhir.welearn.data.source.remote.response.ScoreResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SinglePlayerDataSource(private val apiService: SinglePlayerClient) {
    fun scoreUser(id: Int, tokenUser: String) =
        flow {
            try {
                val response = apiService.scoreUser(id, token = "Bearer $tokenUser")
                emit(response.message)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO) as Flow<ScoreResponse>

    fun highScore(id: Int, tokenUser: String) =
        flow {
            try {
                val response = apiService.getHighScore(id ,token = "Bearer $tokenUser")
                emit(response.message)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun predictAngka(idSoal: Int, score: Int, tokenUser: String) =
        flow{
            try {
                val response = apiService.predictAngka(idSoal, score, token = "Bearer $tokenUser")
                emit(response)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun predictHuruf(idSoal: Int, score: Int, tokenUser: String) =
        flow{
            try {
                val response = apiService.predictHuruf(idSoal, score, token = "Bearer $tokenUser")
                emit(response)
            }catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun levelSoal(level: Int, tokenUser: String) =
        flow {
            try {
                val response = apiService.getLevel(level,token = "Bearer $tokenUser")
                emit(response.message)
            }catch (e: Exception) {
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun randSoalSingle(jenis : Int,level: Int, tokenUser: String) =
        flow {
            try {
                val response = apiService.getRandomSoal(jenis, level, token = "Bearer $tokenUser")
                emit(response.message)
            } catch (e: Exception) {
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
}