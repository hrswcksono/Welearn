package com.tugasakhir.welearn.domain.repository

import com.tugasakhir.welearn.domain.entity.*
import kotlinx.coroutines.flow.Flow

interface ISinglePlayerRepository {
    fun scoreUser(id: Int): Flow<ScoreEntity>
    fun highScore(id: Int): Flow<List<RankingScoreEntity>>
    fun predictAngka(idSoal: Int, score: Int): Flow<ResultPredictEntity>
    fun predictHuruf(idSoal: Int, score: Int): Flow<ResultPredictEntity>
    fun getLevel(idLevel: Int): Flow<List<LevelEntity>>
    fun getRandSoalSingle(jenis: Int ,level: Int): Flow<List<SoalEntity>>
}