package com.tugasakhir.welearn.presentation.presenter.multiplayer

import androidx.lifecycle.ViewModel
import com.tugasakhir.welearn.domain.usecase.multiplayer.MultiPlayerUseCase

class PredictHurufMultiPresenter(private val useCase: MultiPlayerUseCase): ViewModel() {
    fun predictHurufMulti(idGame: Int,idSoal: Int,score: Int , duration: Int, authToken: String) = useCase.predictHurufMulti(idGame, idSoal,score , duration, authToken)
}