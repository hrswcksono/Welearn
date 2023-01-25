package com.tugasakhir.welearn.domain.usecase.singleplayer

import com.tugasakhir.welearn.domain.entity.*
import kotlinx.coroutines.flow.Flow

interface SinglePlayerUseCase {
    fun angkaPredict(idSoal: Int, score: Int): Flow<ResultPredictEntity>
    fun hurufPredict(idSoal: Int, score: Int): Flow<ResultPredictEntity>
    fun getLevel(idLevel: Int): Flow<List<LevelEntity>>
    fun getSoalRandomSinglePlayer(jenis: Int, level: Int): Flow<List<SoalEntity>>
    fun userScore(id: Int): Flow<ScoreEntity>
    fun getHighScore(id: Int): Flow<List<RankingScoreEntity>>
}