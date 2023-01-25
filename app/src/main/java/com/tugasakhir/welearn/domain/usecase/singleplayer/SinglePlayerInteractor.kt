package com.tugasakhir.welearn.domain.usecase.singleplayer

import com.tugasakhir.welearn.domain.repository.ISinglePlayerRepository

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

    override fun getSoalRandomSinglePlayer(jenis: Int, level: Int) = welearnRepository.getRandSoalSingle(jenis, level)

    override fun userScore(id: Int) = welearnRepository.scoreUser(id)

    override fun getHighScore(id: Int) = welearnRepository.highScore(id)
}