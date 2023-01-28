package com.tugasakhir.welearn.domain.usecase.multiplayer

import com.tugasakhir.welearn.domain.entity.PushNotification
import com.tugasakhir.welearn.domain.repository.IMultiPlayerRepository
import kotlinx.coroutines.flow.Flow

class MultiPlayerInteractor (private val welearnRepository: IMultiPlayerRepository): MultiPlayerUseCase {
    override fun makeRoomGame(idJenis: Int, idLevel: Int, authToken: String) = welearnRepository.makeRoomGame(idJenis, idLevel, authToken)
    override fun joinGame(idRoom: Int, authToken: String) = welearnRepository.joinGame(idRoom, authToken)
    override fun gameAlreadyEnd(idGame: String, authToken: String) = welearnRepository.gameAlreadyEnd(idGame, authToken)
    override fun forcedEndGame(idGame: String, authToken: String) = welearnRepository.forceEndGame(idGame, authToken)
    override fun predictHurufMulti(
        idGame: Int, idSoal: Int, score: Int, duration: Int, authToken: String
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