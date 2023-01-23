package com.tugasakhir.welearn.domain.repository

import com.tugasakhir.welearn.domain.entity.RankingScoreEntity
import com.tugasakhir.welearn.domain.entity.ScoreEntity
import com.tugasakhir.welearn.domain.entity.ScoreMultiEntity
import kotlinx.coroutines.flow.Flow

interface IScoreRepository {
    fun scoreMulti(idGame: Int): Flow<List<ScoreMultiEntity>>
    fun scoreUser(id: Int): Flow<ScoreEntity>
    fun highScore(id: Int): Flow<List<RankingScoreEntity>>
}