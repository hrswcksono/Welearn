package com.tugasakhir.welearn.domain.usecase.multiplayer

import com.tugasakhir.welearn.domain.entity.PushNotification
import com.tugasakhir.welearn.domain.repository.IMultiPlayerRepository

class MultiPlayerInteractor (private val welearnRepository: IMultiPlayerRepository): MultiPlayerUseCase {
    override fun makeRoomGame(idJenis: Int, idLevel: Int) = welearnRepository.makeRoomGame(idJenis, idLevel)

    override fun joinGame(idGame: String) = welearnRepository.joinGame(idGame)

    override fun endGame(idGame: String) = welearnRepository.endGame(idGame)

    override fun predictHurufMulti(
        idGame: Int,
        idSoal: Int,
        score: Int,
        duration: Int,
    ) = welearnRepository.predictHurufMulti(idGame, idSoal, score , duration)

    override fun predictAngkaMulti(
        idGame: Int,idSoal: Int,score: Int , duration: Int,
    ) = welearnRepository.predictAngkaMulti(idGame, idSoal, score, duration)

    override fun userJoinedGame() = welearnRepository.getJoinedGame()

    override fun getUserParticipant(idGame: Int) = welearnRepository.getUserParticipant(idGame)

    override fun pushNotification(body: PushNotification) = welearnRepository.pushNotification(body)

    override fun getIDSoalMultiplayer(jenis: Int, level: Int) = welearnRepository.getIDSoalMultiplayer(jenis, level)

    override fun getSoalByID(id: Int) = welearnRepository.soalByID(id)

    override fun scoreMulti(idGame: Int) = welearnRepository.scoreMulti(idGame)
}