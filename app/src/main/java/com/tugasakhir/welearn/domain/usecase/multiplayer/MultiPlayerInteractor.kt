package com.tugasakhir.welearn.domain.usecase.multiplayer

import com.tugasakhir.welearn.domain.entity.PushNotification
import com.tugasakhir.welearn.domain.repository.IMultiPlayerRepository

class MultiPlayerInteractor (private val welearnRepository: IMultiPlayerRepository): MultiPlayerUseCase {
    override fun makeRoomGame(idJenis: Int, idLevel: Int, authToken: String) = welearnRepository.makeRoomGame(idJenis, idLevel, authToken)

    override fun joinGame(idGame: String, authToken: String) = welearnRepository.joinGame(idGame, authToken)

    override fun endGame(idGame: String, authToken: String) = welearnRepository.endGame(idGame, authToken)

    override fun predictHurufMulti(
        idGame: Int,
        idSoal: Int,
        score: Int,
        duration: Int,
        authToken: String
    ) = welearnRepository.predictHurufMulti(idGame, idSoal, score , duration, authToken)

    override fun predictAngkaMulti(
        idGame: Int,idSoal: Int,score: Int , duration: Int, authToken: String
    ) = welearnRepository.predictAngkaMulti(idGame, idSoal, score, duration, authToken)

    override fun userJoinedGame(authToken: String) = welearnRepository.getJoinedGame(authToken)

    override fun getUserParticipant(idGame: Int, authToken: String) = welearnRepository.getUserParticipant(idGame, authToken)

    override fun pushNotification(body: PushNotification) = welearnRepository.pushNotification(body)

    override fun getIDSoalMultiplayer(jenis: Int, level: Int, authToken: String) = welearnRepository.getIDSoalMultiplayer(jenis, level, authToken)

    override fun getSoalByID(id: Int, authToken: String) = welearnRepository.soalByID(id, authToken)

    override fun scoreMulti(idGame: Int, authToken: String) = welearnRepository.scoreMulti(idGame, authToken)
}