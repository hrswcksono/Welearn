package com.tugasakhir.welearn.domain.usecase.singleplayer

import com.tugasakhir.welearn.domain.entity.IDSoalMultiEntity
import com.tugasakhir.welearn.domain.entity.LevelEntity
import com.tugasakhir.welearn.domain.entity.ResultPredictEntity
import com.tugasakhir.welearn.domain.entity.SoalEntity
import kotlinx.coroutines.flow.Flow

interface SinglePlayerUseCase {
    fun angkaPredict(idSoal: Int, score: Int): Flow<ResultPredictEntity>
    fun hurufPredict(idSoal: Int, score: Int): Flow<ResultPredictEntity>
    fun getLevel(idLevel: Int): Flow<List<LevelEntity>>
}