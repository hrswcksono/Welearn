package com.tugasakhir.welearn.domain.repository

import com.tugasakhir.welearn.domain.entity.LevelEntity
import com.tugasakhir.welearn.domain.entity.ResultPredictEntity
import kotlinx.coroutines.flow.Flow

interface ISinglePlayerRepository {
    fun predictAngka(idSoal: Int, score: Int): Flow<ResultPredictEntity>
    fun predictHuruf(idSoal: Int, score: Int): Flow<ResultPredictEntity>
    fun getLevel(idLevel: Int): Flow<List<LevelEntity>>
}