package com.tugasakhir.welearn.domain.usecase.multiplayer

import com.tugasakhir.welearn.data.source.remote.response.PushNotificationResponse
import com.tugasakhir.welearn.domain.entity.PushNotification
import com.tugasakhir.welearn.domain.entity.UserJoinEntity
import com.tugasakhir.welearn.domain.entity.UserPaticipantEntity
import com.tugasakhir.welearn.domain.repository.IMultiPlayerRepository
import com.tugasakhir.welearn.domain.repository.IWelearnRepository
import kotlinx.coroutines.flow.Flow

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
}