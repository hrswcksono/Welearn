package com.tugasakhir.welearn.domain.usecase.score

import com.tugasakhir.welearn.domain.entity.RankingScoreEntity
import com.tugasakhir.welearn.domain.entity.ScoreEntity
import com.tugasakhir.welearn.domain.entity.ScoreMultiEntity
import com.tugasakhir.welearn.domain.repository.IScoreRepository
import com.tugasakhir.welearn.domain.repository.IWelearnRepository
import kotlinx.coroutines.flow.Flow

class ScoreInteractor (private val welearnRepository: IScoreRepository): ScoreUseCase{
    override fun scoreMulti(idGame: Int) = welearnRepository.scoreMulti(idGame)
    override fun userScore(id: Int) = welearnRepository.scoreUser(id)
    override fun getHighScore(id: Int) = welearnRepository.highScore(id)
}