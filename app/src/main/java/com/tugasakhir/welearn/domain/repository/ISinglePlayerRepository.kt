package com.tugasakhir.welearn.domain.repository

import com.tugasakhir.welearn.domain.entity.*
import kotlinx.coroutines.flow.Flow

interface ISinglePlayerRepository {
    fun scoreUser(id: Int, authToken: String): Flow<ScoreEntity>
    fun highScore(id: Int, authToken: String): Flow<List<RankingScoreEntity>>
    fun predictAngka(idSoal: Int, score: Int, authToken: String): Flow<ResultPredictEntity>
    fun predictHuruf(idSoal: Int, score: Int, authToken: String): Flow<ResultPredictEntity>
    fun getLevel(idLevel: Int, authToken: String): Flow<List<LevelEntity>>
    fun getRandSoalSingle(jenis: Int ,level: Int, authToken: String): Flow<List<SoalEntity>>
}