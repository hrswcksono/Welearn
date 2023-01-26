package com.tugasakhir.welearn.domain.usecase.singleplayer

import com.tugasakhir.welearn.domain.entity.*
import kotlinx.coroutines.flow.Flow

interface SinglePlayerUseCase {
    fun angkaPredict(idSoal: Int, score: Int, authToken: String): Flow<ResultPredictEntity>
    fun hurufPredict(idSoal: Int, score: Int, authToken: String): Flow<ResultPredictEntity>
    fun getLevel(idLevel: Int, authToken: String): Flow<List<LevelEntity>>
    fun getSoalRandomSinglePlayer(jenis: Int, level: Int, authToken: String): Flow<List<SoalEntity>>
    fun userScore(id: Int, authToken: String): Flow<ScoreEntity>
    fun getHighScore(id: Int, authToken: String): Flow<List<RankingScoreEntity>>
}