package com.tugasakhir.welearn.domain.usecase.singleplayer

import com.tugasakhir.welearn.domain.entity.LevelEntity
import com.tugasakhir.welearn.domain.entity.ResultPredictEntity
import com.tugasakhir.welearn.domain.repository.ISinglePlayerRepository
import com.tugasakhir.welearn.domain.repository.IWelearnRepository
import com.tugasakhir.welearn.domain.usecase.soal.SoalUseCase
import kotlinx.coroutines.flow.Flow

class SinglePlayerInteractor (private val welearnRepository: ISinglePlayerRepository): SinglePlayerUseCase {
    override fun angkaPredict(
        idSoal: Int,
        score: Int
    ) = welearnRepository.predictAngka(idSoal, score)

    override fun hurufPredict(
        idSoal: Int,
        score: Int
    ) = welearnRepository.predictHuruf(idSoal, score)

    override fun getLevel(idLevel: Int) = welearnRepository.getLevel(idLevel)
}