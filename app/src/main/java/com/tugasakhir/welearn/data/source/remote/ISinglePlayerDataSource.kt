package com.tugasakhir.welearn.data.source.remote

import com.tugasakhir.welearn.data.source.remote.response.*
import kotlinx.coroutines.flow.Flow

interface ISinglePlayerDataSource {
    fun scoreUser(id: Int, tokenUser: String): Flow<ScoreResponse>
    fun highScore(id: Int, tokenUser: String): Flow<List<HighScoreResponse>>
    fun predictAngka(idSoal: Int, score: Int, tokenUser: String): Flow<PredictResponse>
    fun predictHuruf(idSoal: Int, score: Int, tokenUser: String): Flow<PredictResponse>
    fun levelSoal(level: Int, tokenUser: String): Flow<List<LevelResponse>>
    fun randSoalSingle(jenis : Int,level: Int, tokenUser: String): Flow<List<RandomSoalResponse>>
}