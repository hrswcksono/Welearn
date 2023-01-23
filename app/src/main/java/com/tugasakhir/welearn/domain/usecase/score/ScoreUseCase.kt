package com.tugasakhir.welearn.domain.usecase.score

import com.tugasakhir.welearn.domain.entity.RankingScoreEntity
import com.tugasakhir.welearn.domain.entity.ScoreEntity
import com.tugasakhir.welearn.domain.entity.ScoreMultiEntity
import kotlinx.coroutines.flow.Flow

interface ScoreUseCase {
    fun scoreMulti(idGame: Int): Flow<List<ScoreMultiEntity>>
    fun userScore(id: Int): Flow<ScoreEntity>
    fun getHighScore(id: Int): Flow<List<RankingScoreEntity>>
}