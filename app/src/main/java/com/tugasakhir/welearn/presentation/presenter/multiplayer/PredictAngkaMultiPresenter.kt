package com.tugasakhir.welearn.presentation.presenter.multiplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.multiplayer.MultiPlayerUseCase

class PredictAngkaMultiPresenter (private val useCase: MultiPlayerUseCase): ViewModel() {
    fun predictAngkaMulti(idGame: Int,idSoal: Int,score: Int , duration: Int, authToken: String) = useCase.predictAngkaMulti(idGame, idSoal,score , duration, authToken)
}