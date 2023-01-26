package com.tugasakhir.welearn.domain.usecase.singleplayer

import com.tugasakhir.welearn.domain.repository.ISinglePlayerRepository

class SinglePlayerInteractor (private val welearnRepository: ISinglePlayerRepository): SinglePlayerUseCase {
    override fun angkaPredict(
        idSoal: Int,
        score: Int,
        authToken: String
    ) = welearnRepository.predictAngka(idSoal, score, authToken)

    override fun hurufPredict(
        idSoal: Int,
        score: Int,
        authToken: String
    ) = welearnRepository.predictHuruf(idSoal, score, authToken)

    override fun getLevel(idLevel: Int, authToken: String) = welearnRepository.getLevel(idLevel, authToken)

    override fun getSoalRandomSinglePlayer(jenis: Int, level: Int, authToken: String) = welearnRepository.getRandSoalSingle(jenis, level, authToken)

    override fun userScore(id: Int, authToken: String) = welearnRepository.scoreUser(id, authToken)

    override fun getHighScore(id: Int, authToken: String) = welearnRepository.highScore(id, authToken)
}