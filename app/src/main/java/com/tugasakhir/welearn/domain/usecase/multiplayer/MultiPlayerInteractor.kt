package com.tugasakhir.welearn.domain.usecase.multiplayer

import com.tugasakhir.welearn.data.Resource
import com.tugasakhir.welearn.domain.entity.PushNotification
import com.tugasakhir.welearn.domain.repository.IMultiPlayerRepository
import kotlinx.coroutines.flow.Flow

class MultiPlayerInteractor (private val welearnRepository: IMultiPlayerRepository): MultiPlayerUseCase {
    override fun makeRoom(idJenis: Int, idLevel: Int, authToken: String) = welearnRepository.makeRoom(idJenis, idLevel, authToken)
    override fun joinGame(idRoom: Int, authToken: String) = welearnRepository.joinGame(idRoom, authToken)
    override fun gameAlreadyEnd(idGame: String, authToken: String) = welearnRepository.gameAlreadyEnd(idGame, authToken)
    override fun forcedEndGame(idGame: String, authToken: String) = welearnRepository.forceEndGame(idGame, authToken)
    override fun savePredictHurufMulti(
        idGame: Int, idSoal: Int, score: Int, duration: Int, authToken: String
    ) = welearnRepository.savePredictHurufMulti(idGame, idSoal, score , duration, authToken)
    override fun savePredictAngkaMulti(
        idGame: Int,idSoal: Int,score: Int , duration: Int, authToken: String
    ) = welearnRepository.savePredictAngkaMulti(idGame, idSoal, score, duration, authToken)
    override fun getListUserJoin(authToken: String) = welearnRepository.getListUserJoin(authToken)
    override fun getListUserParticipant(idGame: Int, authToken: String) = welearnRepository.getListUserParticipant(idGame, authToken)
    override fun pushNotification(body: PushNotification) = welearnRepository.pushNotification(body)
    override fun getIDSoalMulti(jenis: Int, level: Int, authToken: String) = welearnRepository.getIDSoalMulti(jenis, level, authToken)
    override fun getSoalByID(id: Int, authToken: String) = welearnRepository.getSoalByID(id, authToken)
    override fun getListScoreMulti(idGame: Int, authToken: String) = welearnRepository.getListScoreMulti(idGame, authToken)
    override fun startGame(idGame: Int, authToken: String) = welearnRepository.startGame(idGame, authToken)
}