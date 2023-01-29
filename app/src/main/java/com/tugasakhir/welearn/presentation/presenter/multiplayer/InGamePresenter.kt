package com.tugasakhir.welearn.presentation.presenter.multiplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.multiplayer.MultiPlayerUseCase

class InGamePresenter(private val useCase: MultiPlayerUseCase): ViewModel() {
    fun getListUserParticipant(idGame: Int, authToken: String) = useCase.getListUserParticipant(idGame, authToken)
    fun getSoalByID(id: Int, authToken: String) = useCase.getSoalByID(id, authToken)
    fun savePredictHurufMulti(idGame: Int,idSoal: Int,score: Int , duration: Int, authToken: String) = useCase.savePredictHurufMulti(idGame, idSoal,score , duration, authToken)
    fun savePredictAngkaMulti(idGame: Int,idSoal: Int,score: Int , duration: Int, authToken: String) = useCase.savePredictAngkaMulti(idGame, idSoal,score , duration, authToken)
    fun gameAlreadyEnd(idGame: String, authToken: String) = useCase.gameAlreadyEnd(idGame, authToken)
    fun forceEndGame(idGame: String, authToken: String) = useCase.forcedEndGame(idGame, authToken)
}